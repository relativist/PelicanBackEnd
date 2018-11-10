package ru.usque.pelican.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.usque.pelican.entities.PelicanEvent;
import ru.usque.pelican.repository.PelicanEventRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PelicanEventService implements IPelicanEventService {
    private final PelicanEventRepository repository;

    @Autowired
    public PelicanEventService(PelicanEventRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PelicanEvent> findAll() {
        List<PelicanEvent> events = new ArrayList<>();
        repository.findAll().forEach(events::add);
        return events;
    }

    @Override
    public List<PelicanEvent> findByUserId(Integer id) {
        return repository.findByUserId(id);
    }

    @Override
    public PelicanEvent findById(Integer id) {
        return repository.findById((long)id);
    }

    @Override
    public boolean addEvent(PelicanEvent event) {
        repository.save(event);
        return true;
    }

    @Override
    public void updateEvent(PelicanEvent event) {
        repository.save(event);
    }

    @Override
    public void deleteEvent(Integer id) {
        repository.delete((long) id);
    }


}
