package com.example.goodreadsclone.work;

import com.example.goodreadsclone.userbooks.UserBooks;
import com.example.goodreadsclone.userbooks.UserBooksPrimaryKey;
import com.example.goodreadsclone.userbooks.UserBooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class WorkController {

    private final String COVER_IMAGE_ROOT = "https://covers.openlibrary.org/b/id/";
    private WorkRepository workRepository;
    private UserBooksRepository userBooksRepository;
    @Autowired
    public void setWorkRepository(WorkRepository workRepository){
        this.workRepository= workRepository;
    }
    @Autowired
    public void setUserBooksRepository(UserBooksRepository userBooksRepository){ this.userBooksRepository = userBooksRepository;}


    @GetMapping("/book/{bookId}")
    public String getBook(@PathVariable String bookId, Model model, @AuthenticationPrincipal OAuth2User principal){
        Optional<Work> bookOptional = workRepository.findById(bookId);
        if(bookOptional.isPresent()){
            Work book = bookOptional.get();
            String coverImage ="/images/no-image.png";
            if(book.getCoverIds() != null && book.getCoverIds().size()>0){
                coverImage = COVER_IMAGE_ROOT+book.getCoverIds().get(0)+"-L.jpg";
            }
            model.addAttribute("book", book);
            model.addAttribute("coverImage" , coverImage);
            if(principal != null && principal.getAttribute("login")!= null){
                // if the user if logged in do the following:
                //1. Send the loginId of the user to the book template
                model.addAttribute("loginId", principal.getAttribute("login"));
                //2. Send the UserBooks object to the book template containing all the previous user interaction with this book
                // like rating, reading status, startedDate, completedDate
                UserBooksPrimaryKey primaryKey = new UserBooksPrimaryKey();
                primaryKey.setBookId(bookId);
                primaryKey.setUserId(principal.getAttribute("login"));
                Optional<UserBooks> userBooksOptional =  userBooksRepository.findById(primaryKey);
                if(userBooksOptional.isPresent()){
                    model.addAttribute("userBooks", userBooksOptional.get());
                }else{
                    model.addAttribute("userBooks", new UserBooks());
                }

            }
            return "book";
        }else {
            return "book-not-found";
        }

    }


}
