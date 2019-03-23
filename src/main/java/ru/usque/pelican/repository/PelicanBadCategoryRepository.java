package ru.usque.pelican.repository;

import org.springframework.data.repository.CrudRepository;
import ru.usque.pelican.entities.PelicanBadCategory;

import java.util.List;

public interface PelicanBadCategoryRepository extends CrudRepository<PelicanBadCategory, Integer>  {
    PelicanBadCategory findById(Integer id);
    List<PelicanBadCategory> findByUserId(Integer id);
    void deleteById(Integer id);
}
