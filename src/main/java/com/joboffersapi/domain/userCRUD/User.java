package com.joboffersapi.domain.userCRUD;


import com.joboffersapi.domain.model.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Getter
@Setter
@Builder
class User extends BaseEntity {

    private Long id;
    private String username;
    private String email;
    private String password;
}
