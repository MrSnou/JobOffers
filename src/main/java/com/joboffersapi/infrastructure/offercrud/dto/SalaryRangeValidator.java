package com.joboffersapi.infrastructure.offercrud.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

class SalaryRangeValidator implements ConstraintValidator<ValidSalaryRange, AddOfferRequest> {

    @Override
    public boolean isValid(AddOfferRequest request, ConstraintValidatorContext context) {
        if (request.salaryMin() == null || request.salaryMax() == null) {
            return true;
        }
        return request.salaryMax() >= request.salaryMin();
    }
}
