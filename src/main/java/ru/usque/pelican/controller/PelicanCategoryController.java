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

import java.util.List;

@Controller
@RequestMapping("pelican/categories")
public class PelicanCategoryController {
    private final IPelicanCategoryService service;

    @Autowired
    public PelicanCategoryController(IPelicanCategoryService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<PelicanCategory> getCategoryById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<PelicanCategory>> getCategorys() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<List<PelicanCategory>> createCategories(@RequestBody PelicanCategory category,
                                                         UriComponentsBuilder builder) {
        if(service.addCategory(category)){
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/categories/{id}").buildAndExpand(category.getId()).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
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
