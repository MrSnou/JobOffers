package com.joboffersapi.infrastructure.offerCRUD.http.scheduler;

import com.joboffersapi.domain.offerCRUD.OfferFacade;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name="job_offers.scheduler.enabled", havingValue = "true")
class JobOffersSchedulerConfig {

    @Bean
    JobOffersScheduler jobOffersScheduler(OfferFacade offerFacade) {
        return new JobOffersScheduler(offerFacade);
    }
}
