package ua.com.prim.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ua.com.prim.entity.MyUser;
import ua.com.prim.repository.MyUserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Component
public class AuthHandler implements AuthenticationSuccessHandler {

    @Autowired
    private MyUserRepository myUserRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            Authentication authentication) throws IOException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken)authentication;
        OAuth2User user = token.getPrincipal();

        Map<String, Object> attributes = user.getAttributes();

        MyUser myUser = new MyUser.Builder()
                .email((String) attributes.get("email"))
                .name((String) attributes.get("name"))
                .mainPhoto((String) attributes.get("picture"))
                .registrationDate(new Date())
                .role(MyUser.Role.USER)
                .state(MyUser.State.ACTIVE)
                .mainPhoto("/img/avatarka-pustaya-vk_0.jpg")
                .build();
        myUserRepository.save(myUser);
        httpServletResponse.sendRedirect("/googleAuth/"+myUser.getEmail());
    }
}
