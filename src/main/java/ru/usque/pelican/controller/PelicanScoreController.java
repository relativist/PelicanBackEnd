package ru.usque.pelican.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.usque.pelican.entities.PelicanScore;
import ru.usque.pelican.services.interfaces.IPelicanScoreService;

import javax.ws.rs.QueryParam;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("score")
public class PelicanScoreController {
    private final IPelicanScoreService service;
//    @PersistenceContext
//    private EntityManager em;

    @Autowired
    public PelicanScoreController(IPelicanScoreService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<PelicanScore> getScoreById(@PathVariable("id") Integer id) {
        log.info("score -> get / id {} ", id);
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping("all")
    public ResponseEntity<List<PelicanScore>> getAllScores() {
        log.info("score -> getAllScores ");
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<PelicanScore> findByUser(@QueryParam("userId") Integer userId) {
        log.info("score -> post / findByUser {} ", userId);

        if (userId == null) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        PelicanScore byUser = service.findByUserId(userId);
        log.info("{}",byUser);
        return new ResponseEntity<>(byUser, HttpStatus.OK);
    }

    @GetMapping("operate")
    public ResponseEntity<PelicanScore> operate(@QueryParam("value") Integer value,@QueryParam("userId") Integer userId) {
        log.info("score -> put / value {} / {}", value, userId);
        PelicanScore score = service.operateScore(value, userId);
        return new ResponseEntity<>(score, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/**",
            method = RequestMethod.OPTIONS
    )
    public ResponseEntity handle() {
        return new ResponseEntity(HttpStatus.OK);
    }
}
