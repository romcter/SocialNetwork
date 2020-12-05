package ua.com.prim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.prim.entity.MyUser;
import ua.com.prim.service.MyUserServiceImpl;

@Controller
public class FriendController {

    @Autowired
    private MyUserServiceImpl myUserServiceImpl;


    @GetMapping("/friends")
    public String returnFriendPage (Model model){
        MyUser currentUser = getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        return "friend";
    }

    @GetMapping("/friends/{addOrDeleteFriend}/{userIdForWork}/{pageThatNeedToReturn}")
    public String addOrDeleteFriends(
            @PathVariable String addOrDeleteFriend,
            @PathVariable Long userIdForWork,
            @PathVariable String pageThatNeedToReturn,
            Model model) {
        myUserServiceImpl.addOrDeleteFriends(addOrDeleteFriend, userIdForWork);
        Iterable<MyUser> allUsers = myUserServiceImpl.findAll();
        MyUser currentUser = getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("users", allUsers);
        return pageThatNeedToReturn;
    }

    @GetMapping("/friendPage/{userId}")
    public String returnFriendPage(
            @PathVariable Long userId, Model model){
        model.addAttribute("userWhomVisit", myUserServiceImpl.findById(userId).get());
        model.addAttribute("currentUser", getCurrentUser());
        return "userWhomVisitPage";
    }

    @GetMapping("/ad/{whatNeedToDo}/{naming}/{userIdFromWhomWeTake}")
    public String addOrDeleteMusicOrImageOrVideo(
            @PathVariable String whatNeedToDo,
            @PathVariable String naming,
            @PathVariable Long userIdFromWhomWeTake,
            Model model){
        myUserServiceImpl.addOrDeleteImageOrMusicOrVideo(whatNeedToDo, naming);
        model.addAttribute("userWhomVisit", myUserServiceImpl.findById(userIdFromWhomWeTake).get());
        model.addAttribute("currentUser", getCurrentUser());
        return "userWhomVisitPage";
    }

    @GetMapping("/findByName")
    public String findByName(
            @RequestParam(name = "nameThatNeedFind") String name,
            Model model){
        model.addAttribute("usersThatFindByName", myUserServiceImpl.findUsersByName(name));
        model.addAttribute("currentUser", getCurrentUser());
        return "findByName";
    }

    @GetMapping("/people")
    public String searchPeople(Model model){
        Iterable<MyUser> allUsers = myUserServiceImpl.findAll();
        MyUser currentUser = getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("users", allUsers);
        return "allPeople";
    }


    private MyUser getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return myUserServiceImpl.findByLogin(user.getUsername());
    }
}
