package ru.usque.pelican.repository;

import org.springframework.data.repository.CrudRepository;
import ru.usque.pelican.entities.PelicanUser;

import java.util.List;

public interface PelicanUserRepository extends CrudRepository<PelicanUser, Long>  {
    List<PelicanUser> findByEmail(String email);
    PelicanUser findById(Long id);
}
