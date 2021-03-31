package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@RestController
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {
    private Logger log = LoggerFactory.getLogger(MealRestController.class);
    static final String REST_URL = "/rest/meals";

    @Override
    @GetMapping
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public Meal get(@PathVariable int id) {
        return super.get(id);
    }
    //создаем новую запись с хедерами(ResponseEntity дает нам такую возможность)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@RequestBody Meal meal) {
        Meal created = super.create(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Meal meal, @PathVariable int id) {
        super.update(meal, id);
    }

    //принимаем в параметрах String
//    @GetMapping("/filter")
//    public List<MealTo> getBetween(
//            @RequestParam(required = false) String startDateLocal,
//            @RequestParam(required = false) String endDateLocal,
//            @RequestParam(required = false) String startTimeLocal,
//            @RequestParam(required = false) String endTimeLocal
//
//    ) {
//        LocalDate startDate = parseLocalDate(startDateLocal);
//        LocalDate endDate = parseLocalDate(endDateLocal);
//        LocalTime startTime = parseLocalTime(startTimeLocal);
//        LocalTime endTime = parseLocalTime(endTimeLocal);
//
//        return super.getBetween(startDate, startTime, endDate, endTime);
//    }

    //принимаем в параметрах LocalDateTime
    @GetMapping("/filter")
    public List<MealTo> getBetween(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                    LocalDateTime startDateLocal,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                    LocalDateTime endDateLocal,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                    LocalDateTime startTimeLocal,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                    LocalDateTime endTimeLocal

    ) {
        LocalDate startDate = startDateLocal.toLocalDate();
        LocalDate endDate = endDateLocal.toLocalDate();
        LocalTime startTime = startTimeLocal.toLocalTime();
        LocalTime endTime = endTimeLocal.toLocalTime();

        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}