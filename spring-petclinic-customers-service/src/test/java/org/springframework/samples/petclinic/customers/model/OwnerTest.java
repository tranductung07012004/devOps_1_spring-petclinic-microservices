package org.springframework.samples.petclinic.customers.model;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OwnerTest {

    @Test
    void testGettersAndSetters() {
        Owner owner = new Owner();
        //owner.setId(1);
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setAddress("123 Main St");
        owner.setCity("Boston");
        owner.setTelephone("1234567890");

        assertEquals(1, owner.getId());
        assertEquals("John", owner.getFirstName());
        assertEquals("Doe", owner.getLastName());
        assertEquals("123 Main St", owner.getAddress());
        assertEquals("Boston", owner.getCity());
        assertEquals("1234567890", owner.getTelephone());
    }

    @Test
    void testAddPet() {
        Owner owner = new Owner();
        Pet pet = new Pet();
        pet.setName("Buddy");

        owner.addPet(pet);
        List<Pet> pets = owner.getPets();

        assertEquals(1, pets.size());
        assertEquals("Buddy", pets.get(0).getName());
    }

    @Test
    void testPetsCollection() {
        Owner owner = new Owner();
        Pet pet1 = new Pet();
        pet1.setName("Fluffy");
        pet1.setId(1);
        
        Pet pet2 = new Pet();
        pet2.setName("Buddy");
        pet2.setId(2);
        
        owner.addPet(pet1);
        owner.addPet(pet2);
        
        assertEquals(2, owner.getPets().size());
        assertTrue(owner.getPets().contains(pet1));
        assertTrue(owner.getPets().contains(pet2));
        
        assertEquals(owner, pet1.getOwner());
        assertEquals(owner, pet2.getOwner());
    }
    
    @Test
    void testGetPet() {
        Owner owner = new Owner();
        
        // Test with null pets
        assertNull(owner.getPet("Fluffy", true));
        
        // Add a pet and find by name (case insensitive)
        Pet pet1 = new Pet();
        pet1.setName("Fluffy");
        pet1.setId(1);
        owner.addPet(pet1);
        
        Pet foundPet = owner.getPet("fluffy", true);
        assertNotNull(foundPet);
        assertEquals(pet1, foundPet);
        
        // Find by name (case sensitive)
        foundPet = owner.getPet("Fluffy", false);
        assertNotNull(foundPet);
        assertEquals(pet1, foundPet);
        
        // Test not finding pet with wrong name
        assertNull(owner.getPet("NotExisting", false));
    }
    
    @Test
    void testGetPetWithIgnoredNewProperty() {
        Owner owner = new Owner();
        
        // Add a new pet
        Pet pet1 = new Pet();
        pet1.setName("Fluffy");
        pet1.setId(null); // New pet, no ID
        owner.addPet(pet1);
        
        // Should not find pet if ignoreNew is true
        assertNull(owner.getPet("Fluffy", true));
        
        // Should find pet if ignoreNew is false
        Pet foundPet = owner.getPet("Fluffy", false);
        assertNotNull(foundPet);
        assertEquals(pet1, foundPet);
    }
    
    @Test
    void testToString() {
        Owner owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Doe");
        
        String toString = owner.toString();
        assertTrue(toString.contains("John"));
        assertTrue(toString.contains("Doe"));
    }
    
    @Test
    void testEmptyPets() {
        Owner owner = new Owner();
        assertNotNull(owner.getPets());
        assertEquals(0, owner.getPets().size());
    }
}
