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
import ua.com.prim.servise.MyUserImpl;

@Controller
public class FriendController {

    @Autowired
    private MyUserImpl myUserImpl;

    @GetMapping("/friends")
    public String myFriends(Model model){
        MyUser currentUser = getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        return "friend";
    }
    @GetMapping("/friends/{whatNeedToDo}/{userId}/{page}")
    public String addAndDeleteFriends(
            @PathVariable(value = "whatNeedToDo", required = false, name = "whatNeedToDo")String whatNeedToDo,
            @PathVariable(value = "userId", required = false, name = "userId")Long userIdForWork,
            @PathVariable(value = "page", required = false, name = "page")String pageThatNeedToReturn,
            Model model){
        myUserImpl.addAndDeleteFriends(whatNeedToDo, userIdForWork);
        Iterable<MyUser> users = myUserImpl.findAll();
        MyUser currentUser = getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("users", users);
        return pageThatNeedToReturn;
    }
    @GetMapping("/friendPage/{userId}")
    public String returnFriendPage(@PathVariable Long userId, Model model){
        model.addAttribute("userWhomVisit", myUserImpl.findById(userId).get());
        model.addAttribute("currentUser", getCurrentUser());
        return "userWhomVisitPage";
    }
    @GetMapping("/ad/{whatNeedToDo}/{naming}/{userIdFromWhomWeTake}")
    public String addOrDeleteMusicOrImageOrVideo(@PathVariable String whatNeedToDo,
                                                 @PathVariable String naming,
                                                 @PathVariable Long userIdFromWhomWeTake,
                                                 Model model){
        myUserImpl.addOrDeleteImageOrMusicOrVideo(whatNeedToDo, naming);
        model.addAttribute("userWhomVisit", myUserImpl.findById(userIdFromWhomWeTake).get());
        model.addAttribute("currentUser", getCurrentUser());
        return "userWhomVisitPage";
    }
    @GetMapping("/findByName")
    public String findByName(@RequestParam(name = "nameThatNeedFind") String name,
                             Model model){
        model.addAttribute("usersThatFindByName", myUserImpl.findUsersByName(name));
        model.addAttribute("currentUser", getCurrentUser());
        return "findByName";
    }
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
