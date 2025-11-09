package com.pizzeriadiroma.pizzeria.repository;

import com.pizzeriadiroma.pizzeria.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
