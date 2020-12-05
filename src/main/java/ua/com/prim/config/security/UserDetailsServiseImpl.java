package ua.com.prim.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.com.prim.entity.MyUser;
import ua.com.prim.repository.MyUserRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class UserDetailsServiseImpl implements UserDetailsService {

    @Autowired
    private MyUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        MyUser user = userRepository.findByEmail(login);
        if(user == null){
            throw new UsernameNotFoundException(login + " not found");
        }
        List<GrantedAuthority> roles = Arrays.asList(new SimpleGrantedAuthority(user.getRole().getAuthority()));
        return new User(user.getEmail(),user.getPassword(),roles);
    }
}

