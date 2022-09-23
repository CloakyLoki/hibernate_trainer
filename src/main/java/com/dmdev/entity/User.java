package com.dmdev.entity;

import com.dmdev.converter.BirthDayConverter;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users") //если имя класса и таблицы различаются
public class User {

    @Id
    private String username; //ключ должен реализовывать Serializable
    private String firstname;
    private String lastname;

    @Convert(converter = BirthDayConverter.class)
    @Column(name = "birth_date")
    private Birthday birthDate;

    /**
     * jsonb - это встроенное лаконичное название
     * Если его нет, используется аннотация класса
     * @TypeDef(name = "dmdev", typeClass = JsonBinaryType.class)
     */
   @Type(type = "jsonb")
    private String info;

    @Enumerated(EnumType.STRING)
    private Role role;
}

//POJO - Plain Old Java Object
