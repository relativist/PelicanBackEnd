package ru.usque.pelican.services.interfaces;

import ru.usque.pelican.entities.PelicanBadCategory;

import java.util.List;

public interface IPelicanBadCategoryService {
    List<PelicanBadCategory> findAll();
    PelicanBadCategory findById(Integer id);
    List<PelicanBadCategory> findByUserId(Integer id);
    PelicanBadCategory addBadCategory(PelicanBadCategory category);
    PelicanBadCategory updateBadCategory(PelicanBadCategory user);
    void deleteBadCategory(Integer id);
}
