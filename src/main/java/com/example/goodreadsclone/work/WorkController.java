package com.example.goodreadsclone.work;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class WorkController {

    private final String COVER_IMAGE_ROOT = "https://covers.openlibrary.org/b/id/";
    private WorkRepository workRepository;
    @Autowired
    public void setWorkRepository(WorkRepository workRepository){
        this.workRepository= workRepository;
    }

    @GetMapping("/home")
    public String getHome(){
        return "home";
    }

    @GetMapping("/book/{bookId}")
    public String getBook(@PathVariable String bookId, Model model){
        Optional<Work> bookOptional = workRepository.findById(bookId);
        if(bookOptional.isPresent()){
            Work book = bookOptional.get();
            String coverImage ="/images/no-image.png";
            if(book.getCoverIds() != null && book.getCoverIds().size()>0){
                coverImage = COVER_IMAGE_ROOT+book.getCoverIds().get(0)+"-L.jpg";
            }
            model.addAttribute("book", book);
            model.addAttribute("coverImage" , coverImage);
            return "book";
        }else {
            return "book-not-found";
        }

    }


}
