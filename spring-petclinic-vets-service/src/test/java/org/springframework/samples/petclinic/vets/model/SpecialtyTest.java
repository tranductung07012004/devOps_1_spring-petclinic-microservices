package org.springframework.samples.petclinic.vets.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link Specialty}
 */
class SpecialtyTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        Specialty specialty = new Specialty();
        String name = "radiology";
        
        // Act
        specialty.setName(name);
        
        // Assert
        assertEquals(name, specialty.getName());
        assertNull(specialty.getId()); // ID should be null until persisted
    }
} 