package com.pizzeriadiroma.pizzeria.service;

import com.pizzeriadiroma.pizzeria.entity.Tag;
import com.pizzeriadiroma.pizzeria.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }
}
