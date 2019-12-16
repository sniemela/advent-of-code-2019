package org.aoc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class Day04Test {

    @Test
    public void shouldValidatePart1Passwords() {
        assertTrue(Day04.isValidPasswordPart1("111111"));
        assertFalse(Day04.isValidPasswordPart1("223450"));
        assertFalse(Day04.isValidPasswordPart1("123789"));
    }
    
    @Test
    public void shouldValidatePart2Passwords() {
        assertTrue(Day04.isValidPasswordPart2("112233"));
        assertFalse(Day04.isValidPasswordPart2("123444"));
        assertTrue(Day04.isValidPasswordPart2("111122"));
    }
}
