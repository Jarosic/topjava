package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "John Doe", "email1@mail.ru", "password", Role.ADMIN));
            adminUserController.create(new User(null, "Iren Doe", "email2@mail.ru", "password", Role.USER));
            adminUserController.create(new User(null, "Yaroslav Rybalka", "email3@mail.ru", "password", Role.ADMIN));
            adminUserController.create(new User(null, "Viktoria Pogorelova", "email4@mail.ru", "password", Role.USER));

            MealRestController restController = appCtx.getBean(MealRestController.class);

            restController.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 9, 0), "Завтрак", 500, SecurityUtil.authUserId()));
            restController.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 29, 20, 0), "Обед", 1000, SecurityUtil.authUserId()));
            restController.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500, SecurityUtil.authUserId()));
            restController.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000, SecurityUtil.authUserId()));
            restController.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500, SecurityUtil.authUserId()));
            restController.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100, SecurityUtil.authUserId()));
            restController.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000, SecurityUtil.authUserId()));
            restController.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500, SecurityUtil.authUserId()));
            restController.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410, SecurityUtil.authUserId()));

        }
    }
}
