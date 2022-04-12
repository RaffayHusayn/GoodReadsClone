package com.example.goodreadsclone.userbooks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@Controller
public class UserBooksController {

    private UserBooksRepository userBooksRepository;

    @Autowired
    public void setUserBooksRepository(UserBooksRepository userBooksRepository) {
        this.userBooksRepository = userBooksRepository;
    }

    @PostMapping("/addUserBook")
    public ModelAndView addBookForUser(@RequestBody MultiValueMap<String, String> formData, @AuthenticationPrincipal OAuth2User principal) {
        UserBooks userBooks = new UserBooks();
        UserBooksPrimaryKey userBooksPrimaryKey = new UserBooksPrimaryKey();
        if (principal == null || principal.getAttribute("login") == null) {
            //just go back if the user is not logged in and for some reason this controller is called
            return null;
        }
        userBooksPrimaryKey.setUserId(principal.getAttribute("login"));
        userBooksPrimaryKey.setBookId(formData.getFirst("bookId"));
        userBooks.setKey(userBooksPrimaryKey);
        if (formData.getFirst("startDate") != null) {
            userBooks.setDateStarted(LocalDate.parse(formData.getFirst("startDate")));
        }
        if (formData.getFirst("completedDate") !=null) {
            userBooks.setDateCompleted(LocalDate.parse(formData.getFirst("completedDate")));
        }
        if(formData.getFirst("rating")!= null){
            userBooks.setRating(Integer.parseInt(formData.getFirst("rating")));
        }
        if(formData.getFirst("readingStatus")!= null) {
            userBooks.setReadingStatus(formData.getFirst("readingStatus"));
        }


        userBooksRepository.save(userBooks);

        return new ModelAndView("redirect:/book/"+formData.getFirst("bookId"));
    }
}
