package com.example.demo.service;

import com.example.demo.entity.Animal;
import com.example.demo.service.impl.AnimalInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnimalServiceTest {

    @Mock
    private AnimalInterface animalRepository;

    @InjectMocks
    private AnimalService animalService;

    @Test
    @DisplayName("Should persist and returning animal")
    void save_shouldPersistAndReturnAnimal() {
        Animal animal = Animal.builder()
                .name("Leo")
                .age("5")
                .category("Lion")
                .build();

        when(animalRepository.save(animal)).thenReturn(animal);

        Animal saved = animalService.save(animal);

        assertThat(saved.getName()).isEqualTo("Leo");
        verify(animalRepository).save(animal);
    }

    @Test
    @DisplayName("Should find all and returning all animals")
    void findAll_shouldReturnListOfAnimals() {
        Animal animal = Animal.builder()
                .name("Milo")
                .age("3")
                .category("Tiger")
                .build();

        when(animalRepository.findAll()).thenReturn(List.of(animal));

        List<Animal> result = animalService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategory()).isEqualTo("Tiger");
    }
}