package ru.usque.pelican.repository;

import org.springframework.data.repository.CrudRepository;
import ru.usque.pelican.entities.PelicanPlan;

import java.util.List;

public interface PelicanPlansRepository extends CrudRepository<PelicanPlan, Integer> {
  PelicanPlan findById(Integer id);
  List<PelicanPlan> findByUserId(Integer userId);
}
