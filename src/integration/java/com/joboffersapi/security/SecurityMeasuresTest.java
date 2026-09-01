package com.joboffersapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.joboffersapi.BaseIntegrationTest;
import com.joboffersapi.infrastructure.security.JwtConfigurationProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import com.jayway.jsonpath.JsonPath;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SecurityMeasuresTest extends BaseIntegrationTest {

    @Autowired
    JwtConfigurationProperties jwtConfigurationProperties;

    @Nested
    @DisplayName("/register - Endpoint tests")
    class RegisterEndpointTests {
        @Test
        @DisplayName("Should return [201] - Created and String response.")
        void should_return_201_when_user_registers_with_correct_data() throws Exception {
            // Given && When
            ResultActions performRegister = mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content("""
                    {
                    "username":"TestUser",
                    "password":"TestPassword"
                    }
                    """.trim()));
            // Then
            assertThat(performRegister.andReturn().getResponse().getStatus()).isEqualTo(201);
            assertThat(performRegister.andReturn().getResponse().getContentAsString()).isEqualTo((String.format("User %s registered successfully.", "TestUser")));
        }

        @Test
        @DisplayName("Should return [400] - Bad Request status and errors")
        void should_return_400_when_user_registers_with_invalid_data() throws Exception {
            // Given && When
            ResultActions performRegister = mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON).content("""
                            {
                            "username":"",
                            "password":""
                            }
                            """.trim()));
            // Then
            performRegister.andExpectAll(
                    status().isBadRequest(),
                    jsonPath("$.errors", hasSize(4)),
                    jsonPath("$.errors",
                            containsInAnyOrder("Password cannot be empty.",
                                    "Username size have to be between 3 and 30 characters.",
                                    "Username cannot be empty.",
                                    "Password size have to be between 6 and 50 characters."))
            );
        }
    }

    @Nested
    @DisplayName("/login - Endpoint tests")
    class LoginEndpointTests {

        @BeforeEach
        void defaultTestUser() throws Exception {
            ResultActions performRegister = mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON).content("""
                            {
                            "username":"TestUser",
                            "password":"TestPassword"
                            }
                            """.trim()));
            assertThat(performRegister.andReturn().getResponse().getStatus())
                    .isEqualTo(201);
            assertThat(performRegister.andReturn().getResponse().getContentAsString())
                    .isEqualTo((String.format("User %s registered successfully.", "TestUser")));
        }

        @Test
        @DisplayName("Should return [200] - OK and JwtResponseDto with String Token and UserDto object.")
        void should_return_status_200_token_and_username_when_user_logins_with_correct_data() throws Exception {
            // Given && When
            ResultActions performLogin = mockMvc.perform(post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                            {
                            "username":"TestUser",
                            "password":"TestPassword"
                            }
                            """.trim()));
            // Then
            performLogin.andExpectAll(
                    status().isOk(),
                    jsonPath("$.token", is(matchesPattern("(^[A-Za-z0-9-_]*\\.[A-Za-z0-9-_]*\\.[A-Za-z0-9-_]*$)"))),
                    jsonPath("$.username", is("TestUser"))
            );
        }

        @Test
        @DisplayName("Should return [400] - Bad request and errors")
        void should_return_400_and_errors_when_user_logins_with_invalid_data() throws Exception {
            // Given && When
            ResultActions performLogin = mockMvc.perform(post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                            {
                            "username":"",
                            "password":""
                            }
                            """.trim())
            );
            // Then
            performLogin.andExpectAll(
                    status().isBadRequest(),
                    jsonPath("$.errors", hasSize(2)),
                    jsonPath("$.errors", containsInAnyOrder(
                            "Username cannot be empty.",
                            "Password cannot be empty."
                    ))

            );
        }

        @Test
        @DisplayName("Should return [401] - Unauthorized and errors")
        void should_return_Unauthorized_status_and_errors_when_user_logins_with_invalid_data() throws Exception {
            // Given && When
            ResultActions performLogin = mockMvc.perform(post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                            {
                            "username":"asd",
                            "password":"asd"
                            }
                            """.trim())
            );
            // Then
            performLogin.andExpectAll(
                    status().isUnauthorized(),
                    jsonPath("$.errors", hasSize(1)),
                    jsonPath("$.errors", containsInAnyOrder(
                            "Invalid username or password."
                    ))

            );
        }

        @Test
        @DisplayName("Should return [401] - Unauthorized and errors")
        void should_return_Unauthorized_status_and_errors_when_user_logins_with_invalid_password() throws Exception {
            // Given && When
            ResultActions performLogin = mockMvc.perform(post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                            {
                            "username":"TestUser",
                            "password":"asd"
                            }
                            """.trim())
            );
            // Then
            performLogin.andExpectAll(
                    status().isUnauthorized(),
                    jsonPath("$.errors", hasSize(1)),
                    jsonPath("$.errors", containsInAnyOrder(
                            "Invalid username or password."
                    ))

            );
        }
    }

    @Nested
    @DisplayName("Security tests")
    class SecurityCheckTest {

        private String validToken;


        @BeforeEach
        void registerAndLogin() throws Exception {
            mockMvc.perform(post("/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                        {"username":"TestUser","password":"TestPassword"}
                        """))
                    .andExpect(status().isCreated());

            MvcResult result = mockMvc.perform(post("/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                        {"username":"TestUser","password":"TestPassword"}
                        """))
                    .andExpect(status().isOk())
                    .andReturn();

            validToken = JsonPath.read(result.getResponse().getContentAsString(), "$.token");
        }

        @Test
        @DisplayName("Should return [200] when valid token provided.")
        void should_accept_request_with_valid_token() throws Exception {
            mockMvc.perform(get("/offers")
                            .header("Authorization", "Bearer " + validToken))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should return [401] when no Authorization header provided.")
        void should_reject_request_without_token() throws Exception {
            mockMvc.perform(get("/offers"))
                    .andExpect(status().isUnauthorized());
        }



        @Test
        @DisplayName("Should reject header without Bearer prefix.")
        void should_reject_header_without_bearer_prefix() throws Exception {
            mockMvc.perform(get("/offers")
                            .header("Authorization", "abc"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @DisplayName("Should reject token signed with different secret.")
        void should_reject_token_with_invalid_signature() throws Exception {
            String forgedToken = JWT.create()
                    .withSubject("TestUser")
                    .withIssuer("JobOffersApi - Backend")
                    .withExpiresAt(Instant.now().plusSeconds(720))
                    .sign(Algorithm.HMAC256("wrong-secret"));

            mockMvc.perform(get("/offers")
                            .header("Authorization", "Bearer " + forgedToken))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @DisplayName("Should reject expired token.")
        void should_reject_expired_token() throws Exception {
            String expiredToken = JWT.create()
                    .withSubject("TestUser")
                    .withIssuer(jwtConfigurationProperties.issuer())
                    .withIssuedAt(Instant.now().minusSeconds(7200))
                    .withExpiresAt(Instant.now().minusSeconds(3600))
                    .sign(Algorithm.HMAC256(jwtConfigurationProperties.secretKey()));

            mockMvc.perform(get("/offers")
                            .header("Authorization", "Bearer " + expiredToken))
                    .andExpectAll(
                            status().isUnauthorized(),
                            jsonPath("$.errors", containsInAnyOrder("Authentication required."))
                    );
        }

    }
}

