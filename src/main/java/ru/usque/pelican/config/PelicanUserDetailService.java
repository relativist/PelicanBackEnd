package ru.usque.pelican.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.usque.pelican.entities.PelicanUser;
import ru.usque.pelican.repository.PelicanUserRepository;

import java.util.List;

@Service
public class PelicanUserDetailService implements UserDetailsService {

    private final PelicanUserRepository userRepository;

    public PelicanUserDetailService(PelicanUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        List<PelicanUser> users = userRepository.findByLogin(username);
        if (users.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return new PelicanUserPrincipal(users.get(0));
    }
}
