package ru.usque.pelican.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.usque.pelican.entities.PelicanCategory;
import ru.usque.pelican.entities.PelicanEvent;
import ru.usque.pelican.services.IPelicanEventService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("events")
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
        log.info("events -> get / id {} ", id);
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<PelicanEvent>> getEvents(
            @QueryParam("userId") Integer userId,
            @QueryParam("categoryId") Integer categoryId
    ) {
        log.info("events -> get getEvents / userId {} / categoryId {} ", userId,categoryId);
        List<PelicanEvent> all = service.findAll().stream()
                .filter(e-> userId == null || e.getUser().getId() == userId)
                .filter(e-> categoryId == null || e.getCategory().getId() == categoryId)
                .collect(Collectors.toList());
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<PelicanEvent> createEvents(@RequestBody PelicanEvent event) {
        log.info("events -> post / event {} ", event);
        if (event.getId() == null || event.getId() == 0) {
            em.persist(event);
        }else {
            event = em.merge(event);
        }
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<PelicanEvent> updateArticle(@RequestBody PelicanEvent event) {
        log.info("events -> put / event {} ", event);
        service.updateEvent(event);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Integer id) {
        log.info("events -> get / id {} ", id);
        service.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("dump")
    public ResponseEntity<List<PelicanCategory>> createAllCategories(@RequestBody List<PelicanEvent> events) {
        //noinspection Duplicates
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

    @RequestMapping(
            value = "/**",
            method = RequestMethod.OPTIONS
    )
    public ResponseEntity handle() {
        return new ResponseEntity(HttpStatus.OK);
    }

}
