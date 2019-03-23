package ru.usque.pelican.services.interfaces;

import ru.usque.pelican.entities.PelicanPlan;

import java.util.List;

public interface IPelicanPlansService {
  List<PelicanPlan> findAll();

  PelicanPlan findById(Integer id);

  List<PelicanPlan> findByUserId(Integer userId, Boolean isGrand);

  PelicanPlan addPlan(PelicanPlan user);

  PelicanPlan updatePlan(PelicanPlan user);

  void deletePlan(Integer user);
}
