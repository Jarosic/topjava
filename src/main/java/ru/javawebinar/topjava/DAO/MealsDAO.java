package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MealsDAO {
    private final List<Meal> mealsList;

    public MealsDAO() {
        mealsList = new CopyOnWriteArrayList<>(Arrays.asList(
                new Meal(new AtomicInteger(1), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(new AtomicInteger(2), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(new AtomicInteger(3), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(new AtomicInteger(4), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(new AtomicInteger(5), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(new AtomicInteger(6), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(new AtomicInteger(7), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        ));
    }

    public void addMeal(Meal meal) {
        System.out.println(meal.getId());
        mealsList.add(meal);
    }

    public List<Meal> getAll() {
        return mealsList;
    }

    public void delete(int id) {
        for (Meal m : mealsList) {
            if (m.getId().get() == id) {
                mealsList.remove(m);
            }
        }
    }

    public Meal getMealById(int id) {
        Meal newMeal = new Meal();
        Meal oldMeal = null;
        int i = 0;

        for (Meal m : mealsList) {
            if (m.getId().get() == id) {
                oldMeal = mealsList.get(i);
            }
            i++;
        }

        newMeal.setId(new AtomicInteger(id));
        newMeal.setCalories(oldMeal.getCalories());
        newMeal.setDateTime(oldMeal.getDateTime());
        newMeal.setDescription(oldMeal.getDescription());

        return newMeal;
    }

    public void updateMeal(Meal meal) {
        int i = 0;
        for (Meal m : mealsList) {
            if (m.getId().get() == meal.getId().get()) {
                mealsList.set(i, meal);
            }
            i++;
        }
    }
}
