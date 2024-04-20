package se.artcomputer.f1.bingo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "session_schedule")
public class SessionSchedule {
    @Id
    private Long id;

    @Column(name = "summary")
    private String summary;

    @Column(name = "location")
    private String location;

    @Column(name = "starttime")
    private Date startTime;

    @Column(name = "endtime")
    private Date endTime;

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }
}
