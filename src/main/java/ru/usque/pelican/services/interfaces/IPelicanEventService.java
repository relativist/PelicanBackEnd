package ru.usque.pelican.services.interfaces;

import ru.usque.pelican.entities.PelicanEvent;

import java.util.List;

public interface IPelicanEventService {
    List<PelicanEvent> findAll();
    List<PelicanEvent> findByUserId(Integer id);
    PelicanEvent findById(Integer id);
    boolean addEvent(PelicanEvent event);
    PelicanEvent updateEvent(PelicanEvent event);
    void deleteEvent(Integer event);
}
