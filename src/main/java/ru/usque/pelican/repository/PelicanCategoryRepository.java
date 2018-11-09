package ru.usque.pelican.repository;

import org.springframework.data.repository.CrudRepository;
import ru.usque.pelican.entities.PelicanCategory;

public interface PelicanCategoryRepository extends CrudRepository<PelicanCategory, Long>  {
    PelicanCategory findById(Long id);
}
