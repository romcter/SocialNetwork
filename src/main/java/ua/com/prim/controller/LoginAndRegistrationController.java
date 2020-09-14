package ua.com.prim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.prim.dto.MyUserDto;
import ua.com.prim.entity.MyUser;
import ua.com.prim.servise.MyUserImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class LoginAndRegistrationController {

    @Autowired
    private MyUserImpl myUserImpl;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage(Model model, HttpServletRequest request) {
        if(request.getParameterMap().containsKey("error")) {
            model.addAttribute("userEmpty", true);
        }
        return "loginAndRegistration";
    }
    @PostMapping("/registration")
    public String registrPost(
            @RequestParam(name = "password") String password,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "email") String email,
            Model model) {
        if (myUserImpl.existsByEmail(email)) {
            model.addAttribute("emailEr", true);
            return "loginAndRegistration";
        }
        String pass = passwordEncoder.encode(password);
        MyUserDto userDto = new MyUserDto();
        userDto.setEmail(email);
        userDto.setName(name);
        userDto.setRegistrationDate(new Date());
        userDto.setPassword(pass);
        userDto.setRole(MyUser.Role.USER);
        userDto.setState(MyUser.State.ACTIVE);
        myUserImpl.addMyUser(userDto);
        return "loginAndRegistration";
    }
}


