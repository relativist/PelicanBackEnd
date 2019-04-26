package ru.usque.pelican.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.usque.pelican.controller.util.ControllerUtils;
import ru.usque.pelican.entities.PelicanEvent;
import ru.usque.pelican.services.interfaces.IPelicanEventService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
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
                .filter(e-> userId == null || e.getUser().getId().equals(userId))
                .filter(e-> categoryId == null || e.getCategory().getId().equals(categoryId))
                .collect(Collectors.toList());
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<PelicanEvent> createEvents(@RequestBody PelicanEvent event, HttpServletRequest request) {
        log.info("events -> post / event {} ", event);
        return ControllerUtils.callResponse(request, event.getUser().getId(), () -> {
            PelicanEvent ev = event;
            if (ev.getId() == null || event.getId() == 0) {
                em.persist(event);
            }else {
                ev = em.merge(event);
            }
            return ev;
        });
    }

    @PutMapping()
    public ResponseEntity<PelicanEvent> updateArticle(@RequestBody PelicanEvent event, HttpServletRequest request) {
        log.info("events -> put / event {} ", event);
        return ControllerUtils.callResponse(request, event.getUser().getId(), () -> service.updateEvent(event));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Integer id, HttpServletRequest request) {
        log.info("events -> get / id {} ", id);
        PelicanEvent event = service.findById(id);
        return ControllerUtils.callResponse(request, event.getUser().getId(), () -> {
            service.deleteEvent(id);
            return null;
        });
    }


    @RequestMapping(
            value = "/**",
            method = RequestMethod.OPTIONS
    )
    public ResponseEntity handle() {
        return new ResponseEntity(HttpStatus.OK);
    }

}
