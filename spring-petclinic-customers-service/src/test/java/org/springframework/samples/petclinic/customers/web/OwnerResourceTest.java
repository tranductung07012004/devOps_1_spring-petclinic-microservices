package org.springframework.samples.petclinic.customers.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OwnerResource.class)
@ActiveProfiles("test")
class OwnerResourceTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    OwnerRepository ownerRepository;

    @MockBean
    OwnerEntityMapper ownerEntityMapper;

    @Test
    void shouldGetAnOwnerInJSonFormat() throws Exception {
        Owner owner = setupOwner();
        given(ownerRepository.findById(1)).willReturn(Optional.of(owner));

        mvc.perform(get("/owners/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.firstName").value("John"))
            .andExpect(jsonPath("$.lastName").value("Doe"))
            .andExpect(jsonPath("$.address").value("123 Main St"))
            .andExpect(jsonPath("$.city").value("New York"))
            .andExpect(jsonPath("$.telephone").value("1234567890"));
    }

    @Test
    void shouldReturnNotFoundForNonExistingOwner() throws Exception {
        given(ownerRepository.findById(99)).willReturn(Optional.empty());

        mvc.perform(get("/owners/99").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("null"));
    }

    @Test
    void shouldGetAllOwnersInJsonFormat() throws Exception {
        List<Owner> owners = Arrays.asList(setupOwner(), setupAnotherOwner());
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
    void shouldCreateNewOwner() throws Exception {
        Owner owner = setupOwner();
        String ownerRequest = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"address\":\"123 Main St\",\"city\":\"New York\",\"telephone\":\"1234567890\"}";
        
        given(ownerEntityMapper.map(any(Owner.class), any(OwnerRequest.class))).willReturn(owner);
        given(ownerRepository.save(any(Owner.class))).willReturn(owner);

        mvc.perform(post("/owners")
            .content(ownerRequest)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.firstName").value("John"))
            .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void shouldUpdateExistingOwner() throws Exception {
        Owner owner = setupOwner();
        String ownerRequest = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"address\":\"123 Main St\",\"city\":\"New York\",\"telephone\":\"1234567890\"}";
        
        given(ownerRepository.findById(1)).willReturn(Optional.of(owner));
        
        mvc.perform(put("/owners/1")
            .content(ownerRequest)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
        
        verify(ownerEntityMapper).map(eq(owner), any(OwnerRequest.class));
        verify(ownerRepository).save(owner);
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistingOwner() throws Exception {
        String ownerRequest = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"address\":\"123 Main St\",\"city\":\"New York\",\"telephone\":\"1234567890\"}";
        
        given(ownerRepository.findById(99)).willReturn(Optional.empty());
        
        mvc.perform(put("/owners/99")
            .content(ownerRequest)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    private Owner setupOwner() {
        Owner owner = Mockito.mock(Owner.class);
        when(owner.getId()).thenReturn(1);
        when(owner.getFirstName()).thenReturn("John");
        when(owner.getLastName()).thenReturn("Doe");
        when(owner.getAddress()).thenReturn("123 Main St");
        when(owner.getCity()).thenReturn("New York");
        when(owner.getTelephone()).thenReturn("1234567890");
        return owner;
    }

    private Owner setupAnotherOwner() {
        Owner owner = Mockito.mock(Owner.class);
        when(owner.getId()).thenReturn(2);
        when(owner.getFirstName()).thenReturn("Jane");
        when(owner.getLastName()).thenReturn("Smith");
        when(owner.getAddress()).thenReturn("456 Park Ave");
        when(owner.getCity()).thenReturn("Boston");
        when(owner.getTelephone()).thenReturn("0987654321");
        return owner;
    }
} 