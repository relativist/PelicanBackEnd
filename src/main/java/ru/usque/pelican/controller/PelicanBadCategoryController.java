package ru.usque.pelican.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.usque.pelican.config.PelicanUserPrincipal;
import ru.usque.pelican.controller.util.ControllerUtils;
import ru.usque.pelican.dto.PelicanError;
import ru.usque.pelican.entities.PelicanBadCategory;
import ru.usque.pelican.services.interfaces.IPelicanBadCategoryService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.QueryParam;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("bad-categories")
@Api(description = "saying hello", tags = "hello")
public class PelicanBadCategoryController {
    private final IPelicanBadCategoryService service;
    @PersistenceContext
    private EntityManager em;

    @Autowired
    public PelicanBadCategoryController(IPelicanBadCategoryService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    @ApiOperation(
            nickname = "index",
            value = "Says hello to you",
            notes = "This endpoint just tells you a greeting message.<br/>" +
                    "See also: https://en.wikipedia.org/wiki/%22Hello,_World!%22_program")
    public ResponseEntity<PelicanBadCategory> getCategoryById(@PathVariable("id") Integer id) {
        log.info("category -> get id / {}",id);
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<PelicanBadCategory>> getBadCategories(@QueryParam("userId") Integer userId) {
        log.info("category -> get getCategories / {} ", userId);
        if (userId == null) {
            return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
        }
        return new ResponseEntity<>(service.findByUserId(userId), HttpStatus.OK);
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<PelicanBadCategory> createCategories(@RequestBody PelicanBadCategory category,
    HttpServletRequest request) {
        log.info("category -> post / {} ", category);
        return ControllerUtils.callResponse(request, category.getUser().getId(), () -> service.addBadCategory(category));
    }

    @Transactional
    @PutMapping()
    public ResponseEntity<PelicanBadCategory> updateArticle(@RequestBody PelicanBadCategory category,HttpServletRequest request) {
        log.info("category -> put / {} ", category);
        return ControllerUtils.callResponse(request, category.getUser().getId(), () -> service.updateBadCategory(category));
    }

    @Transactional
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Integer id,HttpServletRequest request) {
        log.info("category -> delete / {} ", id);
        PelicanBadCategory category = service.findById(id);
        return ControllerUtils.callResponse(request, category.getUser().getId(), () -> {
            em.remove(category);
            service.deleteBadCategory(id);
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
