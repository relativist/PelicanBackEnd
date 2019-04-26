package ru.usque.pelican.services.interfaces;

import ru.usque.pelican.entities.PelicanCategory;

import java.util.List;

public interface IPelicanCategoryService {
    List<PelicanCategory> findAll();
    PelicanCategory findById(Integer id);
    List<PelicanCategory> findByUserId(Integer id);
    boolean addCategory(PelicanCategory category);
    PelicanCategory updateCategory(PelicanCategory user);
    void deleteCategory(Integer id);
}
