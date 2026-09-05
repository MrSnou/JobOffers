package com.joboffersapi.infrastructure.offercrud.scheduler;

import com.joboffersapi.domain.offercrud.OfferFacade;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name="job-offers.scheduler.enabled", havingValue = "true")
class JobOffersSchedulerConfig {

    @Bean
    JobOffersScheduler jobOffersScheduler(OfferFacade offerFacade) {
        return new JobOffersScheduler(offerFacade);
    }
}
