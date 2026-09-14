package com.example.demo.service;

import com.example.demo.dto.AnimalDTO;
import com.example.demo.entity.Animal;
import com.example.demo.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnimalService {

    private final AnimalRepository animalRepository;

    public AnimalDTO save(AnimalDTO animalDto) {
        Animal animal = Animal.builder()
                .name(animalDto.getName())
                .category(animalDto.getCategory())
                .age(animalDto.getAge())
                .build();

        Animal animalSaved = this.animalRepository.save(animal);
        log.info("Animal with id: {} saved", animalSaved.getId());

        return AnimalDTO.builder()
                .id(animalSaved.getId())
                .name(animalSaved.getName())
                .age(animalSaved.getAge())
                .category(animalDto.getCategory())
                .build();
    }
    public List<AnimalDTO> findAll(){
        return this.animalRepository.findAll()
                .stream().map(this::animalEntityToAnimalDTO)
                .toList();
    }

    /**
     * Converts Animal DTO to Animal Entity class
     * */
    private Animal animalDtoToAnimalEntity(AnimalDTO animalDto) {
        return Animal.builder()
                .name(animalDto.getName())
                .age(animalDto.getAge())
                .category(animalDto.getCategory())
                .build();
    }

    /**
     * Converts Animal Entity to AnimalDTO class
     * */
    private AnimalDTO animalEntityToAnimalDTO(Animal animal) {
        return AnimalDTO.builder()
                .id(animal.getId())
                .name(animal.getName())
                .age(animal.getAge())
                .category(animal.getCategory())
                .build();
    }
}
