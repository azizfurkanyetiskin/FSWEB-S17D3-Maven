package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/workintech/koalas")
public class KoalaController {

    private Map<Integer, Koala> koalas;

    @PostConstruct
    public void init() {
        koalas = new HashMap<>();
    }

    @GetMapping
    public List<Koala> findAll() {
        return new ArrayList<>(koalas.values());
    }

    @GetMapping("/{id}")
    public Koala findById(@PathVariable int id) {
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
        return koalas.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Koala save(@RequestBody Koala koala) {
        if (koala.getId() == null || koalas.containsKey(koala.getId())) {
            throw new ZooException("Invalid id or already exists", HttpStatus.BAD_REQUEST);
        }
        koalas.put(koala.getId(), koala);
        return koala;
    }

    @PutMapping("/{id}")
    public Koala update(@PathVariable int id, @RequestBody Koala koala) {
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
        koala.setId(id);
        koalas.put(id, koala);
        return koala;
    }

    @DeleteMapping("/{id}")
    public Koala delete(@PathVariable int id) {
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
        return koalas.remove(id);
    }
}