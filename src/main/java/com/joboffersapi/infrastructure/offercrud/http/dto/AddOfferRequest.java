package com.joboffersapi.infrastructure.offercrud.http.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;

// TODO : Default Values for salary MIN/MAX and those should not be required. validationMessages.properties.

@ValidSalaryRange
@Builder
public record AddOfferRequest(
        @NotEmpty(message = "Offer title cannot be empty.")
        @Size(min = 1, max = 30, message = "Size have to fit into 1-30 characters range.")
        String title,

        @NotEmpty(message = "Offer company have to contain at least 1 character.")
        @Size(min = 1, max = 50, message = "Company name have to fit into 1-50 characters range.")
        String company,

        @NotNull(message = "Offer minimal salary have to contain at least 1 character.")
        @PositiveOrZero(message = "Salary have to be positive number or zero.")
        Double salaryMin,

        @NotNull(message = "Offer maximum salary have to contain at least 1 character.")
        @PositiveOrZero(message = "Salary have to be positive number or zero.")
        Double salaryMax,

        @NotEmpty(message = "Offer url have to contain at least 1 character.")
        String url,

        @NotEmpty(message = "Source of offer have to contain at least 1 character.")
        String source,

        @NotNull(message = "Estimated salary cannot be null.")
        Boolean salary_estimated
) {
}
