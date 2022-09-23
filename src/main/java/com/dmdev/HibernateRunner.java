package com.dmdev;

import com.dmdev.converter.BirthDayConverter;
import com.dmdev.entity.Birthday;
import com.dmdev.entity.Role;
import com.dmdev.entity.User;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.Session;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateRunner {

    public static void main(String[] args) throws SQLException {
        var configuration = new Configuration();
        configuration.addAnnotatedClass(User.class); //добавить отслеживаемую сущность
        //указывает правило трансформации имен (либо ставить аннотацию над полем в Entity)
        // configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.addAttributeConverter(new BirthDayConverter(), true); //автоприменение конвертера, без аннотаций

        configuration.registerTypeOverride(new JsonBinaryType()); //регистрация нового типа данных
        configuration.configure();
        try (var sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction(); //обязательно

            var user = User.builder()
                    .username("ivan@gmail.com")
                    .firstname("Ivan")
                    .lastname("Ivanov")
                    .birthDate(new Birthday(LocalDate.of(2000, 1, 7)))
                    .info("""
                            {
                            "name": "Ivan",
                            "id": 25
                            }
                            """)
                    .role(Role.USER)
                    .build();
            session.save(user);

            //обновит user, если его нет, пробросит исключение
            //session.update(user);

            //обновит user, если его нет - создаст
            //session.saveOrUpdate(user);

            //удалит user, если его нет - ничего не произойдет
            //session.delete(user);

            var user = session.get(User.class, "ivan@gmail.com"); //как Hibernate определяет таблицу?

            session.getTransaction().commit(); //обязательно
        }
    }
}
