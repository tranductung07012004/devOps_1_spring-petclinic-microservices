package org.springframework.samples.petclinic.customers.web.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.customers.model.Owner;
import org.springframework.samples.petclinic.customers.web.OwnerRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OwnerEntityMapperTest {

    private final OwnerEntityMapper mapper = new OwnerEntityMapper();

    @Test
    void shouldMapOwnerRequestToOwner() {
        // Given
        Owner owner = new Owner();
        OwnerRequest request = new OwnerRequest("John", "Doe", "123 Main St", "Boston", "1234567890");
        
        // When
        Owner result = mapper.map(owner, request);
        
        // Then
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("123 Main St", result.getAddress());
        assertEquals("Boston", result.getCity());
        assertEquals("1234567890", result.getTelephone());
    }
    
    @Test
    void shouldMapOwnerRequestToExistingOwner() {
        // Given
        Owner existingOwner = new Owner();
        existingOwner.setId(1);
        existingOwner.setFirstName("Old First");
        existingOwner.setLastName("Old Last");
        existingOwner.setAddress("Old Address");
        existingOwner.setCity("Old City");
        existingOwner.setTelephone("0000000000");
        
        OwnerRequest request = new OwnerRequest("New First", "New Last", "New Address", "New City", "1111111111");
        
        // When
        Owner result = mapper.map(existingOwner, request);
        
        // Then
        assertEquals(1, result.getId()); // ID should be preserved
        assertEquals("New First", result.getFirstName());
        assertEquals("New Last", result.getLastName());
        assertEquals("New Address", result.getAddress());
        assertEquals("New City", result.getCity());
        assertEquals("1111111111", result.getTelephone());
    }
    
    @Test
    void shouldHandleNullValues() {
        // Given
        Owner owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setAddress("123 Main St");
        owner.setCity("Boston");
        owner.setTelephone("1234567890");
        
        OwnerRequest request = new OwnerRequest(null, null, null, null, null);
        
        // When
        Owner result = mapper.map(owner, request);
        
        // Then
        assertEquals(null, result.getFirstName());
        assertEquals(null, result.getLastName());
        assertEquals(null, result.getAddress());
        assertEquals(null, result.getCity());
        assertEquals(null, result.getTelephone());
    }
} 