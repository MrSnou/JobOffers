package com.joboffersapi.infrastructure.offercrud.http.util;

import lombok.Builder;


@Builder
record ApiValidationErrorDto(String message) {
}
