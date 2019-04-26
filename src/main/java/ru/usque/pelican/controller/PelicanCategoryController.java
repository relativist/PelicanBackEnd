package ru.usque.pelican.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.usque.pelican.controller.util.ControllerUtils;
import ru.usque.pelican.entities.PelicanCategory;
import ru.usque.pelican.services.interfaces.IPelicanCategoryService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
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
        log.info("category -> get id / {}",id);
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<PelicanCategory>> getCategories(@QueryParam("userId") Integer userId,
                                                               @QueryParam("parentId") Integer parentId) {
        log.info("category -> get getCategories / {} / {}", userId, parentId);
        if (parentId != null) {
            return new ResponseEntity<>(service.findAll().stream()
                    .filter(e-> {
                        if (e.getParent() != null) {
                            return Objects.equals(e.getParent().getId(), parentId);
                        }
                        return false;
                    })
                    .collect(Collectors.toList()), HttpStatus.OK);
        }

        if (userId == null) {
            return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
        }
        return new ResponseEntity<>(service.findByUserId(userId), HttpStatus.OK);
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<PelicanCategory> createCategories(@RequestBody PelicanCategory category, HttpServletRequest request) {
        log.info("category -> post / {} ", category);
        return ControllerUtils.callResponse(request, category.getUser().getId(), () -> {
            PelicanCategory pelicanCategory = category;
            if (pelicanCategory.getId() == null || pelicanCategory.getId() == 0) {
                if (pelicanCategory.getParent() != null && pelicanCategory.getParent().getId() == 0) {
                    pelicanCategory.setParent(null);
                }
                pelicanCategory.setId(null);
                em.persist(pelicanCategory);
            }else {
                pelicanCategory = em.merge(category);
            }
            return pelicanCategory;
        });
    }

    @Transactional
    @PostMapping("dump")
    public ResponseEntity<List<PelicanCategory>> createAllCategories(@RequestBody List<PelicanCategory> categories) {
        //noinspection Duplicates
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

    @Transactional
    @PutMapping()
    public ResponseEntity<PelicanCategory> updateArticle(@RequestBody PelicanCategory category, HttpServletRequest request) {
        log.info("category -> put / {} ", category);
        return ControllerUtils.callResponse(request, category.getUser().getId(), () -> service.updateCategory(category));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Integer id, HttpServletRequest request) {
        log.info("category -> delete / {} ", id);
        PelicanCategory category = service.findById(id);
        return ControllerUtils.callResponse(request, category.getUser().getId(), () -> {
            service.deleteCategory(id);
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
