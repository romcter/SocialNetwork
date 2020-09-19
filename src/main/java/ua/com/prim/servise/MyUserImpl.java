package ua.com.prim.servise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.com.prim.dto.MyUserDto;
import ua.com.prim.entity.MyUser;
import ua.com.prim.repository.MyUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class MyUserImpl implements MyUserService {

    @Autowired
    private MyUserRepository myUserRepository;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void addMyUser(MyUserDto userDto) {
        MyUser user = new MyUser();
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setRegistrationDate(userDto.getRegistrationDate());
        user.setRole(userDto.getRole());
        user.setState(userDto.getState());
        user.setMainPhoto("/img/avatarka-pustaya-vk_0.jpg");
        myUserRepository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return myUserRepository.existsByEmail(email);
    }

    @Override
    public MyUser findByLogin(String login) {
        return myUserRepository.findByEmail(login);
    }

    @Override
    public void save(MyUser user) {
        myUserRepository.save(user);
    }

    @Override
    public List<MyUser> findAll() {
        return myUserRepository.findAll();
    }

    @Override
    public Optional<MyUser> findById(Long id) {
        return myUserRepository.findById(id);
    }

    @Override
    public void addAndDeleteMusic(String actionThatNeedToDo, String musicNameForWork) {
        MyUser currentUser = getCurrentUser();
        if (actionThatNeedToDo.equals("add")) currentUser.getMusic().add(musicNameForWork);
        else currentUser.getMusic().remove(musicNameForWork);
        myUserRepository.save(currentUser);
    }

    @Override
    public void addAndDeleteImage(String actionThatNeedToDo, String imageNameForWork) {
        MyUser currentUser = getCurrentUser();
        if (actionThatNeedToDo.equals("add")) currentUser.getImages().add(imageNameForWork);
        else currentUser.getImages().remove(imageNameForWork);
        myUserRepository.save(currentUser);
    }

    @Override
    public void addAndDeleteVideo(String actionThatNeedToDo, String videoNameForWork) {
        MyUser currentUser = getCurrentUser();
        if (actionThatNeedToDo.equals("add")) currentUser.getVideos().add(videoNameForWork);
        else currentUser.getVideos().remove(videoNameForWork);
        myUserRepository.save(currentUser);
    }

    @Override
    public void addAndDeleteFriends(String whatNeedToDo, Long userIdForWork) {
        MyUser currentUser = getCurrentUser();
        Optional<MyUser> userWithWhomToWork = myUserRepository.findById(userIdForWork);
        if (whatNeedToDo.equals("add")) {
            currentUser.getFriends().add(userWithWhomToWork.get());
            userWithWhomToWork.get().getFriends().add(currentUser);
        } else {
            currentUser.getFriends().remove(userWithWhomToWork.get());
            userWithWhomToWork.get().getFriends().remove(currentUser);
        }
        myUserRepository.save(currentUser);
        myUserRepository.save(userWithWhomToWork.get());
    }

    @Override
    public boolean passwordRecovery(String emailThatNeedToRecovery) {
        if (myUserRepository.existsByEmail(emailThatNeedToRecovery)) {
            String activationCode = UUID.randomUUID().toString();
            MyUser currentUser = myUserRepository.findByEmail(emailThatNeedToRecovery);
            currentUser.setActivationCode(activationCode);
            String message = String.format("Hello ,%s! \n" +
                            "Welcome to Priman. Visit next link: http://localhost:8888/passwordRecovery/%s",
                    currentUser.getName(),
                    activationCode);
            mailSender.send(emailThatNeedToRecovery, "Activation code", message);
            myUserRepository.save(currentUser);
            return true;
        }
        return false;
    }

    @Override
    public MyUser findByActivationCode(String activationCode) {
        MyUser currentUser = myUserRepository.findByActivationCode(activationCode);
        return currentUser;
    }

    @Override
    public boolean changePassword(Long userIdWhomNeedToChangePassword, String newPassword) {
        MyUser currentUser = myUserRepository.findById(userIdWhomNeedToChangePassword).get();
        currentUser.setPassword(passwordEncoder.encode(newPassword));
        myUserRepository.save(currentUser);
        return true;
    }

    @Override
    public void googleAuth(String emailUserThatNeedFind) {
        MyUser currentUser = myUserRepository.findByEmail(emailUserThatNeedFind);
        String futurePassword = passwordGenerator();
        currentUser.setPassword(passwordEncoder.encode(futurePassword));
        String message = String.format("Hello ,%s! \n" +
                        "Welcome to Priman. Your login: %s, and password: %s.\n " +
                        "Follow the next link to visit our site: http://localhost:8888/login",
                currentUser.getName(),
                currentUser.getEmail(),
                futurePassword);
        mailSender.send(currentUser.getEmail(), "Activation code", message);
        myUserRepository.save(currentUser);
    }

    @Override
    public void deleteAccount(Long userId) {
        MyUser currentUser = myUserRepository.findById(userId).get();
        currentUser.setRole(MyUser.Role.DELETE);
        myUserRepository.save(currentUser);
    }

    @Override
    public boolean accountRecovery(String emailForAccountRecovery) {
        MyUser currentUser = myUserRepository.findByEmail(emailForAccountRecovery);
        if (currentUser != null) {
            String futurePassword = passwordGenerator();
            String message = String.format("Hello, %s! \n" +
                            "We change your last password on \"%s\". " +
                            "Visit our site if you want to enter in your page http://localhost:8888/login",
                    currentUser.getName(),
                    futurePassword);
            currentUser.setRole(MyUser.Role.USER);
            currentUser.setPassword(passwordEncoder.encode(futurePassword));
            mailSender.send(emailForAccountRecovery, "Activation code", message);
            myUserRepository.save(currentUser);
            return true;
        }
        return false;
    }

    @Override
    public void banOrUnban(Long userId, String actionThanNeedToDo) {
        MyUser userWhomWantToBanOrUnban = myUserRepository.findById(userId).get();
        if (actionThanNeedToDo.equals("banned")) userWhomWantToBanOrUnban.setRole(MyUser.Role.BANNED);
        else userWhomWantToBanOrUnban.setRole(MyUser.Role.USER);
        myUserRepository.save(userWhomWantToBanOrUnban);
    }

    @Override
    public MyUser findByEmail(String email) {
        return myUserRepository.findByEmail(email);
    }

    @Override
    public void addOrDeleteImageOrMusicOrVideo(String whatNeedToDo, String naming) {
        MyUser currentUser = getCurrentUser();
        if (whatNeedToDo.equals("addImage")) currentUser.getImages().add(naming);
        if (whatNeedToDo.equals("deleteImage")) currentUser.getImages().remove(naming);
        if (whatNeedToDo.equals("addVideo")) currentUser.getVideos().add(naming);
        if (whatNeedToDo.equals("deleteVideo")) currentUser.getVideos().remove(naming);
        if (whatNeedToDo.equals("addMusic")) currentUser.getMusic().add(naming);
        if (whatNeedToDo.equals("deleteMusic")) currentUser.getMusic().remove(naming);
        myUserRepository.save(currentUser);
    }

    @Override
    public Iterable<MyUser> findUsersByName(String name) {
        return myUserRepository.findAllByName(name);
    }

    @Override
    public void changeEmailOrPassword(String newEmail, String newPassword) {
        MyUser currentUser = getCurrentUser();
        if (newEmail != null) currentUser.setEmail(newEmail);
        else currentUser.setPassword(passwordEncoder.encode(newPassword));
        myUserRepository.save(currentUser);
    }

    private MyUser getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return myUserRepository.findByEmail(user.getUsername());
    }

    private String passwordGenerator() {
        PasswordGenerator pg = new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)
                .useUpper(true)
                .useLower(true)
                .build();
        return pg.generate(5);
    }
}
