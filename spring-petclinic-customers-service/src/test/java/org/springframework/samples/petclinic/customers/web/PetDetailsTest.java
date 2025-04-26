package org.springframework.samples.petclinic.customers.web;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.samples.petclinic.customers.model.Owner;
import org.springframework.samples.petclinic.customers.model.Pet;
import org.springframework.samples.petclinic.customers.model.PetType;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class PetDetailsTest {

    @Test
    void shouldMapPetToPetDetails() {
        // given
        Owner owner = Mockito.mock(Owner.class);
        when(owner.getId()).thenReturn(1);
        when(owner.getFirstName()).thenReturn("John");
        when(owner.getLastName()).thenReturn("Doe");
        
        PetType dogType = new PetType();
        dogType.setId(1);
        dogType.setName("Dog");
        
        Pet pet = Mockito.mock(Pet.class);
        when(pet.getId()).thenReturn(2);
        when(pet.getName()).thenReturn("Max");
        when(pet.getBirthDate()).thenReturn(new Date());
        when(pet.getType()).thenReturn(dogType);
        when(pet.getOwner()).thenReturn(owner);
        
        // when
        PetDetails petDetails = new PetDetails(pet);
        
        // then
        assertEquals(2, petDetails.id());
        assertEquals("Max", petDetails.name());
        assertEquals("John Doe", petDetails.owner());
        assertNotNull(petDetails.birthDate());
        assertNotNull(petDetails.type());
        assertEquals(1, petDetails.type().getId());
        assertEquals("Dog", petDetails.type().getName());
    }
    
    @Test
    void shouldHandlePetWithNullType() {
        // given
        Owner owner = Mockito.mock(Owner.class);
        when(owner.getId()).thenReturn(1);
        when(owner.getFirstName()).thenReturn("John");
        when(owner.getLastName()).thenReturn("Doe");
        
        Pet pet = Mockito.mock(Pet.class);
        when(pet.getId()).thenReturn(2);
        when(pet.getName()).thenReturn("Max");
        when(pet.getBirthDate()).thenReturn(new Date());
        when(pet.getOwner()).thenReturn(owner);
        when(pet.getType()).thenReturn(null);
        
        // when
        PetDetails petDetails = new PetDetails(pet);
        
        // then
        assertEquals(2, petDetails.id());
        assertEquals("Max", petDetails.name());
        assertNotNull(petDetails.birthDate());
        assertNull(petDetails.type());
    }
} 