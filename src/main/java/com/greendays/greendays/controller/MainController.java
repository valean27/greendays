package com.greendays.greendays.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("/home")
    public String index() {
        return "index";
    }

    @GetMapping("/rapoarte")
    public String rapoarte(@RequestParam(name = "name", required = false, defaultValue = "Stefan")String name, Model model) {
        model.addAttribute("name", name);
        return "rapoarte";
    }

    @GetMapping("/statistici")
    public String statistici() {
        return "statistici";
    }

    @GetMapping("/export")
    public String export() {
        return "export";
    }
}
