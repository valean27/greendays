package com.greendays.greendays.controller;

import com.greendays.greendays.model.Client;
import com.greendays.greendays.model.DailyReport;
import com.greendays.greendays.model.Garbage;
import com.greendays.greendays.service.ClientService;
import com.greendays.greendays.service.DailyReportService;
import com.greendays.greendays.service.GarbageService;
import com.greendays.greendays.service.IncidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
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
        List<String> clientTypes = clientService.getClients().stream().map(Client::getClientType).collect(Collectors.toList());
        List<Garbage> garbageList = garbageService.getGarbages();
        model.addAttribute("clientTypes", clientTypes);
        model.addAttribute("garbageList", garbageList);
        return "dailyReport";
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
