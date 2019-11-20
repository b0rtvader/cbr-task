package ru.bortnik.second.dto;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class StringFieldValidator implements ConstraintValidator<StringFieldMatch, Object> {

    private String field;
    private String matchTo;
    private int length;

    @Override
    public void initialize(StringFieldMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.matchTo = constraintAnnotation.matchTo();
        this.length = constraintAnnotation.length();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        Object fieldValue = new BeanWrapperImpl(o).getPropertyValue(field);
        Object matchToValue = new BeanWrapperImpl(o).getPropertyValue(matchTo);

        if (fieldValue == null || matchToValue == null) {
            return false;
        }

        String first = fieldValue.toString();
        String second = matchToValue.toString();
        if (length == -1) {
            return Objects.equals(first, second);
        }

        if (first.length() < length || second.length() < length) {
            return false;
        }

        return Objects.equals(substring(first), substring(second));
    }

    private String substring(String s) {
        return s.substring(0, length);
    }
}
