package com.fibersim.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
public class ViewController {
    @GetMapping("")
    public String mainPage(Model model) {
        log.info("Serving view mainPage");

        return "index";
    }

    @GetMapping("/raytracing/{id}")
    public String viewSimulation(Model model, @PathVariable String id) {
        log.info("Serving view viewSimulation");

        return "simulation";
    }
}
