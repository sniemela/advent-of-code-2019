package org.aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class Day01Test {

    @Test
    public void shouldCalculateFuelRequirementBasedOnModuleMass() {
        Day01 day1 = new Day01();
        assertEquals(2, day1.getModuleFuelRequirement(12));
        assertEquals(2, day1.getModuleFuelRequirement(14));
        assertEquals(654, day1.getModuleFuelRequirement(1969));
        assertEquals(33583, day1.getModuleFuelRequirement(100756));
    }

    @Test
    public void part1() {
        assertEquals(4, new Day01().part1(Arrays.asList(12, 14)));
    }

    @Test
    public void shouldCalculateTotalFuelRequirementByMassAndFuel() {
        Day01 day1 = new Day01();
        assertEquals(50346, day1.getFuelRequiredByMassAndFuel(100756));
        assertEquals(966, day1.getFuelRequiredByMassAndFuel(1969));
    }

    @Test
    public void part2() {
        assertEquals(51312, new Day01().part2(Arrays.asList(100756, 1969)));
    }
}
