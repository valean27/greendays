package com.greendays.greendays.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/dailyReport")
    public String dailyReport(@RequestParam(name = "name", required = false, defaultValue = "Stefan")String name, Model model) {
        model.addAttribute("name", name);
        return "dailyReport";
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
