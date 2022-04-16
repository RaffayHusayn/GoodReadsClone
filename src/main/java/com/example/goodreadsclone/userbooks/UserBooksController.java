package com.example.goodreadsclone.userbooks;

import com.example.goodreadsclone.user.BooksByUser;
import com.example.goodreadsclone.user.BooksByUserRepository;
import com.example.goodreadsclone.work.Work;
import com.example.goodreadsclone.work.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.endpoint.AbstractWebClientReactiveOAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Optional;

@Controller
public class UserBooksController {

    private UserBooksRepository userBooksRepository;
    private BooksByUserRepository booksByUserRepository;
    private WorkRepository workRepository;

    @Autowired
    public void setUserBooksRepository(UserBooksRepository userBooksRepository) {
        this.userBooksRepository = userBooksRepository;
    }

    @Autowired
    public void setBooksByUserRepository(BooksByUserRepository booksByUserRepository){
        this.booksByUserRepository = booksByUserRepository;
    }

    @Autowired
    public void setWorkRepository(WorkRepository workRepository){
        this.workRepository = workRepository;
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


        //--------------------- Saving the data in the UserBooks Cassandra table-------------------------//
        userBooksRepository.save(userBooks);

        String bookId = formData.getFirst("bookId");
        Optional<Work> workOptional= workRepository.findById(bookId);
        if(!workOptional.isPresent()){
            return new ModelAndView("redirect:/");
        }
        Work work = workOptional.get();

        // time based UUID is already being set at the time of construction in the constructor
        BooksByUser booksByUser = new BooksByUser();
        booksByUser.setBookId(bookId);
        booksByUser.setId(principal.getAttribute("login"));
        booksByUser.setBookName(work.getTitle());
        booksByUser.setAuthorName(work.getAuthorNames());
        booksByUser.setCoverIds(work.getCoverIds());
        booksByUser.setReadingStatus(formData.getFirst("readingStatus"));
        booksByUser.setRating(Integer.parseInt(formData.getFirst("rating")));


        System.out.println(work.getCoverIds());
        System.out.println(work.getTitle());

        booksByUserRepository.save(booksByUser);



        return new ModelAndView("redirect:/book/"+formData.getFirst("bookId"));
    }
}
