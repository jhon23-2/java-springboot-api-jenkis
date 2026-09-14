package com.example.demo.rest;

import com.example.demo.dto.AnimalDTO;
import com.example.demo.service.AnimalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnimalRest.class)
class AnimalRestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AnimalService animalService;

    @Test
    @DisplayName("Should create animal and return DTO with 200 status")
    void save_shouldCreateAnimalAndReturnDTO() throws Exception {
        AnimalDTO inputDTO = AnimalDTO.builder()
                .name("Leo")
                .age("5")
                .category("Lion")
                .build();

        AnimalDTO savedDTO = AnimalDTO.builder()
                .id("test-uuid-123")
                .name("Leo")
                .age("5")
                .category("Lion")
                .build();

        when(animalService.save(any(AnimalDTO.class))).thenReturn(savedDTO);

        mockMvc.perform(post("/api/animals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("test-uuid-123"))
                .andExpect(jsonPath("$.name").value("Leo"))
                .andExpect(jsonPath("$.age").value("5"))
                .andExpect(jsonPath("$.category").value("Lion"));
    }

    @Test
    @DisplayName("Should return list of animal DTOs")
    void findAll_shouldReturnListOfAnimalDTOs() throws Exception {
        AnimalDTO animalDTO1 = AnimalDTO.builder()
                .id("test-uuid-456")
                .name("Milo")
                .age("3")
                .category("Tiger")
                .build();

        AnimalDTO animalDTO2 = AnimalDTO.builder()
                .id("test-uuid-789")
                .name("Simba")
                .age("4")
                .category("Lion")
                .build();

        when(animalService.findAll()).thenReturn(List.of(animalDTO1, animalDTO2));

        mockMvc.perform(get("/api/animals")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("test-uuid-456"))
                .andExpect(jsonPath("$[0].name").value("Milo"))
                .andExpect(jsonPath("$[0].category").value("Tiger"))
                .andExpect(jsonPath("$[1].id").value("test-uuid-789"))
                .andExpect(jsonPath("$[1].name").value("Simba"))
                .andExpect(jsonPath("$[1].category").value("Lion"));
    }
}
