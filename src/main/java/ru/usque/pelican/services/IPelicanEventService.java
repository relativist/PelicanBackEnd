package ru.usque.pelican.services;

import ru.usque.pelican.entities.PelicanEvent;

import java.util.List;

public interface IPelicanEventService {
    List<PelicanEvent> findAll();
    PelicanEvent findById(Integer id);
    boolean addEvent(PelicanEvent event);
    void updateEvent(PelicanEvent event);
    void deleteEvent(Integer event);
}
