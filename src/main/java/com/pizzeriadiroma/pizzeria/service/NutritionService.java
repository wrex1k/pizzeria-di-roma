package com.pizzeriadiroma.pizzeria.service;

import com.pizzeriadiroma.pizzeria.entity.Pizza;
import com.pizzeriadiroma.pizzeria.entity.PizzaSize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class NutritionService {

    public Map<String, Integer> calculate(Pizza pizza, PizzaSize size) {

        BigDecimal totalWeight = BigDecimal.ZERO;
        BigDecimal totalCalories = BigDecimal.ZERO;
        BigDecimal totalProtein = BigDecimal.ZERO;
        BigDecimal totalCarbs = BigDecimal.ZERO;
        BigDecimal totalFat = BigDecimal.ZERO;

        for (var pi : pizza.getIngredients()) {
            var ing = pi.getIngredient();
            BigDecimal grams = pi.getQuantity();

            BigDecimal cal = ing.getCaloriesPer100g().multiply(grams).divide(BigDecimal.valueOf(100));
            BigDecimal protein = ing.getProteinsPer100g().multiply(grams).divide(BigDecimal.valueOf(100));
            BigDecimal carbs = ing.getCarbsPer100g().multiply(grams).divide(BigDecimal.valueOf(100));
            BigDecimal fat = ing.getFatsPer100g().multiply(grams).divide(BigDecimal.valueOf(100));

            totalWeight = totalWeight.add(grams);
            totalCalories = totalCalories.add(cal);
            totalProtein = totalProtein.add(protein);
            totalCarbs = totalCarbs.add(carbs);
            totalFat = totalFat.add(fat);
        }

        Map<String, Integer> out = new HashMap<>();
        out.put("weight", totalWeight.intValue());
        out.put("calories", totalCalories.intValue());
        out.put("protein", totalProtein.intValue());
        out.put("carbs", totalCarbs.intValue());
        out.put("fat", totalFat.intValue());

        return out;
    }

}

