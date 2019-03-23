package ru.usque.pelican.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.usque.pelican.entities.PelicanPlan;
import ru.usque.pelican.services.interfaces.IPelicanPlansService;

import javax.transaction.Transactional;
import javax.ws.rs.QueryParam;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("plan")
public class PelicanPlanController {
    private final IPelicanPlansService service;

    @Autowired
    public PelicanPlanController(IPelicanPlansService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<PelicanPlan> getUserById(@PathVariable("id") Integer id) {
        log.info("plan -> get / id {} ", id);
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<PelicanPlan>> getPlan(@QueryParam("userId") Integer userId, @QueryParam("isGrand") Boolean isGrand) {
        log.info("plan -> get / userId {} / isGrand {}", userId, isGrand);
        if (userId == null) {
            return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
        }
        return new ResponseEntity<>(service.findByUserId(userId,isGrand), HttpStatus.OK);
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<PelicanPlan> createUsers(@RequestBody PelicanPlan plan) {
        log.info("users -> post / plan {} ", plan);
        plan = service.addPlan(plan);
        return new ResponseEntity<>(plan, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<PelicanPlan> updateArticle(@RequestBody PelicanPlan plan) {
        log.info("plan -> put / plan {} ", plan);
        plan = service.updatePlan(plan);
        return new ResponseEntity<>(plan, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Integer id) {
        log.info("plan -> delete / id {} ", id);
        service.deletePlan(id);
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
