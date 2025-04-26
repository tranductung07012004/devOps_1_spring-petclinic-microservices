package org.springframework.samples.petclinic.vets.system;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link VetsProperties} and {@link VetsProperties.Cache}
 */
class VetsPropertiesTest {

    @Test
    void testProperties() {
        // Arrange
        int ttl = 60;
        int heapSize = 100;
        VetsProperties.Cache cache = new VetsProperties.Cache(ttl, heapSize);
        VetsProperties properties = new VetsProperties(cache);
        
        // Act & Assert
        assertEquals(ttl, properties.cache().ttl());
        assertEquals(heapSize, properties.cache().heapSize());
    }
} 