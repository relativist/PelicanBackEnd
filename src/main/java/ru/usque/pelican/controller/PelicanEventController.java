package ru.usque.pelican.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.usque.pelican.entities.PelicanCategory;
import ru.usque.pelican.entities.PelicanEvent;
import ru.usque.pelican.entities.PelicanUser;
import ru.usque.pelican.services.IPelicanEventService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.QueryParam;
import java.util.List;

@Controller
@RequestMapping("pelican/events")
public class PelicanEventController {
    private final IPelicanEventService service;
    @PersistenceContext
    private EntityManager em;

    @Autowired
    public PelicanEventController(IPelicanEventService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<PelicanEvent> getEventById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<PelicanEvent>> getEvents(@QueryParam("userId") Integer userId) {
        if (userId == null) {
            return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
        }
        return new ResponseEntity<>(service.findByUserId(userId), HttpStatus.OK);
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<PelicanEvent> createEvents(@RequestBody PelicanEvent event) {
        if (event.getId() == 0) {
            em.persist(event);
        }else {
            event = em.merge(event);
        }
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<PelicanEvent> updateArticle(@RequestBody PelicanEvent event) {
        service.updateEvent(event);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Integer id) {
        service.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("dump")
    public ResponseEntity<List<PelicanCategory>> createAllCategories(@RequestBody List<PelicanEvent> events) {
        if (events != null && !events.isEmpty()) {
            events.forEach(e-> {
                if (e.getId() == 0) {
                    em.persist(e);
                }else {
                    em.merge(e);
                }
            });
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
