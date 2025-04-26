package org.springframework.samples.petclinic.customers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class CustomersServiceApplicationTest {

    @Test
    void contextLoads() {
        // This test will fail if application context cannot start
    }
} 