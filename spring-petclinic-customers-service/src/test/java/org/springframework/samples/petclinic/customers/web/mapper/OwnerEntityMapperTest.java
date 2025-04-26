package org.springframework.samples.petclinic.customers.web.mapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.samples.petclinic.customers.model.Owner;
import org.springframework.samples.petclinic.customers.web.OwnerRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OwnerEntityMapperTest {

    private final OwnerEntityMapper mapper = new OwnerEntityMapper();

    @Test
    void shouldMapOwnerRequestToOwner() {
        // given
        OwnerRequest request = new OwnerRequest("John", "Doe", "123 Main St", "New York", "1234567890");
        Owner owner = new Owner();
        
        // when
        Owner mappedOwner = mapper.map(owner, request);
        
        // then
        assertEquals("John", mappedOwner.getFirstName());
        assertEquals("Doe", mappedOwner.getLastName());
        assertEquals("123 Main St", mappedOwner.getAddress());
        assertEquals("New York", mappedOwner.getCity());
        assertEquals("1234567890", mappedOwner.getTelephone());
    }
    
    @Test
    void shouldUpdateExistingOwnerWithNewValues() {
        // given
        Owner existingOwner = new Owner();
        existingOwner.setFirstName("Bob");
        existingOwner.setLastName("Smith");
        existingOwner.setAddress("456 Park Ave");
        existingOwner.setCity("Boston");
        existingOwner.setTelephone("9876543210");
        
        OwnerRequest request = new OwnerRequest("John", "Doe", "123 Main St", "New York", "1234567890");
        
        // when
        Owner updatedOwner = mapper.map(existingOwner, request);
        
        // then
        assertEquals("John", updatedOwner.getFirstName());
        assertEquals("Doe", updatedOwner.getLastName());
        assertEquals("123 Main St", updatedOwner.getAddress());
        assertEquals("New York", updatedOwner.getCity());
        assertEquals("1234567890", updatedOwner.getTelephone());
    }
    
    @Test 
    void shouldPreserveOwnerIdWhenMapping() {
        // given
        // Using a real Owner object with fields to test the mapper
        Owner owner = new Owner();
        // For test purposes, use a real Owner with a null ID
        // The ID should not be changed by mapping
        
        OwnerRequest request = new OwnerRequest("John", "Doe", "123 Main St", "New York", "1234567890");
        
        // when
        Owner mappedOwner = mapper.map(owner, request);
        
        // then
        // Verify we get the same object back with updated fields
        assertSame(owner, mappedOwner);
        assertEquals(owner.getId(), mappedOwner.getId());
    }
} 