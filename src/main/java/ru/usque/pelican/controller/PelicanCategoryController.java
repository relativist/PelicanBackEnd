package ru.usque.pelican.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.usque.pelican.entities.PelicanCategory;
import ru.usque.pelican.services.IPelicanCategoryService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.QueryParam;
import java.util.List;

@RestController
@RequestMapping("categories")
@Api(description = "saying hello", tags = "hello")
public class PelicanCategoryController {
    private final IPelicanCategoryService service;
    @PersistenceContext
    private EntityManager em;

    @Autowired
    public PelicanCategoryController(IPelicanCategoryService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    @ApiOperation(
            nickname = "index", // To avoid indexUsingGET in ApiClient
            value = "Says hello to you",
            notes = "This endpoint just tells you a greeting message.<br/>" +
                    "See also: https://en.wikipedia.org/wiki/%22Hello,_World!%22_program")
    public ResponseEntity<PelicanCategory> getCategoryById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<PelicanCategory>> getCategories(@QueryParam("userId") Integer userId) {
        if (userId == null) {
            return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
        }
        return new ResponseEntity<>(service.findByUserId(userId), HttpStatus.OK);
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<PelicanCategory> createCategories(@RequestBody PelicanCategory category) {
        if (category.getId() == 0) {
            em.persist(category);
        }else {
            category = em.merge(category);
        }
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @Transactional
    @PostMapping("dump")
    public ResponseEntity<List<PelicanCategory>> createAllCategories(@RequestBody List<PelicanCategory> categories) {
        if (categories != null && !categories.isEmpty()) {
            categories.forEach(e-> {
                if (e.getId() == 0) {
                    em.persist(e);
                }else {
                    em.merge(e);
                }
            });
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<PelicanCategory> updateArticle(@RequestBody PelicanCategory category) {
        service.updateCategory(category);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Integer id) {
        service.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
