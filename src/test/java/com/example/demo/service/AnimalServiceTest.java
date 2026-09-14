package com.example.demo.service;

import com.example.demo.dto.AnimalDTO;
import com.example.demo.entity.Animal;
import com.example.demo.repository.AnimalRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnimalServiceTest {

    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private AnimalService animalService;

    @Test
    @DisplayName("Should persist and returning animal DTO")
    void save_shouldPersistAndReturnAnimalDTO() {
        AnimalDTO animalDTO = AnimalDTO.builder()
                .name("Leo")
                .age("5")
                .category("Lion")
                .build();

        Animal animalEntity = Animal.builder()
                .id("test-uuid-123")
                .name("Leo")
                .age("5")
                .category("Lion")
                .build();

        when(animalRepository.save(any(Animal.class))).thenReturn(animalEntity);

        AnimalDTO saved = animalService.save(animalDTO);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isEqualTo("test-uuid-123");
        assertThat(saved.getName()).isEqualTo("Leo");
        assertThat(saved.getAge()).isEqualTo("5");
        assertThat(saved.getCategory()).isEqualTo("Lion");
        verify(animalRepository).save(any(Animal.class));
    }

    @Test
    @DisplayName("Should find all and returning all animal DTOs")
    void findAll_shouldReturnListOfAnimalDTOs() {
        Animal animalEntity = Animal.builder()
                .id("test-uuid-456")
                .name("Milo")
                .age("3")
                .category("Tiger")
                .build();

        when(animalRepository.findAll()).thenReturn(List.of(animalEntity));

        List<AnimalDTO> result = animalService.findAll();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo("test-uuid-456");
        assertThat(result.get(0).getName()).isEqualTo("Milo");
        assertThat(result.get(0).getAge()).isEqualTo("3");
        assertThat(result.get(0).getCategory()).isEqualTo("Tiger");
        verify(animalRepository).findAll();
    }
}