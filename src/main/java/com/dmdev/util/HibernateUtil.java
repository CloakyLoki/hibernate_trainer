package com.dmdev.util;

import com.dmdev.converter.BirthDayConverter;
import com.dmdev.entity.User;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

public static SessionFactory buildSessionFactory(){
    var configuration = new Configuration();
    configuration.setPhysicalNamingStrategy((new CamelCaseToUnderscoresNamingStrategy()));
    configuration.addAnnotatedClass(User.class);
    configuration.addAttributeConverter(new BirthDayConverter(), true); //автоприменение конвертера, без аннотаций
    configuration.registerTypeOverride(new JsonBinaryType()); //регистрация нового типа данных
    configuration.configure();

    return configuration.buildSessionFactory();
}
}
