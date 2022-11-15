package com.greendays.greendays.controller;

import com.greendays.greendays.model.dal.*;
import com.greendays.greendays.model.dto.File;
import com.greendays.greendays.repository.PaymentRepository;
import com.greendays.greendays.service.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ClientService clientService;
    private final DailyReportService dailyReportService;
    private final GarbageService garbageService;
    private final IncidentService incidentService;
    private final StorageService storageService;
    private final PaymentRepository paymentRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/dailyReport")
    public String dailyReport(Model model) {
        initializeDatabase();
        initializeDailyReportModel(model);
        return "dailyReport";
    }

    private void initializeDailyReportModel(Model model) {
        List<String> clientTypes = clientService.getClients().stream().map(Client::getClientType).collect(Collectors.toList());
        List<Garbage> garbageList = garbageService.getGarbages();
        model.addAttribute("clientTypes", clientTypes);
        model.addAttribute("garbageList", garbageList);
    }

    @PostMapping("/postDailyReport")
    public String postDailyReport(@RequestParam MultiValueMap<String, String> paramMap, @RequestParam("foaieParcurs") MultipartFile foaieParcurs, @RequestParam("talonCantarire") MultipartFile talonCantarire, Model model, RedirectAttributes redirectAttributes) {
        String talonCantarireAsFullPath = null;
        String foaieParcursAsFullPath = null;

        if (!foaieParcurs.isEmpty()) {
            foaieParcursAsFullPath = "upload-dir/" + LocalDateTime.now() + "." + StringUtils.substringAfter(foaieParcurs.getOriginalFilename(), ".");
            storageService.store(foaieParcurs, foaieParcursAsFullPath);
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded " + foaieParcurs.getOriginalFilename() + "!");

        }
        if (!talonCantarire.isEmpty()) {
            talonCantarireAsFullPath = "upload-dir/" + LocalDateTime.now() + "." + StringUtils.substringAfter(talonCantarire.getOriginalFilename(), ".");
            storageService.store(talonCantarire, talonCantarireAsFullPath);
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded " + talonCantarire.getOriginalFilename() + "!");

        }

        initializeDailyReportModel(model);

        paramMap.add("talonCantarire", talonCantarireAsFullPath == null ? "" : talonCantarireAsFullPath);
        paramMap.add("foaieParcurs", foaieParcursAsFullPath == null ? "" : foaieParcursAsFullPath);
        DailyReport report = populateReportFromParamMap(paramMap);
        String result = dailyReportService.postDailyReport(report);
        model.addAttribute("result", result);
        return "redirect:dailyReport";
    }


    @PostMapping("/postFinancialReport")
    public String postFinancialReport(@RequestParam MultiValueMap<String, String> paramMap, Model model, RedirectAttributes redirectAttributes) {
        Payment payment = new Payment();
        payment.setDate(Date.valueOf(Objects.requireNonNull(paramMap.getFirst("dataPlatii"))));
        payment.setTotalDc(BigDecimal.valueOf(Long.parseLong(Objects.requireNonNull(paramMap.getFirst("totalDc")))));
        payment.setPhysicalPerson(BigDecimal.valueOf(Long.parseLong(Objects.requireNonNull(paramMap.getFirst("persoaneFizice")))));
        payment.setJuridicalPerson(BigDecimal.valueOf(Long.parseLong(Objects.requireNonNull(paramMap.getFirst("presoaneJuridice")))));
        payment.setOcasionalServices(BigDecimal.valueOf(Long.parseLong(Objects.requireNonNull(paramMap.getFirst("serviciiOcazionale")))));
        payment.setRedevence(BigDecimal.valueOf(Long.parseLong(Objects.requireNonNull(paramMap.getFirst("redeventa")))));
        payment.setTrimester(Integer.parseInt(Objects.requireNonNull(paramMap.getFirst("trimestru"))));
        payment.setYear(Integer.parseInt(Objects.requireNonNull(paramMap.getFirst("an"))));
        paymentRepository.save(payment);

        return "Success";
    }

    private DailyReport populateReportFromParamMap(MultiValueMap<String, String> paramMap) {
        DailyReport report = new DailyReport();
        String clientType = paramMap.getFirst("client");
        Optional<Client>dbClient = clientService.getClients().stream().filter(cl -> cl.getClientType().equals(clientType)).findAny();
        if (dbClient.isPresent()){
            report.setClient(dbClient.get());
        }else {
            Client client = new Client();
            client.setClientType(paramMap.getFirst("client"));
            report.setClient(client);
        }
        report.setDate(Date.valueOf(Objects.requireNonNull(paramMap.getFirst("data"))));
        report.setDestination(paramMap.getFirst("destinatie"));
        report.setDriverName(paramMap.getFirst("numeSofer"));
        report.setProblems(paramMap.getFirst("alteProbleme"));
        report.setRouteNumber(Integer.valueOf(Objects.requireNonNull(paramMap.getFirst("numarTraseu"))));
        report.setQuantity(BigDecimal.valueOf(Double.parseDouble(Objects.requireNonNull(paramMap.getFirst("cantitate")))));
        Incident incident = new Incident();
        incident.setIncidentType(paramMap.getFirst("incident"));
        incident.setObservations(paramMap.getFirst("observatii"));
        report.setIncident(incident);
        Optional<Garbage> garbage = garbageService.getGarbages().stream().filter(garbage1 -> garbage1.getGarbageName().equalsIgnoreCase(paramMap.getFirst("denumireDeseu"))).findFirst();
        if (garbage.isPresent()) {
            report.setGarbage(garbage.get());
        } else {
            Garbage newGarbage = new Garbage();
            newGarbage.setGarbageName(paramMap.getFirst("denumireDeseu"));
            newGarbage.setGarbageCode(paramMap.getFirst("codDeseu"));
            report.setGarbage(newGarbage);
        }
        report.setUat(paramMap.getFirst("uat"));
        report.setWeightTalon(paramMap.getFirst("talonCantarire"));
        report.setRouteSheet(paramMap.getFirst("foaieParcurs"));
        System.out.println(report);
        return report;
    }

    private void initializeDatabase() {
//        clientService.insertClient("Casnic");
//        clientService.insertClient("Non-Casnic");
//        clientService.insertClient("Mixt");
//        garbageService.insertGarbage("20 01 01", "Hartie si Carton");
//        garbageService.insertGarbage("20 01 02", "Sticla");
//        garbageService.insertGarbage("20 01 08", "Deseuri Biodegradabile");
    }

    @GetMapping("/monthlyReport")
    public String monthlyReport(@RequestParam(name = "name", required = false, defaultValue = "Stefan") String name, Model model) {
        model.addAttribute("name", name);
        return "monthlyReport";
    }

    @GetMapping("/trimestrialReport")
    public String trimestrialReport(@RequestParam(name = "name", required = false, defaultValue = "Stefan") String name, Model model) {
        model.addAttribute("name", name);
        return "trimestrialReport";
    }

    @GetMapping("/anualReport")
    public String anualReport(@RequestParam(name = "name", required = false, defaultValue = "Stefan") String name, Model model) {
        model.addAttribute("name", name);
        return "anualReport";
    }

    @GetMapping("/paymentReport")
    public String paymentReport(@RequestParam(name = "name", required = false, defaultValue = "Stefan") String name, Model model) {
        model.addAttribute("name", name);
        return "paymentReport";
    }

    @GetMapping("/reportsArchive")
    public String reportsArchive(@RequestParam(name = "name", required = false, defaultValue = "Stefan") String name, Model model) {
        try {
            List<File> files = Files.list(Paths.get("/usr/greendays/src/main/resources/zipuri")).map(path -> {
                com.greendays.greendays.model.dto.File file = new File();
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
            model.addAttribute("files", files);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("name", name);

        return "reportsArchive";
    }


    @GetMapping("/tabelArhiva")
    public String archiveTable(@RequestParam(name = "name", required = false, defaultValue = "Stefan") String name, Model model) {
        model.addAttribute("name", name);
        return "tabelArhiva";
    }

    @GetMapping("/generareRapoarte")
    public String generareRapoarte(@RequestParam(name = "name", required = false, defaultValue = "Stefan") String name, Model model) {
        model.addAttribute("name", name);
        return "generareRapoarte";
    }

}
