package com.joboffersapi.feature;

import com.joboffersapi.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserFetchedAndCheckedCurrentOffersIntegrationTest extends BaseIntegrationTest {

    @Test
    @DisplayName("User fetched and checked current offers - Happy Path Test")
    void HappyPath() {
        // 1. User sending request to controller
        // 2. Service layer fetching offers from external site and saving them to db if not exists
        // 3. Service layer fetching offers from repository
        // 4. Service layer returning offers list to controller
        // 5. Controller returns data to user
    }
}
