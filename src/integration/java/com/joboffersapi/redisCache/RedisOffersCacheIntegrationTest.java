package com.joboffersapi.redisCache;

import com.joboffersapi.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Duration;

@AutoConfigureMockMvc(addFilters = false)
class RedisOffersCacheIntegrationTest extends BaseIntegrationTest {

    @Container
    private static final GenericContainer<?> REDIS;

    static {
        REDIS = new GenericContainer<>("redis:latest").withExposedPorts(6379);
        REDIS.start();
    }

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("spring.redis.port", REDIS::getFirstMappedPort);
        registry.add("spring.cache.type", () -> "redis");
        registry.add("spring.cache.redis.time-to-live", () -> 2);
    }
    
    @Test
    @DisplayName("Should save offers and invalidate them by time to live value.")
    public void should_save_offers_to_cache_and_invalidate_by_time_to_live() throws Exception {
        /// 1. Should save to cache offers request.
        // Given
        assertThat(redisTemplate.keys("jobOffers*")).isEmpty();

        // When
        mockMvc.perform(get("/offers").accept(MediaType.APPLICATION_JSON))    // First saving data to cache
                .andExpect(status().isOk());
        mockMvc.perform(get("/offers").accept(MediaType.APPLICATION_JSON));   // 2x quick manual check what log4j2 prints in console.
        mockMvc.perform(get("/offers").accept(MediaType.APPLICATION_JSON));   // Should be 3x "/offers - Endpont hit!" and
                                                                                        // 1x "OfferService | findAllOffersFromDb - fetching all offers from database." as an actual database fetch.
        // Then
        assertThat(redisTemplate.keys("jobOffers*")).isNotEmpty();

        /// 2. Should invalidate offers saved in cache.
        // Given && When && Then
        await().atMost(Duration.ofSeconds(6))
                .pollInterval(Duration.ofMillis(500))
                .untilAsserted(() ->
                        assertThat(redisTemplate.keys("jobOffers*")).isEmpty());

    }
}
