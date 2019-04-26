package ru.usque.pelican.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.usque.pelican.entities.PelicanUser;
import ru.usque.pelican.repository.PelicanUserRepository;
import ru.usque.pelican.services.interfaces.IPelicanUserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class PelicanUserService implements IPelicanUserService {
    private final PelicanUserRepository repository;

    @Autowired
    public PelicanUserService(PelicanUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PelicanUser> findAll() {
        List<PelicanUser> users = new ArrayList<>();
        repository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public PelicanUser findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<PelicanUser> findByLogin(String login) {
        return repository.findByLogin(login);
    }

    @Override
    public synchronized boolean addUser(PelicanUser user) {
        if(repository.findByEmail(user.getEmail()).isEmpty()){
            repository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public PelicanUser updateUser(PelicanUser user) {
        return repository.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
        repository.delete(id);
    }

    @Override
    public boolean auth(String login, String password) {
        return repository.findByLogin(login).stream().anyMatch(e-> e.getPassword().equals(password));
    }
}
