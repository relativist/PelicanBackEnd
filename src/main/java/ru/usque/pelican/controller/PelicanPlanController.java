package ru.usque.pelican.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.usque.pelican.controller.util.ControllerUtils;
import ru.usque.pelican.entities.PelicanPlan;
import ru.usque.pelican.services.interfaces.IPelicanPlansService;

import javax.servlet.http.HttpServletRequest;
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
    public ResponseEntity<PelicanPlan> createUsers(@RequestBody PelicanPlan plan, HttpServletRequest request) {
        log.info("users -> post / plan {} ", plan);
        return ControllerUtils.callResponse(request, plan.getUser().getId(), ()-> service.addPlan(plan));
    }

    @PutMapping()
    public ResponseEntity<PelicanPlan> updateArticle(@RequestBody PelicanPlan plan, HttpServletRequest request) {
        log.info("plan -> put / plan {} ", plan);
        return ControllerUtils.callResponse(request, plan.getUser().getId(), () -> service.updatePlan(plan));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Integer id, HttpServletRequest request) {
        log.info("plan -> delete / id {} ", id);
        PelicanPlan plan = service.findById(id);
        return ControllerUtils.callResponse(request, plan.getUser().getId(), () -> {
            service.deletePlan(id);
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
