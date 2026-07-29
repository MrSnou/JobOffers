package com.joboffersapi.domain.usercrud;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Getter
@Setter
@Builder
class User{

    private String id;
    private String username;
    private String email;
    private String password;
}
