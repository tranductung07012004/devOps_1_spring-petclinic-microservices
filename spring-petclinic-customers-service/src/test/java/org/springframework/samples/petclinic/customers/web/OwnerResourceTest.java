package org.springframework.samples.petclinic.customers.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.customers.model.Owner;
import org.springframework.samples.petclinic.customers.model.OwnerRepository;
import org.springframework.samples.petclinic.customers.web.mapper.OwnerEntityMapper;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OwnerResource.class)
@ActiveProfiles("test")
class OwnerResourceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OwnerRepository ownerRepository;

    @MockBean
    private OwnerEntityMapper ownerEntityMapper;

    @Test
    void shouldGetOwnerById() throws Exception {
        Owner owner = setupOwner();
        given(ownerRepository.findById(1)).willReturn(Optional.of(owner));

        mvc.perform(get("/owners/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.firstName").value("John"))
            .andExpect(jsonPath("$.lastName").value("Doe"))
            .andExpect(jsonPath("$.address").value("123 Main St"))
            .andExpect(jsonPath("$.city").value("Boston"))
            .andExpect(jsonPath("$.telephone").value("1234567890"));
    }

    @Test
    void shouldGetAllOwners() throws Exception {
        Owner owner1 = setupOwner();
        Owner owner2 = new Owner();
        owner2.setId(2);
        owner2.setFirstName("Jane");
        owner2.setLastName("Smith");
        owner2.setAddress("456 Elm St");
        owner2.setCity("New York");
        owner2.setTelephone("0987654321");

        List<Owner> owners = Arrays.asList(owner1, owner2);
        given(ownerRepository.findAll()).willReturn(owners);

        mvc.perform(get("/owners").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].firstName").value("John"))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].firstName").value("Jane"));
    }

    @Test
    void shouldCreateOwner() throws Exception {
        Owner newOwner = new Owner();
        newOwner.setId(1);
        
        OwnerRequest ownerRequest = new OwnerRequest();

        given(ownerEntityMapper.map(any(Owner.class), any(OwnerRequest.class))).willReturn(newOwner);
        given(ownerRepository.save(any(Owner.class))).willReturn(newOwner);

        mvc.perform(post("/owners")
            .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"address\":\"123 Main St\",\"city\":\"Boston\",\"telephone\":\"1234567890\"}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());

        verify(ownerRepository).save(any(Owner.class));
    }

    @Test
    void shouldUpdateOwner() throws Exception {
        Owner existingOwner = setupOwner();
        given(ownerRepository.findById(1)).willReturn(Optional.of(existingOwner));
        
        mvc.perform(put("/owners/1")
            .content("{\"firstName\":\"Johnny\",\"lastName\":\"Doe\",\"address\":\"123 Main St\",\"city\":\"Boston\",\"telephone\":\"1234567890\"}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        verify(ownerRepository).save(any(Owner.class));
    }
    
    @Test
    void shouldReturn404WhenUpdatingNonExistingOwner() throws Exception {
        given(ownerRepository.findById(999)).willReturn(Optional.empty());
        
        mvc.perform(put("/owners/999")
            .content("{\"firstName\":\"Johnny\",\"lastName\":\"Doe\",\"address\":\"123 Main St\",\"city\":\"Boston\",\"telephone\":\"1234567890\"}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    private Owner setupOwner() {
        Owner owner = new Owner();
        owner.setId(1);
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setAddress("123 Main St");
        owner.setCity("Boston");
        owner.setTelephone("1234567890");
        return owner;
    }
} 