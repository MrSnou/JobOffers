package com.joboffersapi.infrastructure.offercrud.apivalidation;

import lombok.Builder;

import java.util.List;

@Builder
record ApiValidationErrorDto(
        List<String> errors
) {


}
