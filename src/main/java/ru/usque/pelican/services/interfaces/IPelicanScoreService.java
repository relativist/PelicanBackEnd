package ru.usque.pelican.services.interfaces;

import ru.usque.pelican.entities.PelicanScore;

import java.util.List;

public interface IPelicanScoreService {
  List<PelicanScore> findAll();

  PelicanScore findByUserId(Integer userId);

  PelicanScore findById(Integer id);

  PelicanScore operateScore(Integer value, Integer userId);
}
