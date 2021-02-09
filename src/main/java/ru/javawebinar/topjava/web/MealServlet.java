package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.DAO.MealsDAO;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("redirect to mealList");

        List<MealTo> mealsList = MealsUtil.filteredByStreams(
                new MealsDAO().getMealsList(),
                LocalTime.of(0, 0),
                LocalTime.of(23, 0),
                MealsUtil.caloriesPerDay
        );
        req.setAttribute("mealsList", mealsList);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("meals.jsp");
        requestDispatcher.forward(req, resp);

    }
}
