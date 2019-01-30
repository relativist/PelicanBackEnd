package ru.usque.pelican.repository;

import org.springframework.data.repository.CrudRepository;
import ru.usque.pelican.entities.PelicanBadEvent;

import java.util.List;

public interface PelicanBadEventRepository extends CrudRepository<PelicanBadEvent, Integer>  {
    PelicanBadEvent findById(Integer id);
    List<PelicanBadEvent> findByUserId(Integer id);
}
