package com.greendays.greendays.controller;

import com.greendays.greendays.model.dal.Client;
import com.greendays.greendays.model.dal.DailyReport;
import com.greendays.greendays.model.dal.Garbage;
import com.greendays.greendays.model.dal.Incident;
import com.greendays.greendays.model.dto.DailyReportDto;
import com.greendays.greendays.model.dto.Trimester;
import com.greendays.greendays.service.PdfReportGenerator;
import com.greendays.greendays.service.DailyReportService;
import com.greendays.greendays.service.Quarter;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ReportController {

    @Autowired
    DailyReportService dailyReportService;

    @Autowired
    PdfReportGenerator pdfReportGenerator;

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
    public ResponseEntity<InputStreamResource> monthlyReport(@RequestParam String data, @RequestParam String data2) throws ParseException {
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

    @RequestMapping(value = "/updateNonCasnicQuantity", method = RequestMethod.POST)
    public String updateNonCasnicQuantity(@RequestParam MultiValueMap<String, String> paramMap) throws ParseException {
        DailyReport report = dailyReportService.getReportById(paramMap.getFirst("reportId"));
        report.setQuantity(report.getQuantity() - Double.parseDouble(Objects.requireNonNull(paramMap.getFirst("cantitateNoncasnica"))));
        dailyReportService.postDailyReport(report);
        Double newQuantity = (Double.valueOf(Objects.requireNonNull(paramMap.getFirst("cantitateNoncasnica"))));
        DailyReport newReport = getUpdatedReportFromReport(report, newQuantity);
        dailyReportService.postDailyReport(newReport);
        return "redirect:tabelArhiva";
    }

    public DailyReport getUpdatedReportFromReport(DailyReport dailyReport, Double quantity) {
        DailyReport report = new DailyReport();
        Client client = new Client();
        client.setClientType("Non-Casnic");
        report.setClient(client);
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
        Garbage garbage = new Garbage();
        garbage.setGarbageName(dailyReport.getGarbage().getGarbageName());
        garbage.setGarbageCode(dailyReport.getGarbage().getGarbageCode());
        report.setGarbage(garbage);
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
    @RequestMapping(value = "/zip", produces = "application/zip", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> getMonthlyReportFilesAsZip(@RequestParam String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date convertedCurrentDate = sdf.parse(date);
            LocalDate localDate = Instant.ofEpochMilli(convertedCurrentDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

            Optional<Path> optionalPath = Files.list(Paths.get("src/main/resources/zipuri"))
                    .filter(path -> path.toString().startsWith("src/main/resources/zipuri/" + localDate.getYear() + "-" + (localDate.getMonthValue() > 9 ? localDate.getMonthValue() : "0" + localDate.getMonthValue())))
                            .findFirst();

            if (optionalPath.isPresent()) {
                return ResponseEntity.ok()
                        .header("Content-Disposition", "attachment;filename=" + optionalPath.get().getFileName().toString())
                        .header("Content-Type", "application/octet-stream")
                        .body(new InputStreamResource(new ByteArrayInputStream(Files.readAllBytes(optionalPath.get()))));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
