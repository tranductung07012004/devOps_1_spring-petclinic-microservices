package org.springframework.samples.petclinic.customers.web;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OwnerRequestTest {

    @Test
    void testAllFieldsConstructor() {
        OwnerRequest ownerRequest = new OwnerRequest("John", "Doe", "123 Main St", "Boston", "1234567890");
        
        assertEquals("John", ownerRequest.firstName());
        assertEquals("Doe", ownerRequest.lastName());
        assertEquals("123 Main St", ownerRequest.address());
        assertEquals("Boston", ownerRequest.city());
        assertEquals("1234567890", ownerRequest.telephone());
    }
    
    @Test
    void testCopyConstructor() {
        OwnerRequest originalRequest = new OwnerRequest("John", "Doe", "123 Main St", "Boston", "1234567890");
        OwnerRequest copiedRequest = new OwnerRequest(originalRequest);
        
        assertEquals(originalRequest.firstName(), copiedRequest.firstName());
        assertEquals(originalRequest.lastName(), copiedRequest.lastName());
        assertEquals(originalRequest.address(), copiedRequest.address());
        assertEquals(originalRequest.city(), copiedRequest.city());
        assertEquals(originalRequest.telephone(), copiedRequest.telephone());
    }
    
    @Test
    void testWithMethods() {
        OwnerRequest ownerRequest = new OwnerRequest("John", "Doe", "123 Main St", "Boston", "1234567890");
        
        OwnerRequest updatedRequest = ownerRequest
            .withFirstName("Jane")
            .withLastName("Smith")
            .withAddress("456 Elm St")
            .withCity("New York")
            .withTelephone("0987654321");
        
        assertEquals("Jane", updatedRequest.firstName());
        assertEquals("Smith", updatedRequest.lastName());
        assertEquals("456 Elm St", updatedRequest.address());
        assertEquals("New York", updatedRequest.city());
        assertEquals("0987654321", updatedRequest.telephone());
        
        // Original should be unchanged
        assertEquals("John", ownerRequest.firstName());
        assertEquals("Doe", ownerRequest.lastName());
        assertEquals("123 Main St", ownerRequest.address());
        assertEquals("Boston", ownerRequest.city());
        assertEquals("1234567890", ownerRequest.telephone());
    }
    
    @Test
    void testDefaultConstructor() {
        OwnerRequest ownerRequest = new OwnerRequest();
        
        assertNull(ownerRequest.firstName());
        assertNull(ownerRequest.lastName());
        assertNull(ownerRequest.address());
        assertNull(ownerRequest.city());
        assertNull(ownerRequest.telephone());
    }
}
