package ru.usque.pelican.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.usque.pelican.entities.PelicanBadCategory;
import ru.usque.pelican.repository.PelicanBadCategoryRepository;
import ru.usque.pelican.repository.PelicanUserRepository;
import ru.usque.pelican.services.interfaces.IPelicanBadCategoryService;

import java.util.ArrayList;
import java.util.List;

@Service
public class PelicanBadCategoryService implements IPelicanBadCategoryService {
    private final PelicanBadCategoryRepository repository;
    private final PelicanUserRepository userRepository;

    @Autowired
    public PelicanBadCategoryService(PelicanBadCategoryRepository repository, PelicanUserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public List<PelicanBadCategory> findAll() {
        List<PelicanBadCategory> categories = new ArrayList<>();
        repository.findAll().forEach(categories::add);
        return categories;
    }

    @Override
    public PelicanBadCategory findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<PelicanBadCategory> findByUserId(Integer id) {
        List<PelicanBadCategory> catByUser = repository.findByUserId(id);
        if (catByUser.isEmpty()) {
            PelicanBadCategory cat = new PelicanBadCategory();
            cat.setName("Watch amazing film.");
            cat.setScore(20);
            cat.setUser(userRepository.findById(id));
            addBadCategory(cat);
            catByUser.add(cat);
        }
        return catByUser;
    }

    @Override
    public PelicanBadCategory addBadCategory(PelicanBadCategory category) {
        return repository.save(category);
    }

    @Override
    public PelicanBadCategory updateBadCategory(PelicanBadCategory category) {
        return repository.save(category);
    }

    @Override
    public void deleteBadCategory(Integer id) {
        repository.deleteById(id);
    }

}
