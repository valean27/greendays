package com.greendays.greendays.controller;

import com.greendays.greendays.model.Client;
import com.greendays.greendays.model.DailyReport;
import com.greendays.greendays.model.Garbage;
import com.greendays.greendays.model.Incident;
import com.greendays.greendays.service.ClientService;
import com.greendays.greendays.service.DailyReportService;
import com.greendays.greendays.service.GarbageService;
import com.greendays.greendays.service.IncidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ClientService clientService;
    private final DailyReportService dailyReportService;
    private final GarbageService garbageService;
    private final IncidentService incidentService;

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
    public String postDailyReport(@RequestParam MultiValueMap<String,String> paramMap, Model model){
        initializeDailyReportModel(model);
        DailyReport report = populateReportFromParamMap(paramMap);
        String result = dailyReportService.postDailyReport(report);
        model.addAttribute("result", result);
        return "dailyReport";
    }

    private DailyReport populateReportFromParamMap(MultiValueMap<String, String> paramMap) {
        DailyReport report = new DailyReport();
        Client client = new Client();
        client.setClientType(paramMap.getFirst("client"));
        report.setClient(client);
        report.setDate(Date.valueOf(Objects.requireNonNull(paramMap.getFirst("data"))));
        report.setDestination(paramMap.getFirst("destinatie"));
        report.setDriverName(paramMap.getFirst("numeSofer"));
        report.setProblems(paramMap.getFirst("alteProbleme"));
        report.setRouteNumber(Integer.valueOf(Objects.requireNonNull(paramMap.getFirst("numarTraseu"))));
        report.setQuantity(Double.valueOf(Objects.requireNonNull(paramMap.getFirst("cantitate"))));
        Incident incident = new Incident();
        incident.setIncidentType(paramMap.getFirst("incident"));
        incident.setObservations(paramMap.getFirst("observatii"));
        report.setIncident(incident);
        Garbage garbage = new Garbage();
        garbage.setGarbageName(paramMap.getFirst("denumireDeseu"));
        garbage.setGarbageCode(paramMap.getFirst("codDeseu"));
        report.setGarbage(garbage);
        report.setUat(paramMap.getFirst("uat"));
        return report;
    }

    private void initializeDatabase() {
        clientService.insertClient("Casnic");
        clientService.insertClient("Non-Casnic");
        clientService.insertClient("Mixt");
        garbageService.insertGarbage("20 01 01", "Hartie si Carton");
        garbageService.insertGarbage("20 01 02", "Sticla");
        garbageService.insertGarbage("20 01 08", "Deseuri Biodegradabile");
    }

    @GetMapping("/monthlyReport")
    public String monthlyReport(@RequestParam(name = "name", required = false, defaultValue = "Stefan")String name, Model model) {
        model.addAttribute("name", name);
        return "monthlyReport";
    }

    @GetMapping("/trimestrialReport")
    public String trimestrialReport(@RequestParam(name = "name", required = false, defaultValue = "Stefan")String name, Model model) {
        model.addAttribute("name", name);
        return "trimestrialReport";
    }

    @GetMapping("/anualReport")
    public String anualReport(@RequestParam(name = "name", required = false, defaultValue = "Stefan")String name, Model model) {
        model.addAttribute("name", name);
        return "anualReport";
    }
}
