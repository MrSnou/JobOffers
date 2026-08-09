package com.joboffersapi.infrastructure.offercrud.http.dto;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SalaryRangeValidator.class)
@interface ValidSalaryRange {
    String message() default "Maximum offered salary have to be greater or equal than minimum offered salary.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}