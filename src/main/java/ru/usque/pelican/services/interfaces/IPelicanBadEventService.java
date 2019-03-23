package ru.usque.pelican.services.interfaces;

import ru.usque.pelican.entities.PelicanBadEvent;

import java.util.List;

public interface IPelicanBadEventService {
    List<PelicanBadEvent> findAll();
    List<PelicanBadEvent> findByUserId(Integer id);
    PelicanBadEvent findById(Integer id);
    PelicanBadEvent addBadEvent(PelicanBadEvent event);
    PelicanBadEvent updateBadEvent(PelicanBadEvent event);
    void deleteBadEvent(Integer event);
}
