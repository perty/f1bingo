package se.artcomputer.f1.bingo.domain;

import org.springframework.stereotype.Service;
import se.artcomputer.f1.bingo.controller.NewsDto;
import se.artcomputer.f1.bingo.entity.NewsEntity;
import se.artcomputer.f1.bingo.repository.NewsRepository;

import java.util.List;

@Service
public class NewsService {

    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public List<NewsDto> getNews() {
        return newsRepository.findAll().stream()
                .map(newsEntity -> new NewsDto(
                        newsEntity.timestamp(),
                        newsEntity.content()
                ))
                .sorted((o1, o2) -> o2.timestamp().compareTo(o1.timestamp()))
                .toList();
    }

    public void postNews(NewsDto newsDto) {
        newsRepository.save(new NewsEntity(newsDto.content(), newsDto.timestamp()));
    }
}
