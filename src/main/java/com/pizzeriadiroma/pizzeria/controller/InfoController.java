package com.pizzeriadiroma.pizzeria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InfoController {

    @GetMapping("/terms")
    public String termsPage() {
        return "info/terms";
    }

    @GetMapping("/privacy")
    public String privacyPage() {
        return "info/privacy";
    }
}