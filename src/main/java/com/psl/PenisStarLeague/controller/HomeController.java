package com.psl.PenisStarLeague.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(){
        // model.addAttribute("userName", user.getAttribute("name"));
        return "index";

    }
}
