package com.example.goodreadsclone.work;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class WorkController {

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
            model.addAttribute("title", book.getTitle());
            model.addAttribute("authorsList", book.getAuthorNames());
            model.addAttribute("pubishedDate", book.getPublishedDate());
            return "book";
        }else {
            return "book-not-found";
        }

    }


}
