package com.pizzeriadiroma.pizzeria.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Tag name must not be blank.")
    @Size(min = 2, max = 50, message = "Pizza name must be between 2 and 50 characters.")
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String emoji;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<Pizza> pizzas = new HashSet<>();

    public Tag() {}

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmoji() {return emoji; }

    public void setEmoji(String emoji) { this.emoji = emoji; }

    public Set<Pizza> getPizzas() { return pizzas; }

    public void setPizzas(Set<Pizza> pizzas) { this.pizzas = pizzas; }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
