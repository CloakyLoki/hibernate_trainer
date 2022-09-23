package com.dmdev.converter;

import com.dmdev.entity.Birthday;

import javax.persistence.AttributeConverter;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

public class BirthDayConverter implements AttributeConverter<Birthday, Date> {

    @Override
    public Date convertToDatabaseColumn(Birthday attribute) {
        return Optional.ofNullable(attribute)
                .map(Birthday::birthDate)
                .map(Date::valueOf)
                .orElse(null);


    }

    @Override
    public Birthday convertToEntityAttribute(Date dbData) {
        return Optional.ofNullable(dbData)
                .map(Date::toLocalDate)
                .map(Birthday::new)
                .orElse(null);
    }
}
