package org.aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day01 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        List<Integer> moduleMasses = getModuleMasses(Path.of(Day01.class.getResource("day01/input.txt").toURI()));

        Day01 day1 = new Day01();

        System.out.println("Part 1 = " + day1.part1(moduleMasses));
        System.out.println("Part 2 = " + day1.part2(moduleMasses));
    }

    public static List<Integer> getModuleMasses(Path moduleFile) {
        try {
            return Files.readAllLines(moduleFile).stream().map(Integer::parseInt).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public int part1(List<Integer> moduleMasses) {
        return moduleMasses.stream().mapToInt(this::getModuleFuelRequirement).sum();
    }

    public int part2(List<Integer> moduleMasses) {
        return moduleMasses.stream().mapToInt(this::getFuelRequiredByMassAndFuel).sum();
    }

    public int getFuelRequiredByMassAndFuel(int initialMass) {
        int reducedMass = initialMass;
        int total = 0;

        while (reducedMass > 0) {
            reducedMass = Math.max(0, getModuleFuelRequirement(reducedMass));
            total += reducedMass;
        }

        return total;
    }

    public int getModuleFuelRequirement(int moduleMass) {
        return Math.round(moduleMass / 3) - 2;
    }
}
