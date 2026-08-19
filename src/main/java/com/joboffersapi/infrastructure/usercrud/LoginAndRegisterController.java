package com.joboffersapi.infrastructure.usercrud;

import com.joboffersapi.domain.usercrud.UserFacade;
import com.joboffersapi.infrastructure.security.TokenGenerator;
import com.joboffersapi.infrastructure.usercrud.dto.LoginRequestDto;
import com.joboffersapi.infrastructure.usercrud.dto.JwtResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
public class LoginAndRegisterController {

    UserFacade userFacade;
    TokenGenerator tokenGenerator;

    @PostMapping("/token")
    public ResponseEntity<JwtResponseDto> authenticateAndGenerateToken(@Valid @RequestBody LoginRequestDto loginRequest) {
        return null;
    }






}
