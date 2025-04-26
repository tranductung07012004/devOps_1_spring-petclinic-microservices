package org.springframework.samples.petclinic.customers.model;

import org.junit.jupiter.api.Test;
import java.util.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PetTest {

    @Test
    void testGetId() {
        //given
        Pet pet = new Pet();

        //when
        pet.setId(1);

        //then
        assertEquals(1, pet.getId());
    }

    @Test
    void testGetName() {
        //given
        Pet pet = new Pet();

        //when
        pet.setName("Leo");

        //then
        assertEquals("Leo", pet.getName());
    }

    @Test
    void testGetBirthDate() {
        //given
        Pet pet = new Pet();
        LocalDate birthDate = LocalDate.now();

        //when
        pet.setBirthDate(birthDate);

        //then
        assertEquals(birthDate, pet.getBirthDate());
    }

    @Test
    void testGetType() {
        //given
        Pet pet = new Pet();
        PetType petType = new PetType();
        petType.setId(6);

        //when
        pet.setType(petType);

        //then
        assertEquals(petType, pet.getType());
    }

    @Test
    void testGetOwner() {
        //given
        Pet pet = new Pet();
        Owner owner = new Owner();
        owner.setId(1);

        //when
        pet.setOwner(owner);

        //then
        assertEquals(owner, pet.getOwner());
    }
    
    @Test
    void testIsNew() {
        Pet pet = new Pet();
        assertTrue(pet.isNew());
        
        pet.setId(1);
        assertEquals(false, pet.isNew());
    }
    
    @Test
    void testToString() {
        Pet pet = new Pet();
        pet.setName("Leo");
        pet.setId(1);
        
        String toString = pet.toString();
        assertTrue(toString.contains("Leo"));
        assertTrue(toString.contains("1"));
    }
    
    @Test
    void testFullConstructor() {
        LocalDate birthDate = LocalDate.of(2020, 1, 1);
        Owner owner = new Owner();
        owner.setId(1);
        PetType petType = new PetType();
        petType.setId(1);
        
        Pet pet = new Pet(1, "Leo", birthDate, petType, owner);
        
        assertEquals(1, pet.getId());
        assertEquals("Leo", pet.getName());
        assertEquals(birthDate, pet.getBirthDate());
        assertEquals(petType, pet.getType());
        assertEquals(owner, pet.getOwner());
    }
    
    @Test
    void testDefaultConstructor() {
        Pet pet = new Pet();
        
        assertNull(pet.getId());
        assertNull(pet.getName());
        assertNull(pet.getBirthDate());
        assertNull(pet.getType());
        assertNull(pet.getOwner());
    }

    @Test
    void testPetSettersAndGetters() {
        Pet pet = new Pet();
        pet.setId(1);
        pet.setName("Buddy");
        pet.setBirthDate(new Date());

        PetType type = new PetType();
        type.setId(2);
        type.setName("Dog");
        pet.setType(type);

        Owner owner = new Owner();
        owner.setFirstName("John");
        pet.setOwner(owner);

        assertEquals(1, pet.getId());
        assertEquals("Buddy", pet.getName());
        assertNotNull(pet.getBirthDate());
        assertEquals("Dog", pet.getType().getName());
        assertEquals("John", pet.getOwner().getFirstName());
    }

    @Test
    void testPetEqualsAndHashCode() {
        Pet pet1 = new Pet();
        pet1.setId(1);
        pet1.setName("Max");

        Pet pet2 = new Pet();
        pet2.setId(1);
        pet2.setName("Max");

        assertEquals(pet1, pet2);
        assertEquals(pet1.hashCode(), pet2.hashCode());
    }

    @Test
    void testPetEqualsWithDifferentObjects() {
        Pet pet1 = new Pet();
        pet1.setId(1);
        pet1.setName("Buddy");

        Pet pet2 = new Pet();
        pet2.setId(2);
        pet2.setName("Buddy");

        assertNotEquals(pet1, pet2);
    }

    @Test
    void testPetEqualsWithNull() {
        Pet pet = new Pet();
        pet.setId(1);
        pet.setName("Buddy");

        assertNotEquals(pet, null);
    }

    @Test
    void testPetEqualsWithDifferentClass() {
        Pet pet = new Pet();
        pet.setId(1);
        pet.setName("Buddy");

        String differentObject = "This is a string";
        assertNotEquals(pet, differentObject);
    }

    @Test
    void testPetHashCodeWithDifferentObjects() {
        Pet pet1 = new Pet();
        pet1.setId(1);
        pet1.setName("Buddy");

        Pet pet2 = new Pet();
        pet2.setId(2);
        pet2.setName("Buddy");

        assertNotEquals(pet1.hashCode(), pet2.hashCode());
    }
}
