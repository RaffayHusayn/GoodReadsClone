package com.example.goodreadsclone.userbooks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserBooksController {

    private UserBooksRepository userBooksRepository;
    @Autowired
    public void setUserBooksRepository(UserBooksRepository userBooksRepository){
        this.userBooksRepository = userBooksRepository;
    }

//    @PostMapping("/addUserBook")
//    public String addBookForUser(@AuthenticationPrincipal OAuth2User principal){
//
//    }
}
