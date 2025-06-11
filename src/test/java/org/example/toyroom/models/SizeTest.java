package org.example.toyroom.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SizeTest {

    @Test
    void testToStringReturnsCapitalizedName() {
        assertEquals("Large", Size.LARGE.toString());
        assertEquals("Medium", Size.MEDIUM.toString());
        assertEquals("Small", Size.SMALL.toString());
    }
}
