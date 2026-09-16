package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/workintech/kangaroos")
public class KangarooController {

    private Map<Integer, Kangaroo> kangaroos;

    @PostConstruct
    public void init() {
        kangaroos = new HashMap<>();
    }

    @GetMapping
    public List<Kangaroo> findAll() {
        return new ArrayList<>(kangaroos.values());
    }

    @GetMapping("/{id}")
    public Kangaroo findById(@PathVariable int id) {
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
        return kangaroos.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Kangaroo save(@RequestBody Kangaroo kangaroo) {
        if (kangaroo.getId() == null || kangaroos.containsKey(kangaroo.getId())) {
            throw new ZooException("Invalid id or already exists", HttpStatus.BAD_REQUEST);
        }
        kangaroos.put(kangaroo.getId(), kangaroo);
        return kangaroo;
    }

    @PutMapping("/{id}")
    public Kangaroo update(@PathVariable int id, @RequestBody Kangaroo kangaroo) {
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
        kangaroo.setId(id);
        kangaroos.put(id, kangaroo);
        return kangaroo;
    }

    @DeleteMapping({"/{id}", "/developers/{id}"})
    public Kangaroo delete(@PathVariable int id) {
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
        return kangaroos.remove(id);
    }
}