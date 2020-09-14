package ua.com.prim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.com.prim.entity.MyUser;
import ua.com.prim.servise.MyUserImpl;

@Controller
public class SearchPeopleController {

    @Autowired
    private MyUserImpl myUserImpl;

    @GetMapping("/people")
    public String searchPeople(Model model){
        Iterable<MyUser> users = myUserImpl.findAll();
        MyUser currentUser = getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("users", users);
        return "allPeople";
    }
    private MyUser getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return myUserImpl.findByLogin(user.getUsername());
    }
}
