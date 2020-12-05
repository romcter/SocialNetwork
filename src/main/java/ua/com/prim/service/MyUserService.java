package ua.com.prim.service;

import ua.com.prim.dto.MyUserDto;
import ua.com.prim.entity.MyUser;

import java.util.List;
import java.util.Optional;

public interface MyUserService {
    void addMyUser(MyUserDto user);
    boolean existsByEmail(String Email);
    MyUser findByLogin(String login);
    void save(MyUser user);
    List<MyUser> findAll();
    Optional<MyUser> findById(Long id);
    void addOrDeleteMusic(String actionThatNeedToDo, String musicNameForWork);
    void addOrDeleteImage(String actionThatNeedToDo, String imageNameForWork);
    void addOrDeleteVideo(String actionThatNeedToDo, String videoNameForWork);
    void addOrDeleteFriends(String whatNeedToDo, Long userIdForWork);
    boolean passwordRecovery(String emailThatNeedToRecovery);
    MyUser findByActivationCode(String activationCode);
    boolean changePassword(Long userIdWhomNeedToChangePassword, String newPassword);
    void googleAuth(String emailUserThatNeedFind);
    void deleteAccount(Long userId);
    void changeEmailOrPassword(String email, String password);
    boolean accountRecovery(String emailForAccountRecovery);
    void banOrUnban(Long userId, String actionThanNeedToDo);
    void addOrDeleteImageOrMusicOrVideo(String whatNeedToDo, String naming);
    Iterable<MyUser> findUsersByName(String name);
}
