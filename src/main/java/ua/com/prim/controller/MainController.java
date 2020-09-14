package ua.com.prim.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.prim.entity.MyUser;
import ua.com.prim.servise.MyUserImpl;

@Controller
public class MainController {

    @Autowired
    private MyUserImpl myUserImpl;

    @RequestMapping("/chat")
    public String index(Model model) {
        MyUser currentUser = getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        return "chat";
    }
    private MyUser getCurrentUser(){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        return myUserImpl.findByLogin(username);
    }

}
