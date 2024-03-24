package se.artcomputer.f1.bingo.controller;

import org.springframework.web.bind.annotation.*;
import se.artcomputer.f1.bingo.domain.NewsService;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public List<NewsDto> news() {
        return newsService.getNews();
    }

    @PostMapping
    public void postNews(@RequestBody NewsRequest request) {
        newsService.postNews(new NewsDto(Date.from(Instant.now()), request.content()));
    }
}
