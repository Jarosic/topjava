package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.DAO.MealsDAO;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    private static final String MEALS_LIST = "meals.jsp";
    private static final String ADD_OR_EDIT = "add_edit_meals.jsp";
    private final MealsDAO dao = new MealsDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("do get: redirect to mealList");
        resp.setContentType("text/html; charset=UTF-8");

        String forward = "";
        String action = req.getParameter("action");

        if (action != null) {
            if (action.equalsIgnoreCase("delete")) {
                int id = Integer.parseInt(req.getParameter("mealsId"));
                dao.delete(id);
                forward = MEALS_LIST;
                req.setAttribute("mealsList", listFilter(dao.getAll()));
            } else if (action.equalsIgnoreCase("update")) {

                int id = Integer.parseInt(req.getParameter("mealsId"));
                Meal meal = dao.getMealById(id);
                req.setAttribute("meal", meal);
                forward = ADD_OR_EDIT;
            }
        } else {
            req.setAttribute("mealsList", listFilter(dao.getAll()));
            forward = MEALS_LIST;
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher(forward);
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        LOG.debug("do post: redirect to mealList");

        Meal meal = new Meal();
        meal.setDateTime(LocalDateTime.parse(req.getParameter("date")));
        meal.setDescription(req.getParameter("description"));
        meal.setCalories(Integer.parseInt(req.getParameter("calories")));
        String id = req.getParameter("mealsId");

        if (id == null || id.isEmpty()) {
            AtomicInteger mealId = new AtomicInteger(dao.getAll().size() + 1);
            meal.setId(mealId);
            dao.addMeal(meal);
        } else {
            meal.setId(new AtomicInteger(Integer.parseInt(id)));
            dao.updateMeal(meal);
        }
        resp.sendRedirect("/topjava/meals");
    }

    private static List<MealTo> listFilter(List<Meal> list) {
        return MealsUtil.filteredByStreams(
                list,
                LocalTime.of(0, 0),
                LocalTime.of(23, 0),
                MealsUtil.caloriesPerDay
        );
    }
}
