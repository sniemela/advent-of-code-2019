package org.aoc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class Day4Test {

    @Test
    public void shouldValidatePart1Passwords() {
        assertTrue(Day4.isValidPasswordPart1("111111"));
        assertFalse(Day4.isValidPasswordPart1("223450"));
        assertFalse(Day4.isValidPasswordPart1("123789"));
    }
    
    @Test
    public void shouldValidatePart2Passwords() {
        assertTrue(Day4.isValidPasswordPart2("112233"));
        assertFalse(Day4.isValidPasswordPart2("123444"));
        assertTrue(Day4.isValidPasswordPart2("111122"));
    }
}
