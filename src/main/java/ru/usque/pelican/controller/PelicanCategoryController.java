package ru.usque.pelican.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.usque.pelican.entities.PelicanCategory;
import ru.usque.pelican.services.IPelicanCategoryService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.QueryParam;
import java.util.List;

@Controller
@RequestMapping("pelican/categories")
public class PelicanCategoryController {
    private final IPelicanCategoryService service;
    @PersistenceContext
    private EntityManager em;

    @Autowired
    public PelicanCategoryController(IPelicanCategoryService service) {
        this.service = service;
    }

    @GetMapping("{id}")
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
