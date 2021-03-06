package ru.usque.pelican.repository;

import org.springframework.data.repository.CrudRepository;
import ru.usque.pelican.entities.PelicanUser;

import java.util.List;

public interface PelicanUserRepository extends CrudRepository<PelicanUser, Integer>  {
    List<PelicanUser> findByEmail(String email);
    PelicanUser findById(Integer id);
    List<PelicanUser> findByLogin(String login);
}
