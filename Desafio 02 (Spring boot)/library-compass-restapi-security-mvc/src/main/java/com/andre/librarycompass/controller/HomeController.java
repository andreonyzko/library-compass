package com.andre.librarycompass.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // redirect "/" to "/livros"
    @GetMapping
    public String directToHome(){
        return "redirect:/livros";
    }

    // display login form page
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    // display accessDenied page
    @GetMapping("/accessDenied")
    public String accessDenied(){
        return "accessDenied";
    }
}
