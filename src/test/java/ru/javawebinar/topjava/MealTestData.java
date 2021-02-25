package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int OTHER_USER = START_SEQ + 1;
    public static final List<Meal> meals = new ArrayList<>(Arrays.asList(
            new Meal(100002, LocalDateTime.of(2020, Month.JANUARY, 30, 8, 0), "Завтрак", 500),
            new Meal(100003, LocalDateTime.of(2020, Month.JANUARY, 30, 12, 0), "Обед", 1000),
            new Meal(100004, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 300),
            new Meal(100005, LocalDateTime.of(2020, Month.JANUARY, 31, 8, 0), "Завтрак", 700),
            new Meal(100006, LocalDateTime.of(2020, Month.JANUARY, 31, 12, 0), "Обед", 1000)

    ));
    public static final int ID = 100006;

    public static Meal getNew() {
        return  new Meal(LocalDateTime.of(2020, Month.MARCH, 31, 20, 0), "Обед", 500);
    }

    public static List<Meal> getSortedList() {
        return meals.stream()
                .sorted((x, y) -> y.getId().compareTo(x.getId()))
                .collect(Collectors.toList());
    }

    public static Meal getUpdated() {
        Meal updated = new Meal();
        updated.setId(ID);
        updated.setDateTime(LocalDateTime.of(2020, Month.MARCH, 30, 8, 0));
        updated.setDescription("Ужин");
        updated.setCalories(500);
        return updated;
    }

    public static Meal notNew() {
        return new Meal(ID,
                LocalDateTime.of(2020, Month.MARCH, 8, 8, 0),
                "Завтрак", 1000);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("user_id").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("id").isEqualTo(expected);
    }
}
