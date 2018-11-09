package ru.usque.pelican.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.usque.pelican.entities.PelicanCategory;
import ru.usque.pelican.entities.PelicanUser;
import ru.usque.pelican.services.IPelicanUserService;

import java.util.List;

@Controller
@RequestMapping("pelican/users")
public class PelicanUserController {
    private final IPelicanUserService service;

    @Autowired
    public PelicanUserController(IPelicanUserService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<PelicanUser> getUserById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<PelicanUser>> getUsers() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<List<PelicanUser>> createUsers(@RequestBody PelicanUser user,
                                                         UriComponentsBuilder builder) {
        if(service.addUser(user)){
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping()
    public ResponseEntity<PelicanUser> updateArticle(@RequestBody PelicanUser user) {
        service.updateUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Integer id) {
        service.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("dump")
    public ResponseEntity<List<PelicanCategory>> createAllCategories(@RequestBody List<PelicanUser> users) {
        if (users != null && !users.isEmpty()) {
            users.forEach(service::addUser);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
