package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User ref = em.getReference(User.class, userId);
        meal.setUser(ref);

        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else {
            return em.merge(meal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        Query query = em.createQuery("DELETE FROM Meal m where m.id=:id and m.user.id=:user_id");
        return query.setParameter("user_id", userId).setParameter("id", id)
                .executeUpdate() != 0;

    }

    @Override
    public Meal get(int id, int userId) {
        Query query = em.createQuery("SELECT m FROM Meal m where m.user.id=:user_id and m.id=:id");
        query.setParameter("user_id", userId).setParameter("id", id);
        return (Meal) query.getSingleResult();
    }

    @Override
    public List<Meal> getAll(int userId) {
        Query query = em.createQuery(
                "SELECT m FROM Meal m where m.user.id=:user_id ORDER BY m.dateTime desc", Meal.class);
        query.setParameter("user_id", userId);
        return query.getResultList();

    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        Query query = em.createQuery(
                "SELECT m FROM Meal m where m.user.id=:user_id " +
                        "and m.dateTime >= :startDateTime and m.dateTime < :endDateTime");
        query
                .setParameter("user_id", userId)
                .setParameter("startDateTime", startDateTime)
                .setParameter("endDateTime", endDateTime);

        return query.getResultList();
    }

}