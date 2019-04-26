package ru.usque.pelican.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.usque.pelican.controller.util.ControllerUtils;
import ru.usque.pelican.entities.PelicanCategory;
import ru.usque.pelican.entities.PelicanUser;
import ru.usque.pelican.services.interfaces.IPelicanUserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
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
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.FORBIDDEN);
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
    @PostMapping("create")
    public ResponseEntity<PelicanUser> createUsers(@RequestBody PelicanUser user) {
        log.info("users -> post / user {} ", user);
        if (user.getId() != null || !service.findByLogin(user.getLogin()).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        em.persist(user);

        log.info("{}",user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<PelicanUser> updateUser(@RequestBody PelicanUser user, HttpServletRequest request) {
        log.info("users -> put / user {} ", user);
        return ControllerUtils.callResponse(request, user.getId(), () -> service.updateUser(user));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id, HttpServletRequest request) {
        log.info("users -> delete / id {} ", id);
        return ControllerUtils.callResponse(request, id, () -> {
            service.deleteUser(id);
            return null;
        });
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
