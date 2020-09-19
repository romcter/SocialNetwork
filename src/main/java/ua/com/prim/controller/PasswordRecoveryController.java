package ua.com.prim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.prim.entity.MyUser;
import ua.com.prim.servise.MyUserImpl;

@Controller
public class PasswordRecoveryController {

    @Autowired
    private MyUserImpl myUserImpl;

    @GetMapping("/passwordRecovery")
    public String returnPage(){
        return "passwordRecovery";
    }
    @PostMapping("/passwordRecovery")
    public String passwordRecovery(
            @RequestParam(name = "emailThatNeedToRecovery") String emailThatNeedToRecovery,
            Model model){
        if(myUserImpl.passwordRecovery(emailThatNeedToRecovery)){
            model.addAttribute("successfulRecovery",true);
            return "loginAndRegistration";
        }
        model.addAttribute("failSend",true);
        return "passwordRecovery";
    }
    @GetMapping("/passwordRecovery/{activationCode}")
    public String activationCode(
            @PathVariable String activationCode,
            Model model){
        MyUser currentUser = myUserImpl.findByActivationCode(activationCode);
        if(currentUser.getActivationCode().equals(activationCode)){
            model.addAttribute("currentUser", currentUser);
            return "changePassword";
        }
        model.addAttribute("fail", true);
        return "passwordRecovery";
    }
    @PostMapping("/passwordRecovery/{userIdWhomNeedToChangePassword}")
    public String changePassword(@PathVariable Long userIdWhomNeedToChangePassword,
                                 @RequestParam("newPassword")String newPassword,
                                 Model model){
        if(myUserImpl.changePassword(userIdWhomNeedToChangePassword, newPassword)){
            model.addAttribute("successChangePass", true);
            return "loginAndRegistration";
        }model.addAttribute("failChangePass", true);
        return "changePassword";
    }
}
