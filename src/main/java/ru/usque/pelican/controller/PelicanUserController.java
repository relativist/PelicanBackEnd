package ru.usque.pelican.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.usque.pelican.entities.PelicanCategory;
import ru.usque.pelican.entities.PelicanUser;
import ru.usque.pelican.services.IPelicanUserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.QueryParam;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("users")
public class PelicanUserController {
    private final IPelicanUserService service;
    @PersistenceContext
    private EntityManager em;

    @Autowired
    public PelicanUserController(IPelicanUserService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<PelicanUser> getUserById(@PathVariable("id") Integer id) {
        log.info("users -> get / id {} ", id);
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping("auth")
    public ResponseEntity<PelicanUser> auth(@QueryParam("login") String login, @QueryParam("password") String password) {
        log.info("users -> auth / login {} / password {} ", login, password);
        if (service.auth(login, password)) {
            return new ResponseEntity<>(service.findByLogin(login).get(0), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping()
    public ResponseEntity<List<PelicanUser>> getUsers(@QueryParam("login") String login) {
        log.info("users -> get / login {} ", login);
        if (login == null) {
            return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
        }
        return new ResponseEntity<>(service.findByLogin(login), HttpStatus.OK);
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<PelicanUser> createUsers(@RequestBody PelicanUser user) {
        log.info("users -> post / user {} ", user);
        if (user.getId() <= 0) {
            em.persist(user);
        }else {
            user = em.merge(user);
        }

        log.info("{}",user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<PelicanUser> updateArticle(@RequestBody PelicanUser user) {
        log.info("users -> put / user {} ", user);
        service.updateUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Integer id) {
        log.info("users -> delete / id {} ", id);
        service.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    @PostMapping("dump")
    public ResponseEntity<List<PelicanCategory>> createAllCategories(@RequestBody List<PelicanUser> users) {
        if (users != null && !users.isEmpty()) {
            users.forEach(e-> {
                if (e.getId() <= 0) {
                    em.persist(e);
                }else {
                    em.merge(e);
                }

            });
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/**",
            method = RequestMethod.OPTIONS
    )
    public ResponseEntity handle() {
        return new ResponseEntity(HttpStatus.OK);
    }
}
