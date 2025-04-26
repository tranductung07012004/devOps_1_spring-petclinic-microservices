package org.springframework.samples.petclinic.customers.web;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.customers.model.Owner;
import org.springframework.samples.petclinic.customers.model.OwnerRepository;
import org.springframework.samples.petclinic.customers.model.Pet;
import org.springframework.samples.petclinic.customers.model.PetRepository;
import org.springframework.samples.petclinic.customers.model.PetType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Maciej Szarlinski
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(PetResource.class)
@ActiveProfiles("test")
class PetResourceTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    PetRepository petRepository;

    @MockBean
    OwnerRepository ownerRepository;

    @Test
    void shouldGetAPetInJSonFormat() throws Exception {

        Pet pet = setupPet();

        given(petRepository.findById(2)).willReturn(Optional.of(pet));


        mvc.perform(get("/owners/2/pets/2").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value(2))
            .andExpect(jsonPath("$.name").value("Basil"))
            .andExpect(jsonPath("$.type.id").value(6));
    }
    
    @Test
    void shouldGetAllPetTypes() throws Exception {
        PetType dog = new PetType();
        dog.setId(1);
        dog.setName("Dog");
        
        PetType cat = new PetType();
        cat.setId(2);
        cat.setName("Cat");
        
        List<PetType> petTypes = Arrays.asList(dog, cat);
        
        given(petRepository.findPetTypes()).willReturn(petTypes);
        
        mvc.perform(get("/petTypes").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("Dog"))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].name").value("Cat"));
    }
    
    @Test
    void shouldCreateNewPet() throws Exception {
        Owner owner = new Owner();
        owner.setId(1);
        owner.setFirstName("John");
        owner.setLastName("Doe");
        
        PetType dog = new PetType();
        dog.setId(1);
        dog.setName("Dog");
        
        Pet pet = new Pet();
        pet.setId(1);
        pet.setName("Fluffy");
        pet.setType(dog);
        pet.setBirthDate(LocalDate.of(2020, 1, 1));
        
        given(ownerRepository.findById(1)).willReturn(Optional.of(owner));
        given(petRepository.findPetTypeById(1)).willReturn(Optional.of(dog));
        given(petRepository.save(any(Pet.class))).willReturn(pet);
        
        mvc.perform(post("/owners/1/pets")
            .content("{\"name\":\"Fluffy\",\"birthDate\":\"2020-01-01\",\"typeId\":1}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
        
        verify(petRepository).save(any(Pet.class));
    }
    
    @Test
    void shouldReturn404WhenCreatingPetForNonExistingOwner() throws Exception {
        given(ownerRepository.findById(999)).willReturn(Optional.empty());
        
        mvc.perform(post("/owners/999/pets")
            .content("{\"name\":\"Fluffy\",\"birthDate\":\"2020-01-01\",\"typeId\":1}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
    
    @Test
    void shouldUpdateExistingPet() throws Exception {
        PetType cat = new PetType();
        cat.setId(2);
        cat.setName("Cat");
        
        Pet existingPet = new Pet();
        existingPet.setId(1);
        existingPet.setName("Fluffy");
        
        given(petRepository.findById(1)).willReturn(Optional.of(existingPet));
        given(petRepository.findPetTypeById(2)).willReturn(Optional.of(cat));
        given(petRepository.save(any(Pet.class))).willReturn(existingPet);
        
        mvc.perform(put("/owners/1/pets/1")
            .content("{\"id\":1,\"name\":\"FluffyUpdated\",\"birthDate\":\"2020-01-01\",\"typeId\":2}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
        
        verify(petRepository).save(any(Pet.class));
    }
    
    @Test
    void shouldReturn404WhenUpdatingNonExistingPet() throws Exception {
        given(petRepository.findById(999)).willReturn(Optional.empty());
        
        mvc.perform(put("/owners/1/pets/999")
            .content("{\"id\":999,\"name\":\"FluffyUpdated\",\"birthDate\":\"2020-01-01\",\"typeId\":2}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    private Pet setupPet() {
        Owner owner = new Owner();
        owner.setFirstName("George");
        owner.setLastName("Bush");

        Pet pet = new Pet();

        pet.setName("Basil");
        pet.setId(2);

        PetType petType = new PetType();
        petType.setId(6);
        pet.setType(petType);

        owner.addPet(pet);
        return pet;
    }
}
