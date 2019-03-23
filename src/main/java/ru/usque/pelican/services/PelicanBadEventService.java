package ru.usque.pelican.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.usque.pelican.entities.PelicanBadEvent;
import ru.usque.pelican.repository.PelicanBadEventRepository;
import ru.usque.pelican.services.interfaces.IPelicanBadEventService;

import java.util.ArrayList;
import java.util.List;

@Service
public class PelicanBadEventService implements IPelicanBadEventService {
    private final PelicanBadEventRepository repository;

    @Autowired
    public PelicanBadEventService(PelicanBadEventRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PelicanBadEvent> findAll() {
        List<PelicanBadEvent> events = new ArrayList<>();
        repository.findAll().forEach(events::add);
        return events;
    }

    @Override
    public List<PelicanBadEvent> findByUserId(Integer id) {
        return repository.findByUserId(id);
    }

    @Override
    public PelicanBadEvent findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public PelicanBadEvent addBadEvent(PelicanBadEvent event) {
        return repository.save(event);
    }

    @Override
    public PelicanBadEvent updateBadEvent(PelicanBadEvent event) {
        return repository.save(event);
    }

    @Override
    public void deleteBadEvent(Integer id) {
        repository.delete(id);
    }


}
