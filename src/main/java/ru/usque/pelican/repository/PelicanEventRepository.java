package ru.usque.pelican.repository;

import org.springframework.data.repository.CrudRepository;
import ru.usque.pelican.entities.PelicanEvent;

public interface PelicanEventRepository extends CrudRepository<PelicanEvent, Long>  {
    PelicanEvent findById(Long id);
}
