package org.springframework.samples.petclinic.customers.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PetTypeTest {

    @Test
    void testPetTypeSettersAndGetters() {
        PetType petType = new PetType();
        petType.setId(3);
        petType.setName("bird");

        assertEquals(3, petType.getId());
        assertEquals("bird", petType.getName());
    }
}
