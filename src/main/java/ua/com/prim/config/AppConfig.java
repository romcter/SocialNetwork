package ua.com.prim.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.com.prim.entity.MyUser;
import ua.com.prim.repository.MyUserRepository;

import java.util.Date;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppConfig extends GlobalMethodSecurityConfiguration {

    private static final String ADMIN = "admin";
    private static final String USER = "user";


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner demo(final MyUserRepository myUserRepository,
                                  final PasswordEncoder encoder){
        MyUser admin = new MyUser.Builder()
                .name(ADMIN)
                .password(encoder.encode("1"))
                .role(MyUser.Role.ADMIN)
                .state(MyUser.State.ACTIVE)
                .registrationDate(new Date())
                .email("prijmuk1997@gm.com")
                .mainPhoto("/img/avatarka-pustaya-vk_0.jpg")
                .build();
        MyUser user = new MyUser.Builder()
                .name(USER)
                .password(encoder.encode("1"))
                .role(MyUser.Role.USER)
                .state(MyUser.State.ACTIVE)
                .registrationDate(new Date())
                .email("1@1.1")
                .mainPhoto("/img/avatarka-pustaya-vk_0.jpg")
                .build();
        return string -> {
            myUserRepository.save(user);
            myUserRepository.save(admin);
        };
    }

}
