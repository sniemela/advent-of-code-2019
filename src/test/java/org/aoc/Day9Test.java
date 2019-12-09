package org.aoc;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day9Test {

    @Test
    public void part1() {
        long[] code = new long[] { 109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99 };
        assertArrayEquals(new long[] { 109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99 }, Day9.run(code));
        
        long result = Day9.run(new long[] { 1102,34915192,34915192,7,4,7,99,0 })[0];
        assertEquals(16, String.valueOf(result).length());
        
        assertArrayEquals(new long[] { 1125899906842624L }, Day9.run(new long[] { 104,1125899906842624L,99 }));
    }
}
