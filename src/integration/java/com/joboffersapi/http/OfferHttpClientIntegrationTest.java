package com.joboffersapi.http;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.joboffersapi.domain.offercrud.OfferFetchable;
import com.joboffersapi.domain.offercrud.exception.OfferFetchingException;
import com.joboffersapi.infrastructure.offercrud.http.OfferFetcherRestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpTimeoutException;
import java.time.Duration;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

class OfferHttpClientIntegrationTest {

    @RegisterExtension
    public static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    private OfferFetchable offerFetchable;
    private OfferFetchable offerFetchableWith1000msWaitingPeriod;

    @BeforeEach
    void setUp() {
        offerFetchable = new OfferFetcherRestTemplate(
                new RestTemplate(), wireMockServer.baseUrl());

        offerFetchableWith1000msWaitingPeriod = new OfferFetcherRestTemplate(new RestTemplateBuilder()
                .connectTimeout(Duration.ofMillis(1000))
                .readTimeout(Duration.ofMillis(1000))
                .build(), wireMockServer.baseUrl());
    }


    @Test
    @DisplayName("Should throw OfferFetchingException when connection reset.")
    void should_throw_OfferFetchingException_when_connection_reset() {
        // Given
        wireMockServer.stubFor(WireMock.get("/offers")
        .willReturn(WireMock.aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", "application/json")
                .withFault(Fault.CONNECTION_RESET_BY_PEER)));
        // When
        Throwable throwable = catchThrowable(() -> offerFetchable.fetchOffers());
        // Then
        assertThat(throwable).isInstanceOf(OfferFetchingException.class);
        assertThat(throwable.getCause()).isInstanceOf(ResourceAccessException.class);
    }

    @Test
    @DisplayName("Should throw OfferFetchingException when exceeded readTimeout")
    void should_throw_OfferFetchingException_when_exceeded_readTimeout() {
        // Given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withFixedDelay(3000)
                        .withBody("[]")));
        // When
        long start = System.currentTimeMillis();
        Throwable throwable = catchThrowable(() -> offerFetchableWith1000msWaitingPeriod.fetchOffers());
        long elapsed = System.currentTimeMillis() - start;
        // Then
        assertThat(elapsed).isLessThan(2000);
        assertThat(throwable).isInstanceOf(OfferFetchingException.class);
        assertThat(throwable.getCause()).isInstanceOf(ResourceAccessException.class);
        assertThat(throwable.getCause().getCause()).isInstanceOf(HttpTimeoutException.class);
    }

}
