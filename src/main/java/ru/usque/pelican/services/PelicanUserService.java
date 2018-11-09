package ru.usque.pelican.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.usque.pelican.entities.PelicanUser;
import ru.usque.pelican.repository.PelicanUserRepository;

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
        return repository.findById((long)id);
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
    public void updateUser(PelicanUser user) {
        repository.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
        repository.delete((long) id);
    }
}