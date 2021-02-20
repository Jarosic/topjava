package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(UserServlet.class);
    private UserRepository repository;

    @Override
    public void init() {
        repository = new InMemoryUserRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to userList");
        String action = request.getParameter("action");
        log.info("getAll " + repository.getAll());

        switch (action == null ? "all" : action) {
            case "delete":

                break;
            case "create":
            case "update":

                break;
            case "all":
            default:
                log.info("getAll " + repository.getAll().toString());
                request.setAttribute("users", repository.getAll());
                request.getRequestDispatcher("/users.jsp").forward(request, response);
                break;
        }
    }
}
