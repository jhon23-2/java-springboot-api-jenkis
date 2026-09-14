package com.example.demo.rest;

import com.example.demo.dto.AnimalDTO;
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
    public ResponseEntity<AnimalDTO> save(@RequestBody AnimalDTO animal) {
        return ResponseEntity.ofNullable(this.animalService.save(animal));
    }

    @GetMapping
    public ResponseEntity<List<AnimalDTO>> findAll(){
        return ResponseEntity.ok(animalService.findAll());
    }
}
