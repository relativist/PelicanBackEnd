package ru.usque.pelican.services;

import ru.usque.pelican.entities.PelicanUser;

import java.util.List;

public interface IPelicanUserService {
    List<PelicanUser> findAll();
    PelicanUser findById(Integer id);
    boolean addUser(PelicanUser user);
    void updateUser(PelicanUser user);
    void deleteUser(Integer user);
}
