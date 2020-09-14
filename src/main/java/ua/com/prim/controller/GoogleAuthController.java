package ua.com.prim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.com.prim.servise.MyUserImpl;

@Controller
public class GoogleAuthController {

    @Autowired
    private MyUserImpl myUserImpl;

    @GetMapping("/googleAuth/{emailUser}")
    public String googleAuth(@PathVariable("emailUser")String emailUser, Model model){
        myUserImpl.googleAuth(emailUser);
        model.addAttribute("successAuth", true);
        return "loginAndRegistration";
    }
}
