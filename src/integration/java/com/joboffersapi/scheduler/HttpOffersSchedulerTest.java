package com.joboffersapi.scheduler;

import com.joboffersapi.BaseIntegrationTest;
import com.joboffersapi.domain.offercrud.OfferFetchable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(properties = "job_offers.scheduler.enabled=true")
public class HttpOffersSchedulerTest extends BaseIntegrationTest {

    @MockitoSpyBean
    private OfferFetchable offerFetchable;

    @Test
    @DisplayName("Should fetch offers from given offer fetchable interface object implementation 2 times with scheduler.")
    public void should_run_http_client_offers_2_times() {
        await().atMost(Duration.ofSeconds(5))
                .untilAsserted(() -> verify(offerFetchable, times(2)).fetchOffers());
    }
}
