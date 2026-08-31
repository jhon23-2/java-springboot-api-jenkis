package com.example.demo.service;

import com.example.demo.entity.Animal;
import com.example.demo.service.impl.AnimalInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private AnimalInterface animalInterface;

    public Animal save(Animal animal) {return null;}
    public List<Animal> findAll(){return null;}
}
