package com.pizzeriadiroma.pizzeria.service;

import com.pizzeriadiroma.pizzeria.dto.AddPizzaRequest;
import com.pizzeriadiroma.pizzeria.entity.Ingredient;
import com.pizzeriadiroma.pizzeria.entity.Pizza;
import com.pizzeriadiroma.pizzeria.entity.Tag;
import com.pizzeriadiroma.pizzeria.exception.PizzaNotFoundException;
import com.pizzeriadiroma.pizzeria.exception.ValidationException;
import com.pizzeriadiroma.pizzeria.repository.PizzaRepository;
import com.pizzeriadiroma.pizzeria.repository.TagRepository;
import com.pizzeriadiroma.pizzeria.repository.IngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.Normalizer;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Service
public class PizzaAdminService {

    private final PizzaRepository pizzaRepository;
    private final TagRepository tagRepository;
    private final IngredientRepository ingredientRepository;

    public PizzaAdminService(PizzaRepository pizzaRepository, TagRepository tagRepository, IngredientRepository ingredientRepository) {
        this.pizzaRepository = pizzaRepository;
        this.tagRepository = tagRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public AddPizzaRequest toForm(Pizza pizza) {
        AddPizzaRequest addPizzaRequest = new AddPizzaRequest();
        addPizzaRequest.setName(pizza.getName());
        addPizzaRequest.setDescription(pizza.getDescription());
        addPizzaRequest.setBasePrice(pizza.getBasePrice());
        addPizzaRequest.setImageUrl(pizza.getImageUrl());
        addPizzaRequest.setAvailable(pizza.getAvailable());
        addPizzaRequest.setFeatured(pizza.getFeatured());

        if (pizza.getTags() != null) {
            addPizzaRequest.setTagIds(pizza.getTags().stream().map(Tag::getId).toList());
        }
        if (pizza.getIngredients() != null) {
            addPizzaRequest.setIngredientIds(pizza.getIngredients().stream().map(Ingredient::getId).toList());
        }
        return addPizzaRequest;
    }

    public void applyFormToEntity(AddPizzaRequest form, Pizza pizza) {
        pizza.setName(form.getName());
        pizza.setDescription(form.getDescription());
        pizza.setBasePrice(form.getBasePrice());
        pizza.setImageUrl(form.getImageUrl());
        pizza.setAvailable(form.getAvailable() != null ? form.getAvailable() : Boolean.FALSE);
        pizza.setFeatured(form.getFeatured() != null ? form.getFeatured() : Boolean.FALSE);

        Set<Tag> tags = new HashSet<>();
        if (form.getTagIds() != null && !form.getTagIds().isEmpty()) {
            var foundTags = tagRepository.findAllById(form.getTagIds());
            if (foundTags.size() != form.getTagIds().size()) {
                throw new ValidationException("One or more selected tags do not exist.");
            }
            tags.addAll(foundTags);
        }
        pizza.setTags(tags);

        Set<Ingredient> ingredients = new HashSet<>();
        if (form.getIngredientIds() != null && !form.getIngredientIds().isEmpty()) {
            var foundIngredients = ingredientRepository.findAllById(form.getIngredientIds());
            if (foundIngredients.size() != form.getIngredientIds().size()) {
                throw new ValidationException("One or more selected ingredients do not exist.");
            }
            ingredients.addAll(foundIngredients);
        }
        pizza.setIngredients(ingredients);
    }

    public String uniqueSlugForName(String name, Integer currentId) {
        String slug = slugify(name);

        Pizza existing = pizzaRepository.findBySlug(slug).orElse(null);

        if (existing == null) {
            return slug;
        }
        if (existing.getId().equals(currentId)) {
            return slug;
        }

        throw new ValidationException(
                "This pizza name is already used (slug '" + slug + "'). Please choose a different name."
        );
    }


    private String slugify(String input) {
        if (!StringUtils.hasText(input)) {
            return "pizza";
        }

        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}+", "");

        return normalized
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
    }

    public void deleteById(Integer id) {
        Pizza pizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new PizzaNotFoundException(id));
        pizzaRepository.delete(pizza);
    }
}
