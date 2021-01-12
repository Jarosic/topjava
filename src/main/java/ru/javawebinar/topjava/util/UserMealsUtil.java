package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
        Map<LocalDate, Integer> caloriesList = new HashMap<>();

        LocalDateTime dateTime = null;
        int dayOfMonth = meals.get(0).getDateTime().getDayOfMonth();
        int year = meals.get(0).getDateTime().getYear();
        Month month = meals.get(0).getDateTime().getMonth();
        int calories = 0;

        for (UserMeal userMeal : meals) {
            dateTime = userMeal.getDateTime();

            if (dateTime.getDayOfMonth() != dayOfMonth || dateTime.getYear() != year || dateTime.getMonth() != month) {
                calories = 0;
            }

            calories += userMeal.getCalories();

            caloriesList.put(dateTime.toLocalDate(), calories);

            dayOfMonth = dateTime.getDayOfMonth();
            year = dateTime.getYear();
            month = dateTime.getMonth();
        }

        for (UserMeal userMeal: meals) {
            dateTime = userMeal.getDateTime();

            if (TimeUtil.isBetweenHalfOpen(dateTime.toLocalTime(), startTime, endTime)) {
                calories = userMeal.getCalories();
                userMealWithExcessList.add(new UserMealWithExcess(dateTime, userMeal.getDescription(), calories, caloriesList.get(dateTime.toLocalDate()) > caloriesPerDay));
            }
        }
        return userMealWithExcessList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }
}
