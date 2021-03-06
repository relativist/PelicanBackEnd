package ru.usque.pelican.services.interfaces;

import ru.usque.pelican.entities.PelicanUser;

import java.util.List;

public interface IPelicanUserService {
  List<PelicanUser> findAll();

  PelicanUser findById(Integer id);

  List<PelicanUser> findByLogin(String login);

  boolean addUser(PelicanUser user);

  PelicanUser updateUser(PelicanUser user);

  void deleteUser(Integer user);

  boolean auth(String login, String password);
}
