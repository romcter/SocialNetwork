package ua.com.prim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.com.prim.entity.MyUser;
import ua.com.prim.service.MyUserServiceImpl;

@Controller
public class AdminController {

    @Autowired
    private MyUserServiceImpl myUserServiceImpl;

    @GetMapping("/admin")
    public String returnAdminPage(Model model){
        model.addAttribute("currentUser", getCurrentUser());
        model.addAttribute("allUser", myUserServiceImpl.findAll());
        return "admin";
    }
    @GetMapping("/admin/{banOrUnban}/{userId}")
    public String banOrUnban(@PathVariable("banOrUnban") String actionThanNeedToDo,
                             @PathVariable Long userId,
                             Model model){
        myUserServiceImpl.banOrUnban(userId, actionThanNeedToDo);
        model.addAttribute("currentUser", getCurrentUser());
        model.addAttribute("allUser", myUserServiceImpl.findAll());
        return "admin";
    }

    private MyUser getCurrentUser(){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        return myUserServiceImpl.findByLogin(username);
    }
}
