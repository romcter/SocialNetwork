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
public class WelcomeController {

    @Autowired
    private MyUserServiceImpl myUserServiceImpl;

    @GetMapping("/")
    public String mainPage(Model model) {
        MyUser currentUser = getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        return "mainPage";
    }
    @GetMapping("/music")
    public String returnPageMusic(Model model){
        MyUser currentUser = getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        return "music";
    }
    @GetMapping("/music/{whatToDo}/{musicName}/{pageThatNeedToReturn}")
    public String addAndDeleteMusic(
            @PathVariable(value = "whatToDo", required = false, name = "whatToDo")String actionThatNeedToDo,
            @PathVariable(value = "musicName", required = false, name = "musicName") String musicNameForWork,
            @PathVariable(value = "pageThatNeedToReturn", required = false, name = "pageThatNeedToReturn") String pageThatNeedToReturn,
            Model model) {
        myUserServiceImpl.addOrDeleteMusic(actionThatNeedToDo, musicNameForWork);
        MyUser currentUser = getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        return pageThatNeedToReturn;
    }
    @GetMapping("/image")
    public String allImages(Model model) {
        MyUser currentUser = getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        return "image";
    }
    @GetMapping("/image/{whatToDo}/{imageName}/{pageThatNeedToReturn}")
    public String addAndDeleteImage(
            @PathVariable(value = "whatToDo", required = false, name = "whatToDo")String actionThatNeedToDo,
            @PathVariable(value = "imageName", required = false, name = "imageName") String imageNameForWork,
            @PathVariable(value = "pageThatNeedToReturn", required = false, name = "pageThatNeedToReturn") String pageThatNeedToReturn,
            Model model) {
        myUserServiceImpl.addOrDeleteImage(actionThatNeedToDo, imageNameForWork);
        MyUser currentUser = getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        return pageThatNeedToReturn;
    }
    @GetMapping("/video")
    public String allVideo(Model model) {
        MyUser currentUser = getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        return "video";
    }
    @GetMapping("/video/{whatToDo}/{videoName}/{pageThatNeedToReturn}")
    public String addAndDeleteVideo(
            @PathVariable(value = "whatToDo", required = false, name = "whatToDo")String actionThatNeedToDo,
            @PathVariable(value = "videoName", required = false, name = "videoName") String videoNameForWork,
            @PathVariable(value = "pageThatNeedToReturn", required = false, name = "pageThatNeedToReturn") String pageThatNeedToReturn,
            Model model) {
        myUserServiceImpl.addOrDeleteVideo(actionThatNeedToDo, videoNameForWork);
        MyUser currentUser = getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        return pageThatNeedToReturn;
    }
    @GetMapping("/unauthorized")
    public String unauthorized(Model model){
        model.addAttribute("currentUser", getCurrentUser());
        return "unauthorized";
    }


    private MyUser getCurrentUser(){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        return myUserServiceImpl.findByLogin(loggedInUser.getName());
    }
}
