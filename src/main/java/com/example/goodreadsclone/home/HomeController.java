package com.example.goodreadsclone.home;

import com.example.goodreadsclone.user.BooksByUser;
import com.example.goodreadsclone.user.BooksByUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.awt.print.Book;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class HomeController {

    private final String COVER_IMAGE_ROOT = "https://covers.openlibrary.org/b/id/";

    private BooksByUserRepository booksByUserRepository;
    @Autowired
    public void setBooksByUserRepository(BooksByUserRepository booksByUserRepository){
        this.booksByUserRepository = booksByUserRepository;
    }
    @GetMapping("/")
    public String home(@AuthenticationPrincipal OAuth2User principal, Model model){
        // if the user is not logged in, show them index page
        if(principal == null || principal.getAttribute("login") == null){
            return "index";
        }

        String userId = principal.getAttribute("login");
        Slice<BooksByUser> booksSlice = booksByUserRepository.findAllById(userId, CassandraPageRequest.of(0, 50));
        List<BooksByUser> booksByUsers = booksSlice.getContent();
        booksByUsers = booksByUsers.stream().distinct().map(
                book->{
                    String coverImageUrl = "/images/no-image.png";
                 if (book.getCoverIds() != null && book.getCoverIds().size() > 0){
                     coverImageUrl = COVER_IMAGE_ROOT+book.getCoverIds().get(0) +"-M.jpg";
                 }
                 book.setCoverUrl(coverImageUrl);
                 return book;
                }).collect(Collectors.toList());
        model.addAttribute("books", booksByUsers);
        return "home";
    }
}
