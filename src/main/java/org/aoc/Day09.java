package org.aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class Day09 {

    public static void main(String[] args) throws IOException, URISyntaxException {
        String fileContent = Files.readString(Path.of(Day09.class.getResource("day09/input.txt").toURI()));
        long[] code = Arrays.stream(fileContent.split("\\,")).mapToLong(Long::parseLong).toArray();
        
        System.out.println("Part 1 = " + part1(code));
        System.out.println("Part 2 = " + part2(code));
    }
    
    public static long part1(long[] code) {
        return run(1, Arrays.copyOf(code, code.length));
    }
    
    public static long part2(long[] code) {
        return run(2, Arrays.copyOf(code, code.length));
    }
    
    public static long run(int input, long[] code) {
        var computer = new Day05.IntCodeComputer(code);
        long output = 0;
        
        computer.addInput(input);
        
        while (computer.run() != 99) {
            output = computer.getOutput();
        }
        
        return output;
    }
    
    public static long[] run(long[] code) {
        var computer = new Day05.IntCodeComputer(code);
        var output = new ArrayList<Long>();
        
        while (computer.run() != 99) {
            output.add(computer.getOutput());
        }
        
        return output.stream().mapToLong(Long::longValue).toArray();
    }
}
