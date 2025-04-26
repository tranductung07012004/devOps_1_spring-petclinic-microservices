package org.springframework.samples.petclinic.customers.web;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Maciej Szarlinski
 */
class PetRequestTest {

    @Test
    void testAllFieldsConstructor() {
        LocalDate birthDate = LocalDate.of(2020, 1, 1);
        PetRequest petRequest = new PetRequest(1, "Fluffy", birthDate, 2);
        
        assertEquals(1, petRequest.id());
        assertEquals("Fluffy", petRequest.name());
        assertEquals(birthDate, petRequest.birthDate());
        assertEquals(2, petRequest.typeId());
    }
    
    @Test
    void testCopyConstructor() {
        LocalDate birthDate = LocalDate.of(2020, 1, 1);
        PetRequest originalRequest = new PetRequest(1, "Fluffy", birthDate, 2);
        PetRequest copiedRequest = new PetRequest(originalRequest);
        
        assertEquals(originalRequest.id(), copiedRequest.id());
        assertEquals(originalRequest.name(), copiedRequest.name());
        assertEquals(originalRequest.birthDate(), copiedRequest.birthDate());
        assertEquals(originalRequest.typeId(), copiedRequest.typeId());
    }
    
    @Test
    void testWithMethods() {
        LocalDate birthDate = LocalDate.of(2020, 1, 1);
        PetRequest petRequest = new PetRequest(1, "Fluffy", birthDate, 2);
        
        LocalDate newBirthDate = LocalDate.of(2021, 2, 2);
        PetRequest updatedRequest = petRequest
            .withId(2)
            .withName("Rex")
            .withBirthDate(newBirthDate)
            .withTypeId(3);
        
        assertEquals(2, updatedRequest.id());
        assertEquals("Rex", updatedRequest.name());
        assertEquals(newBirthDate, updatedRequest.birthDate());
        assertEquals(3, updatedRequest.typeId());
        
        // Original should be unchanged
        assertEquals(1, petRequest.id());
        assertEquals("Fluffy", petRequest.name());
        assertEquals(birthDate, petRequest.birthDate());
        assertEquals(2, petRequest.typeId());
    }
    
    @Test
    void testDefaultConstructorAndSetters() {
        PetRequest petRequest = new PetRequest();
        
        assertNull(petRequest.id());
        assertNull(petRequest.name());
        assertNull(petRequest.birthDate());
        assertNull(petRequest.typeId());
    }
}
