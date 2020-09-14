package ua.com.prim.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.*;

@Entity
@Table(name = "usr")
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String activationCode;

    private String password;

    @Column(nullable = false)
    private String email;

    private String mainPhoto;

    @Temporal(TemporalType.DATE)
    private Date registrationDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private State state;

    @ElementCollection
    private List<String> music = new ArrayList<>();

    @ElementCollection
    private List<String> images = new ArrayList<>();

    @ElementCollection
    private List<String> videos = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "users_friends", joinColumns = {@JoinColumn(name = "channel_id")},
            inverseJoinColumns = {@JoinColumn(name = "friends_ID")})
    private List<MyUser> friends = new ArrayList<>();

    public void addToFriend(MyUser userWhomWantToAdd) {
        friends.add(userWhomWantToAdd);
    }
    public void addToMusic(String musicThatWantToAdd){music.add(musicThatWantToAdd);}
    public void addToImage(String imageThatWantToAdd){images.add(imageThatWantToAdd);}
    public void addToVideo(String videoThatWantToAdd){videos.add(videoThatWantToAdd);}
    public void deleteToFriend(MyUser userWhomWantToDelete) { friends.remove(userWhomWantToDelete); }
    public void deleteToMusic(String musicThatWantToDelete){music.remove(musicThatWantToDelete);}
    public void deleteToImage(String imageThatWantToDelete){images.remove(imageThatWantToDelete);}
    public void deleteToVideo(String videoThatWantToDelete){videos.remove(videoThatWantToDelete);}


    public enum Role implements GrantedAuthority {
        USER, ADMIN, DELETE, BANNED;

        @Override
        public String getAuthority() {
            return "ROLE_" + name();
        }
    }

    public enum State {
        DELETE, ACTIVE, BANNED;

        @Override
        public String toString() {
            return "STATE_" + name();
        }
    }

    public static class Builder {
        private MyUser newUser;

        public Builder() {
            newUser = new MyUser();
        }
        public Builder password(String password) {
            newUser.password = password;
            return this;
        }
        public Builder name(String name) {
            newUser.name = name;
            return this;
        }
        public Builder role(Role role) {
            newUser.role = role;
            return this;
        }
        public Builder state(State state) {
            newUser.state = state;
            return this;
        }
        public Builder email(String email) {
            newUser.email = email;
            return this;
        }
        public Builder mainPhoto(String mainPhoto) {
            newUser.mainPhoto = mainPhoto;
            return this;
        }
        public Builder registrationDate(Date registrationDate) {
            newUser.registrationDate = registrationDate;
            return this;
        }
        public MyUser build() {
            return newUser;
        }
    }

    public List<MyUser> getFriends() {
        return friends;
    }
    public void setFriends(List<MyUser> friends) {
        this.friends = friends;
    }
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
    public String getMainPhoto() {
        return mainPhoto;
    }
    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }
    public Date getRegistrationDate() {
        return registrationDate;
    }
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public State getState() {
        return state;
    }
    public void setState(State state) {
        this.state = state;
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
    public List<String> getVideos() {
        return videos;
    }
    public void setVideos(List<String> videos) {
        this.videos = videos;
    }
    public String getActivationCode() {
        return activationCode;
    }
    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public MyUser() {
    }
}
