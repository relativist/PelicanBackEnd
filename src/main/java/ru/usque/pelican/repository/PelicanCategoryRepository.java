package ru.usque.pelican.repository;

import org.springframework.data.repository.CrudRepository;
import ru.usque.pelican.entities.PelicanCategory;

import java.util.List;

public interface PelicanCategoryRepository extends CrudRepository<PelicanCategory, Long>  {
    PelicanCategory findById(Integer id);
    List<PelicanCategory> findByUserId(Integer id);
}
