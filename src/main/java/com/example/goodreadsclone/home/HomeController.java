package com.example.goodreadsclone.home;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal OAuth2User principal){
        // if the user is not logged in, show them index page
        if(principal == null || principal.getAttribute("login") == null){
            return "index";
        }
        else return "home";
    }
}
