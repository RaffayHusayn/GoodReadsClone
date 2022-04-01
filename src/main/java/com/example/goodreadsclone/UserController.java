package com.example.goodreadsclone;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @RequestMapping("/user")
    public String user(@AuthenticationPrincipal OAuth2User principal){
        System.out.println(principal);
        return "Getting username from OAuth github: "+principal.getAttribute("name");
    }

}
