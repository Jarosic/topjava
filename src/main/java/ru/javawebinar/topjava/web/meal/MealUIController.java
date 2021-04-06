package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/ajax/meals/", produces = MediaType.APPLICATION_JSON_VALUE)
public class MealUIController extends AbstractMealController {
    private Logger log = LoggerFactory.getLogger(MealUIController.class);

    @Override
    @GetMapping
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@RequestParam String datetime,
                       @RequestParam String description,
                       @RequestParam String calories) {
        Meal meal = new Meal(
                null,
                LocalDateTime.parse(datetime),
                description,
                Integer.parseInt(calories)
        );
        log.info("create: {}", meal.toString());
        super.create(meal);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete: {}", id);
        super.delete(id);
    }
}
