package org.aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class Day6Test {
    
    @Test
    public void testPar1() {
        assertEquals(42, Day6.part1(getMap("part1.txt")));
    }
    
    @Test
    public void testPart2() {
        assertEquals(4, Day6.part2(getMap("part2.txt")));
    }

    private List<String> getMap(String file) {
        try {
            return Files.readAllLines(Path.of(Day6Test.class.getResource("day6/" + file).toURI()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
