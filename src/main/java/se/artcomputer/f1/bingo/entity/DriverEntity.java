package se.artcomputer.f1.bingo.entity;

import jakarta.persistence.*;
import se.artcomputer.f1.bingo.domain.Country;
import se.artcomputer.f1.bingo.domain.DriverCode;

import java.util.Date;

@Entity
@Table(name = "driver")
public class DriverEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "code")
    @Enumerated(EnumType.STRING)
    private DriverCode code;

    @Column(name = "number")
    private int number;

    @Column(name = "yellow_cam")
    private boolean yellowCam;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "nationality")
    @Enumerated(EnumType.STRING)
    private Country nationality;

    @Column(name = "length")
    private int length;

    @Column(name = "born")
    private Date born;

    public Date getBorn() {
        return born;
    }

    public void setBorn(Date born) {
        this.born = born;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isYellowCam() {
        return yellowCam;
    }

    public void setYellowCam(boolean yellowCam) {
        this.yellowCam = yellowCam;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public DriverCode getCode() {
        return code;
    }

    public void setCode(DriverCode code) {
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
