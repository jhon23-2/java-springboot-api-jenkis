package com.example.demo.rest;

import com.example.demo.entity.Animal;
import com.example.demo.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animals")
@RequiredArgsConstructor
public class AnimalRest {
    private final AnimalService animalService;

    @PostMapping
    public ResponseEntity<Animal> save(@RequestBody Animal animal) {
        Animal saved = animalService.save(animal);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<Animal>> findAll(){
        return ResponseEntity.ok(animalService.findAll());
    }
}
