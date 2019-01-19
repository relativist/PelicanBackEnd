package ru.usque.pelican.repository;

import org.springframework.data.repository.CrudRepository;
import ru.usque.pelican.entities.PelicanEvent;

import java.util.List;

public interface PelicanEventRepository extends CrudRepository<PelicanEvent, Long>  {
    PelicanEvent findById(Integer id);
    List<PelicanEvent> findByUserId(Integer id);
}
