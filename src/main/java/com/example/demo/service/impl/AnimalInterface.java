package com.example.demo.service.impl;

import com.example.demo.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Used by default when you extends from Animal
public interface AnimalInterface extends JpaRepository<Animal, String> {
}
