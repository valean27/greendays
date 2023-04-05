package com.greendays.greendays.controller;

import com.greendays.greendays.mapper.DailyReportEntityToDailyReportDtoMapper;
import com.greendays.greendays.model.dal.Client;
import com.greendays.greendays.model.dal.DailyReport;
import com.greendays.greendays.model.dal.Garbage;
import com.greendays.greendays.model.dal.Incident;
import com.greendays.greendays.model.dto.DailyReportDto;
import com.greendays.greendays.model.dto.File;
import com.greendays.greendays.model.dto.Statistics;
import com.greendays.greendays.model.dto.Trimester;
import com.greendays.greendays.model.totals.MonthlyReportData;
import com.greendays.greendays.repository.ClientRepository;
import com.greendays.greendays.service.ClientService;
import com.greendays.greendays.service.GarbageService;
import com.greendays.greendays.service.PdfReportGenerator;
import com.greendays.greendays.service.DailyReportService;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.apache.commons.io.FileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ReportController {

    private static final Logger log = LoggerFactory.getLogger(ReportController.class);
    private static final String SAVE_VALUE = "Salveaza";
    private static final String DELETE_VALUE = "Sterge Raport";
    @Autowired
    DailyReportService dailyReportService;

    @Autowired
    PdfReportGenerator pdfReportGenerator;

    @Autowired
    ClientService clientService;

    @Autowired
    GarbageService garbageService;

    @RequestMapping(value = "/pdfreport", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> trimestrialPdfReport(@RequestParam String quarter) {
        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=monthlyReport.pdf");

        Trimester trimester = Trimester.valueOf(Integer.parseInt(quarter));

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfReportGenerator.generateTrimestrialPdfReport(trimester)));
    }


    @RequestMapping(value = "/pdfMonthlyReport", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> monthlyReport(@RequestParam String data) throws ParseException {
        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=monthlyReport.pdf");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedCurrentDate = sdf.parse(data);

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfReportGenerator.generateMonthlyPdfReports(convertedCurrentDate)));
    }

    @RequestMapping(value = "/updateArchiveTable", method = RequestMethod.POST)
    public String updateNonCasnicQuantity(@RequestParam MultiValueMap<String, String> paramMap) {

        if (DELETE_VALUE.equals(paramMap.getFirst("modalButton"))) {
            long reportId = Long.parseLong(Objects.requireNonNull(paramMap.getFirst("reportId")));
            dailyReportService.deleteReport(reportId);
        } else {
            DailyReport report = dailyReportService.getReportById(paramMap.getFirst("reportId"));
            report.setQuantity(report.getQuantity().subtract(BigDecimal.valueOf(Double.parseDouble(Objects.requireNonNull(paramMap.getFirst("cantitateNoncasnica"))))));
            dailyReportService.postDailyReport(report);
            BigDecimal newQuantity = BigDecimal.valueOf(Double.parseDouble(Objects.requireNonNull(paramMap.getFirst("cantitateNoncasnica"))));
            DailyReport newReport = getUpdatedReportFromReport(report, newQuantity);
            dailyReportService.postDailyReport(newReport);
        }

        return "redirect:tabelArhiva";
    }

    public DailyReport getUpdatedReportFromReport(DailyReport dailyReport, BigDecimal quantity) {
        DailyReport report = new DailyReport();
        Optional<Client> optionalClient = clientService.getClients().stream().filter(client1 -> client1.getClientType().equals("Non-Casnic")).findAny();
        if (optionalClient.isPresent()){
            Client client = optionalClient.get();
            report.setClient(client);
        }else {
            Client client = new Client();
            client.setClientType("Non-Casnic");
            report.setClient(client);
        }

        report.setDate(Objects.requireNonNull(dailyReport.getDate()));
        report.setDestination(dailyReport.getDestination());
        report.setDriverName(dailyReport.getDriverName());
        report.setProblems(dailyReport.getProblems());
        report.setRouteNumber(dailyReport.getRouteNumber());
        report.setQuantity(quantity);
        Incident incident = new Incident();
        incident.setIncidentType(dailyReport.getIncident().getIncidentType());
        incident.setObservations(dailyReport.getIncident().getObservations());
        report.setIncident(incident);
        Optional<Garbage> garbage = garbageService.getGarbages().stream().filter(garbage1 -> garbage1.getGarbageName().equals(dailyReport.getGarbage().getGarbageName())).findFirst();
        garbage.ifPresent(report::setGarbage);

        report.setUat(dailyReport.getUat());
        report.setWeightTalon(dailyReport.getWeightTalon());
        report.setRouteSheet(dailyReport.getRouteSheet());
        return report;
    }


    @ResponseBody
    @RequestMapping(value = "/getForQuarter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DailyReport> quarter() {
        return dailyReportService.getAllReportsForQuarter(Trimester.I, 2022);
    }


    @ResponseBody
    @RequestMapping(value = "/zip/{date}", produces = "application/zip", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> getMonthlyReportFilesAsZip(@PathVariable String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date convertedCurrentDate = sdf.parse(date);
            log.info("Converted current date {}", convertedCurrentDate);
            LocalDate localDate = Instant.ofEpochMilli(convertedCurrentDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
log.info("Local date {} ", localDate);
            Optional<Path> optionalPath = Files.list(Paths.get("/usr/greendays/src/main/resources/zipuri"))
                    .peek(path -> log.info(path.toString()))
                    .peek(path -> log.info("/usr/greendays/src" + FileSystems.getDefault().getSeparator() + "main" + FileSystems.getDefault().getSeparator() + "resources" + FileSystems.getDefault().getSeparator() + "zipuri" + FileSystems.getDefault().getSeparator() + localDate.getYear() + "-" + (localDate.getMonthValue() > 9 ? localDate.getMonthValue() : "0" + localDate.getMonthValue())))
                    .filter(path -> path.toString().startsWith("/usr/greendays/src" + FileSystems.getDefault().getSeparator() + "main" + FileSystems.getDefault().getSeparator() + "resources" + FileSystems.getDefault().getSeparator() + "zipuri" + FileSystems.getDefault().getSeparator() + localDate.getYear() + "-" + (localDate.getMonthValue() > 9 ? localDate.getMonthValue() : "0" + localDate.getMonthValue())))
                    .findFirst();

            log.info("optionalPath {} ", optionalPath);

            if (optionalPath.isPresent()) {
                return ResponseEntity.ok()
                        .header("Content-Disposition", "attachment;filename=" + optionalPath.get().getFileName().toString())
                        .header("Content-Type", "application/octet-stream")
                        .body(new InputStreamResource(new ByteArrayInputStream(Files.readAllBytes(optionalPath.get()))));
            }

        } catch (ParseException e) {
            log.error("",e);
            e.printStackTrace();
        } catch (IOException e) {
            log.error("",e);
            e.printStackTrace();
        }

        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/getArchive", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<File> getArchive() {
        try {

            return Files.list(Paths.get("s/usr/greendays/src/main/resources/zipuri")).map(path -> {
                File file = new File();
                file.setFileName(path.getFileName().toString());
                try {
                    file.setSize(Files.size(path));
                    BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
                    FileTime fileTime = attr.creationTime();
                    file.setDate(fileTime.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return file;
            }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getStatistics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Statistics> getStatistics() {
        LocalDate localDate = LocalDate.now();
        List<DailyReportDto> dailyReportDtos = dailyReportService.getAllReportsOfMonth(localDate.getMonthValue(), localDate.getYear()).stream()
                .map(DailyReportEntityToDailyReportDtoMapper::mapEntityToDto)
                .collect(Collectors.toList());
        MonthlyReportData monthlyReportData = new MonthlyReportData(dailyReportDtos, localDate.getMonthValue(), localDate.getYear());

        Statistics statistics = new Statistics();

        statistics.setNumberOfReports(dailyReportDtos.size());
        statistics.setReciclable(monthlyReportData.getTotalRecyclable());
        statistics.setResiduals(monthlyReportData.getTotalByGarbageName("Rezidual"));
        statistics.setTotal(monthlyReportData.getTotal());
        return ResponseEntity.ok(statistics);

    }


}
