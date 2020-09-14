package ua.com.prim.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.com.prim.entity.MyUser;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    @Autowired
    private MyUser user;

    public UserDetailsImpl(MyUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String userRole = user.getRole().name();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole);
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.getState().equals(MyUser.State.BANNED) && !user.getState().equals(MyUser.State.DELETE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getState().equals(MyUser.State.ACTIVE);
    }
    public MyUser getUser() {
        return user;
    }
}

