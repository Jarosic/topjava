package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        return service.create(meal);
    }

    public Meal update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        return service.update(meal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        if (service.get(id).getUserId() != SecurityUtil.authUserId() || service.get(id) == null)  {
            throw new NotFoundException("Meal not found!");
        }
        service.delete(id);
    }

    public Meal get(int id) {
        log.info("get {}", id);
        if (service.get(id).getUserId() != SecurityUtil.authUserId() || service.get(id) == null)  {
            throw new NotFoundException("Meal not found!");
        }
        return service.get(id);
    }

    public Collection<Meal> getAll() {
        log.info("getAll {}");
        return service.getAll();
    }

    public List<MealTo> getMealTo() {
        log.info("getMealTo");
        return MealsUtil.getTos(getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealTo> getAllWithFilter(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("getAllWithFilter");
        LocalDate finalStartDate = (startDate == null) ?
                LocalDate.of(1, Month.JANUARY, 1) : startDate;
        LocalDate finalEndDate = (endDate == null) ?
                LocalDate.of(9999, 12, 31) : endDate;

        LocalTime finalStartTime = (startTime == null) ?
                LocalTime.of(0, 0) : startTime;
        LocalTime finalEndTime = (endTime == null) ?
                LocalTime.of(23, 59) : endTime;

        return getMealTo().stream()
                .filter(mealTo -> DateTimeUtil.isBetweenHalfOpen(mealTo.getDateTime(), finalStartDate, finalEndDate))
                .filter(mealTo -> DateTimeUtil.isBetweenHalfOpen(mealTo.getDateTime(), finalStartTime, finalEndTime))
                .peek(System.out::println)
                .collect(Collectors.toList());
    }

}