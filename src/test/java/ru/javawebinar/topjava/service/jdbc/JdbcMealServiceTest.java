package ru.javawebinar.topjava.service.jdbc;

import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;

@IfProfileValue(name = "spring.profiles.active", values = {"JDBC"})
@ActiveProfiles(JDBC)
public class JdbcMealServiceTest extends AbstractMealServiceTest {
}