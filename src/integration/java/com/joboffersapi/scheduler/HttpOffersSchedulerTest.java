package com.joboffersapi.scheduler;

import com.joboffersapi.BaseIntegrationTest;
import com.joboffersapi.domain.offerCRUD.OfferFetchable;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@TestPropertySource(properties = "job_offers.scheduler.enabled=true")
public class HttpOffersSchedulerTest extends BaseIntegrationTest {

    @MockitoSpyBean
    private OfferFetchable offerFetchable;

    @Test
    public void should_run_http_client_offers_2_times() {
        await().atMost(Duration.ofSeconds(5))
                .untilAsserted(() -> verify(offerFetchable, times(2)).fetchOffers());
    }
}
