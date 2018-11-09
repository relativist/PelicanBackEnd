package ru.usque.pelican.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.usque.pelican.entities.PelicanCategory;
import ru.usque.pelican.repository.PelicanCategoryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PelicanCategoryService implements IPelicanCategoryService {
    private final PelicanCategoryRepository repository;

    @Autowired
    public PelicanCategoryService(PelicanCategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PelicanCategory> findAll() {
        List<PelicanCategory> categories = new ArrayList<>();
        repository.findAll().forEach(categories::add);
        return categories;
    }

    @Override
    public PelicanCategory findById(Integer id) {
        return repository.findById((long)id);
    }

    @Override
    public boolean addCategory(PelicanCategory category) {
        repository.save(category);
        return true;
    }

    @Override
    public void updateCategory(PelicanCategory category) {
        repository.save(category);
    }

    @Override
    public void deleteCategory(Integer id) {
        repository.delete((long)id);
    }

}
