package ua.com.prim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.prim.entity.MyUser;
import ua.com.prim.servise.MyUserImpl;

@Controller
public class SettingsUserController {

    @Autowired
    private MyUserImpl myUserImpl;

    @GetMapping("/settingUser")
    public String settingsPageReturn(Model model){
        model.addAttribute("currentUser", getCurrentUser());
        return "settingUser";
    }
    @PostMapping("/settingUser")
    public String changePassOrEmail(@RequestParam(name = "changeEmail", required = false)String newEmail,
                                    @RequestParam(name = "changePass", required = false)String newPass,
                                    Model model){
        if(!newEmail.contains("@") && !newEmail.contains(".")){
            model.addAttribute("validEmail", true);
            return "settingUser";
        }
        myUserImpl.changeEmailOrPassword(newEmail, newPass);
        model.addAttribute("currentUser", getCurrentUser());
        model.addAttribute("successChange", true);
        return "settingUser";
    }
    @GetMapping("/deleteAccount/{userId}")
    public String deleteUserAccount(@PathVariable Long userId){
        myUserImpl.deleteAccount(userId);
        return "redirect:/login";
    }
    @GetMapping("/accountRecovery")
    public String returnAccountRecoveryPage(){
        return "accountRecovery";
    }
    @PostMapping("/activateAccount")
    public String activateAccountByEmail(@RequestParam(name = "emailForAccountRecovery")String emailForAccountRecovery,
                                         Model model){
        if(myUserImpl.accountRecovery(emailForAccountRecovery)){
            model.addAttribute("successActivate", true);
            return "loginAndRegistration";
        }model.addAttribute("failActivate", true);
        return "accountRecovery";
    }

    private MyUser getCurrentUser(){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        return myUserImpl.findByLogin(username);
    }
}
