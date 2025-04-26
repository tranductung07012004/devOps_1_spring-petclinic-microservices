package org.springframework.samples.petclinic.vets.model;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link Vet}
 */
class VetTest {

    @Test
    void testBasicGettersAndSetters() {
        // Arrange
        Vet vet = new Vet();
        Integer id = 1;
        String firstName = "James";
        String lastName = "Carter";
        
        // Act
        vet.setId(id);
        vet.setFirstName(firstName);
        vet.setLastName(lastName);
        
        // Assert
        assertEquals(id, vet.getId());
        assertEquals(firstName, vet.getFirstName());
        assertEquals(lastName, vet.getLastName());
    }
    
    @Test
    void testSpecialties() {
        // Arrange
        Vet vet = new Vet();
        Specialty radiology = new Specialty();
        radiology.setName("radiology");
        
        Specialty surgery = new Specialty();
        surgery.setName("surgery");
        
        // Act
        vet.addSpecialty(radiology);
        vet.addSpecialty(surgery);
        
        // Assert
        assertEquals(2, vet.getNrOfSpecialties());
        
        List<Specialty> specialties = vet.getSpecialties();
        assertEquals(2, specialties.size());
        assertTrue(specialties.contains(radiology));
        assertTrue(specialties.contains(surgery));
    }
    
    @Test
    void testEmptySpecialties() {
        // Arrange
        Vet vet = new Vet();
        
        // Act & Assert
        assertEquals(0, vet.getNrOfSpecialties());
        assertTrue(vet.getSpecialties().isEmpty());
    }
    
    @Test
    void testSpecialtiesUnmodifiable() {
        // Arrange
        Vet vet = new Vet();
        Specialty radiology = new Specialty();
        radiology.setName("radiology");
        vet.addSpecialty(radiology);
        
        // Act
        List<Specialty> specialties = vet.getSpecialties();
        
        // Assert - verify that the returned list is unmodifiable
        assertThrows(UnsupportedOperationException.class, () -> specialties.add(new Specialty()));
    }
} 