package org.aoc;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class Day02Test {

    @Test
    public void shouldInterpretProgram() {
        assertArrayEquals(new int[] { 2,0,0,0,99 }, Day02.interp(new int[] { 1,0,0,0,99 }));
        assertArrayEquals(new int[] { 2,3,0,6,99 }, Day02.interp(new int[] { 2,3,0,3,99 }));
        assertArrayEquals(new int[] { 2,4,4,5,99,9801 }, Day02.interp(new int[] { 2,4,4,5,99,0 }));
        assertArrayEquals(new int[] { 30,1,1,4,2,5,6,0,99 }, Day02.interp(new int[] { 1,1,1,4,99,5,6,0,99 }));
    }
}
