package ua.com.prim.servise;

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
    void addAndDeleteMusic(String actionThatNeedToDo, String musicNameForWork);
    void addAndDeleteImage(String actionThatNeedToDo, String imageNameForWork);
    void addAndDeleteVideo(String actionThatNeedToDo, String videoNameForWork);
    void addAndDeleteFriends(String whatNeedToDo, Long userIdForWork);
    boolean passwordRecovery(String emailThatNeedToRecovery);
    MyUser findByActivationCode(String activationCode);
    boolean changePassword(Long userIdWhomNeedToChangePassword, String newPassword);
    void googleAuth(String emailUserThatNeedFind);
    void deleteAccount(Long userId);
    void changeEmailOrPassword(String email, String password);
    boolean accountRecovery(String emailForAccountRecovery);
    void banOrUnban(Long userId, String actionThanNeedToDo);
    MyUser findByEmail(String email);
    void addOrDeleteImageOrMusicOrVideo(String whatNeedToDo, String naming);
    Iterable<MyUser> findUsersByName(String name);
}
