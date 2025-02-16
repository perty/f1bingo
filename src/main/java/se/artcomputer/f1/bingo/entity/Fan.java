package se.artcomputer.f1.bingo.entity;

import jakarta.persistence.*;
import se.artcomputer.f1.bingo.domain.FanName;

import java.util.Date;

@Entity
@Table(name = "fan")
public class Fan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String name;
    private String password;
    private String roles;
    private Date lastRead;

    public Fan() {
    }

    public Fan(String email, String roles, String encode) {
        this.name = email;
        this.roles = roles;
        this.password = encode;
    }
//    private Date lastOpenedTime;

//    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
//    @JoinTable(
//        name = "fan_card",
//        joinColumns = @JoinColumn(name = "fan_id"),
//        inverseJoinColumns = @JoinColumn(name = "card_id")
//    )
//    private List<Card> album;

//    public Date getLastOpenedTime() {
//        return lastOpenedTime;
//    }
//
//    public void setLastOpenedTime(Date lastOpenedTime) {
//        this.lastOpenedTime = lastOpenedTime;
//    }

    public String getName() {
        return name;
    }

    public void setName(String email) {
        this.name = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Date getLastRead() {
        return lastRead;
    }

    public void setLastRead(Date lastReadTime) {
        this.lastRead = lastReadTime;
    }

    public FanName getFanName() {
        return new FanName(name);
    }

//    public List<Card> getAlbum() {
//        return album;
//    }
//
//    public void setAlbum(List<Card> album) {
//        this.album = album;
//    }
}
