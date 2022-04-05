package com.example.goodreadsclone.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Controller
public class SearchController {

    private final WebClient webClient;

    public SearchController(WebClient.Builder webClientBuilder) {
        //making use of the Search API available in the openLibarary website
        this.webClient = webClientBuilder.baseUrl("http://openlibrary.org/search/authors.json").build();
    }


    @GetMapping("/search/{query}")
    public String getSearchResults(@PathVariable String query, Model model){
        Mono<SearchResult> searchResultMono= this.webClient.get()
                .uri("?q={query}", query).retrieve().bodyToMono(SearchResult.class);
        SearchResult searchResult = searchResultMono.block();
        model.addAttribute("searchResult", searchResult);
        return "search";
    }
}
