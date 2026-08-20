package com.joboffersapi.infrastructure.usercrud;

import com.joboffersapi.domain.usercrud.UserFacade;
import com.joboffersapi.infrastructure.security.TokenGenerator;
import com.joboffersapi.infrastructure.usercrud.dto.UserLoginRequestDto;
import com.joboffersapi.infrastructure.usercrud.dto.UserRegisterRequestDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.joboffersapi.infrastructure.usercrud.util.UserCrudMapper.mapFromUserLoginRequestDtoToLoginRequest;
import static com.joboffersapi.infrastructure.usercrud.util.UserCrudMapper.mapFromUserRegisterRequestDtoToRegisterRequest;

@RestController
@AllArgsConstructor
@Log4j2
public class LoginAndRegisterController {

    UserFacade userFacade;
    TokenGenerator tokenGenerator;

    @PostMapping("/token")
    public ResponseEntity<String> authenticateAndGenerateToken(@Valid @RequestBody UserLoginRequestDto loginRequest) {
        userFacade.login(mapFromUserLoginRequestDtoToLoginRequest(loginRequest));
        return ResponseEntity.ok("Successfully logged in");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid UserRegisterRequestDto userRegisterRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        userFacade.register(mapFromUserRegisterRequestDtoToRegisterRequest(
                                userRegisterRequestDto)).message());
    }


}
