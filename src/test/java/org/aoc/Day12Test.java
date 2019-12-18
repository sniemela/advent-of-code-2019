package org.aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.aoc.Day12.Moon;
import org.junit.jupiter.api.Test;

public class Day12Test {

    @Test
    public void part1() throws IOException, URISyntaxException {
        assertEquals(1940, Day12.part1(getMoons(), 100));
    }
    
    @Test
    public void part2() throws IOException, URISyntaxException {
        assertEquals(4686774924L, Day12.part2(getMoons()));
    }
    
    private List<Moon> getMoons() throws IOException, URISyntaxException {
        return Files.readAllLines(Path.of(Day12Test.class.getResource("day12/test.txt").toURI())).stream()
                .map(Day12::convertToMoon)
                .collect(Collectors.toList());
    }
}
