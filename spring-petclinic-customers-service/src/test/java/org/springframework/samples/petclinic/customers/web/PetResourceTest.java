package org.springframework.samples.petclinic.customers.web;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        
        mvc.perform(get("/petTypes"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("Dog"))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].name").value("Cat"));
    }
    
    @Test
    void shouldCreateNewPet() throws Exception {
        Owner owner = Mockito.mock(Owner.class);
        when(owner.getId()).thenReturn(1);
        
        PetType dogType = new PetType();
        dogType.setId(1);
        dogType.setName("Dog");
        
        String petRequest = "{"
            + "\"name\": \"Leo\","
            + "\"birthDate\": \"2020-09-07\","
            + "\"typeId\": 1"
            + "}";
        
        given(ownerRepository.findById(1)).willReturn(Optional.of(owner));
        given(petRepository.findPetTypeById(1)).willReturn(Optional.of(dogType));
        given(petRepository.save(any(Pet.class))).willAnswer(invocation -> {
            Pet savedPet = invocation.getArgument(0);
            savedPet.setId(3);
            return savedPet;
        });
        
        mvc.perform(post("/owners/1/pets")
            .content(petRequest)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(3))
            .andExpect(jsonPath("$.name").value("Leo"));
        
        verify(petRepository).save(any(Pet.class));
    }
    
    @Test
    void shouldReturnNotFoundWhenCreatingPetWithNonExistingOwner() throws Exception {
        String petRequest = "{"
            + "\"name\": \"Leo\","
            + "\"birthDate\": \"2020-09-07\","
            + "\"typeId\": 1"
            + "}";
        
        given(ownerRepository.findById(99)).willReturn(Optional.empty());
        
        mvc.perform(post("/owners/99/pets")
            .content(petRequest)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
    
    @Test
    void shouldUpdateExistingPet() throws Exception {
        PetType catType = new PetType();
        catType.setId(2);
        catType.setName("Cat");
        
        Pet existingPet = new Pet();
        existingPet.setId(2);
        existingPet.setName("Basil");
        
        String petRequest = "{"
            + "\"id\": 2,"
            + "\"name\": \"Basil Updated\","
            + "\"birthDate\": \"2019-07-12\","
            + "\"typeId\": 2"
            + "}";
        
        given(petRepository.findById(2)).willReturn(Optional.of(existingPet));
        given(petRepository.findPetTypeById(2)).willReturn(Optional.of(catType));
        
        mvc.perform(put("/owners/1/pets/2")
            .content(petRequest)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
        
        verify(petRepository).save(any(Pet.class));
    }
    
    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistingPet() throws Exception {
        String petRequest = "{"
            + "\"id\": 99,"
            + "\"name\": \"Not Found\","
            + "\"birthDate\": \"2020-09-07\","
            + "\"typeId\": 1"
            + "}";
        
        given(petRepository.findById(99)).willReturn(Optional.empty());
        
        mvc.perform(put("/owners/1/pets/99")
            .content(petRequest)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    private Pet setupPet() {
        Owner owner = Mockito.mock(Owner.class);
        when(owner.getFirstName()).thenReturn("George");
        when(owner.getLastName()).thenReturn("Bush");

        Pet pet = new Pet();

        pet.setName("Basil");
        pet.setId(2);

        PetType petType = new PetType();
        petType.setId(6);
        pet.setType(petType);

        pet.setOwner(owner);
        return pet;
    }
}
