package ua.com.prim.dto;

import ua.com.prim.entity.*;

import java.util.Date;
import java.util.List;

public class MyUserDto {

    private Long id;
    private String name;
    private String password;
    private String email;
    private Date registrationDate;
    private List<String> music;
    private List<String> images;
    private MyUser.Role role;
    private MyUser.State state;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Date getRegistrationDate() {
        return registrationDate;
    }
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
    public List<String> getMusic() {
        return music;
    }
    public void setMusic(List<String> music) {
        this.music = music;
    }
    public List<String> getImages() {
        return images;
    }
    public void setImages(List<String> images) {
        this.images = images;
    }
    public MyUser.Role getRole() {
        return role;
    }
    public void setRole(MyUser.Role role) {
        this.role = role;
    }
    public MyUser.State getState() {
        return state;
    }
    public void setState(MyUser.State state) {
        this.state = state;
    }
}
