package org.aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class Day5Test {

    @Test
    public void testPart1() {
        long[] code = new long[] { 1002,4,3,4,33 };
        Day5.interp(0, code);
        assertEquals(99, code[code.length - 1]);
    }
    
    @Test
    public void testPart2() {
        long[] code = new long[] { 3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31, 1106, 0, 36, 98, 0,
                0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104, 999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46,
                98, 99 };
        assertEquals(999, Day5.interp(7, Arrays.copyOf(code, code.length)));
        assertEquals(1000, Day5.interp(8, Arrays.copyOf(code, code.length)));
        assertEquals(1001, Day5.interp(9, Arrays.copyOf(code, code.length)));
    }
}
