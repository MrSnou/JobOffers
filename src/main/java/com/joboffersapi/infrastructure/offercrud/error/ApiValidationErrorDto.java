package com.joboffersapi.infrastructure.offercrud.error;

import lombok.Builder;

import java.util.List;

@Builder
record ApiValidationErrorDto(
        List<String> errors
) {


}
