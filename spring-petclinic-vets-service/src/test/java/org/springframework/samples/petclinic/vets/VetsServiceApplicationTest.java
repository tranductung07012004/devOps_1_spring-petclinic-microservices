package org.springframework.samples.petclinic.vets;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Test class to verify that the application context loads successfully
 */
@SpringBootTest
@ActiveProfiles("test")
class VetsServiceApplicationTest {

    @Test
    void contextLoads() {
        // This test will succeed if the Spring context loads without error
    }
} 