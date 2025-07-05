package com.andre.librarycompass.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping
    public String directToHome(){
        return "redirect:/livros";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/accessDenied")
    public String acessDenied(){
        return "accessDenied";
    }
}
