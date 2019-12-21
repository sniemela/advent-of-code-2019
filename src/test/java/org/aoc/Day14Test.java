package org.aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.aoc.util.FileUtils;
import org.junit.jupiter.api.Test;

public class Day14Test {

    @Test
    public void part1() {
        assertEquals(31, Day14.part1(FileUtils.readAllLines(Day14Test.class, "day14", "test.txt")));
    }
    
    @Test
    public void part2() {
        assertEquals(460664, Day14.part2(FileUtils.readAllLines(Day14Test.class, "day14", "part2.txt")));
    }
}
