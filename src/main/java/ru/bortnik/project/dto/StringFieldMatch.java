package ru.bortnik.project.dto;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Используется для проверки строковых полей, которые должны иметь одинаковый префикс
 */
@Constraint(validatedBy = StringFieldValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface StringFieldMatch {

    String message() default "Fields values don't match";

    /**
     * Название первого поля для проверки совпадения
     * Для корректной проверки тип поля должен быть {@link String}
     *
     * @return
     */
    String field();

    /**
     * Название второго поля для проверки совпадения
     * Для корректной проверки тип поля должен быть {@link String}
     *
     * @return
     */
    String matchTo();

    /**
     * Количество символов, которые должны совпадать
     * symbols = -1: полное совпадение
     * symbols = 0: могут быть полностью разными
     * symbols = N > 0: должны совпадать первые N символов
     *
     * @return
     */
    int length() default -1;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
