package ru.usque.pelican.repository;

import org.springframework.data.repository.CrudRepository;
import ru.usque.pelican.entities.PelicanScore;

import java.util.List;

public interface PelicanScoreRepository extends CrudRepository<PelicanScore, Integer>  {
    PelicanScore findById(Integer id);
    List<PelicanScore> findByUserId(Integer userId);
}
