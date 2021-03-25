package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController {
    private final Logger LOG = LoggerFactory.getLogger(JspMealController.class);

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

    @GetMapping("/filter")
    public String filter(HttpServletRequest request, Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));

        List<Meal> mealsDateFiltered = service.getBetweenInclusive(startDate, endDate, SecurityUtil.authUserId());
        List<MealTo> mealsToFiltered =
                MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
        LOG.info("Filtered: {}", mealsToFiltered.toString());
        model.addAttribute("meals", mealsToFiltered);
        return "meals";

    }
}
