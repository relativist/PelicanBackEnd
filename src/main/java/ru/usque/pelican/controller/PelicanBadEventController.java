package ru.usque.pelican.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.usque.pelican.entities.PelicanBadEvent;
import ru.usque.pelican.services.interfaces.IPelicanBadEventService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("bad-events")
public class PelicanBadEventController {
    private final IPelicanBadEventService service;
    @PersistenceContext
    private EntityManager em;

    @Autowired
    public PelicanBadEventController(IPelicanBadEventService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<PelicanBadEvent> getBadEventById(@PathVariable("id") Integer id) {
        log.info("BadEvent -> get / id {} ", id);
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<PelicanBadEvent>> getBadEvents(
            @QueryParam("userId") Integer userId,
            @QueryParam("badCategoryId") Integer categoryId
    ) {
        log.info("BadEvent -> get getEvents / userId {} / categoryId {} ", userId,categoryId);
        List<PelicanBadEvent> all = service.findAll().stream()
                .filter(e-> userId == null || e.getUser().getId().equals(userId))
                .filter(e-> categoryId == null || e.getCategory().getId().equals(categoryId))
                .collect(Collectors.toList());
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<PelicanBadEvent> createBadEvents(@RequestBody PelicanBadEvent event) {
        log.info("BadEvent -> post / event {} ", event);
        event = service.addBadEvent(event);
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<PelicanBadEvent> update(@RequestBody PelicanBadEvent event) {
        log.info("BadEvent -> put / event {} ", event);
        event = service.updateBadEvent(event);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        log.info("BadEvent -> get / id {} ", id);
        service.deleteBadEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(
            value = "/**",
            method = RequestMethod.OPTIONS
    )
    public ResponseEntity handle() {
        return new ResponseEntity(HttpStatus.OK);
    }

}
