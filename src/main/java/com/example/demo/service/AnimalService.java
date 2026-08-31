package com.example.demo.service;

import com.example.demo.entity.Animal;
import com.example.demo.service.impl.AnimalInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnimalService {

    private final AnimalInterface animalInterface;

    public Animal save(Animal animal) {
        Animal animalSaved = animalInterface.save(animal);
        log.info("Animal with id: {} saved", animalSaved.getId());
        return animalSaved;
    }
    public List<Animal> findAll(){return animalInterface.findAll();}
}
