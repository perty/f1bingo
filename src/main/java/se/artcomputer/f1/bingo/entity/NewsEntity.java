package se.artcomputer.f1.bingo.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "news")
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "timestamp")
    private Date timestamp;

    public NewsEntity() {
    }

    public NewsEntity(String content, Date timestamp) {
        this.content = content;
        this.timestamp = timestamp;
    }

    public String content() {
        return content;
    }

    public Date timestamp() {
        return timestamp;
    }
}
