package ru.usque.pelican.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.usque.pelican.entities.PelicanUser;

import java.util.Collection;
@Slf4j
@Data
public class PelicanUserPrincipal implements UserDetails {
    private PelicanUser user;

    public PelicanUserPrincipal(PelicanUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("WTF?");
        return null;
    }

    @Override
    public String getPassword() {
        log.info("PASS");
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
