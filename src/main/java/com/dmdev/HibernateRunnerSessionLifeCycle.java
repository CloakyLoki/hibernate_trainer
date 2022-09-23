package com.dmdev;

import com.dmdev.converter.BirthDayConverter;
import com.dmdev.entity.Birthday;
import com.dmdev.entity.Role;
import com.dmdev.entity.User;
import com.dmdev.util.HibernateUtil;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.Session;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateRunnerSessionLifeCycle {


    public static void main(String[] args) throws SQLException {

        var user = User.builder() //состояние user'a - Transient (только создан, не имеет отношения ни к какой сессии)
                .username("ivan@gmail.com")
                .firstname("Ivanov")
                .lastname("Ivan")
                .build();

        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (var session1 = sessionFactory.openSession()) {
                session1.beginTransaction();

                session1.saveOrUpdate(user); //состояние user'a - Persistent по отношению к session1, Transient к session2

                session1.getTransaction().commit();
            }
            try (var session2 = sessionFactory.openSession()) {
                session2.beginTransaction();

                //session2.delete(user); //сначала через неявный get Persistent, потом delete

                user.setFirstname("sveta");
              //  session2.refresh(user); <---- накладывает изменения ИЗ БД! firstname снова "Ivanov"
              //  session2.merge(user); <---- накладывает изменения В БД! В БД записывается "sveta"

                session2.getTransaction().commit();
            }
        }
    }
}