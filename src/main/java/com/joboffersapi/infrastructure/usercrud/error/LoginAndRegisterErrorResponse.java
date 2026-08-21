package com.joboffersapi.infrastructure.usercrud.error;

import lombok.Builder;

import java.util.List;

@Builder
record LoginAndRegisterErrorResponse(List<String> errors ) {
}
