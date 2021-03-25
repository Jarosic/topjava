package ru.javawebinar.topjava.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class JspMealController {

    private final MealService service;

    public JspMealController(MealService service) {
        this.service = service;
    }

    @GetMapping("/meals")
    public String getMeals(Model model) {
        List<MealTo> meals = MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()),
                SecurityUtil.authUserCaloriesPerDay());
        model.addAttribute("meals",  meals);
        return "meals";
    }

    @PostMapping("/create")
    public String setMeal(@RequestParam(value = "id", required = false) String id,
                          @RequestParam(value = "dateTime") String dateTime,
                          @RequestParam(value = "description") String description,
                          @RequestParam(value = "calories") String calories) {
        Meal meal = new Meal();

        meal.setDateTime(LocalDateTime.parse(dateTime));
        meal.setDescription(description);
        meal.setCalories(Integer.parseInt(calories));

        if (id.equals("")) {
            service.create(meal, SecurityUtil.authUserId());
        } else {
            meal.setId(Integer.parseInt(id));
            service.update(meal, SecurityUtil.authUserId());
        }
        return "redirect:/meals";
    }

    @GetMapping("/create")
    public String create(@RequestParam(value = "id", required = false) String id, Model model ) {
        Meal meal = new Meal();

        if (id != null) {
            meal = service.get(Integer.parseInt(id), SecurityUtil.authUserId());
        }

        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(value = "id") String id) {
        service.delete(Integer.parseInt(id), SecurityUtil.authUserId());
        return "redirect:/meals";
    }
}
