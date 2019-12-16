package org.aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Day07 {

    public static void main(String[] args) throws IOException, URISyntaxException {
        String fileContent = Files.readString(Path.of(Day05.class.getResource("day07/input.txt").toURI()));
        long[] code = Arrays.stream(fileContent.split("\\,")).mapToLong(Long::parseLong).toArray();
        
        System.out.println("Part 1 = " + part1(code));
        System.out.println("Part 2 = " + part2(code));
    }
    
    public static int part1(long[] code) {
        int[] permutations = getPermutations(5, new int[] { 0,1,2,3,4 });
        
        int maxSignal = Integer.MIN_VALUE;
        
        int i = 0;
        while (i < permutations.length) {
            int outputSignal = 0;
            
            for (int j = 0; j < 5; j++) {
                List<Integer> inputs = Arrays.asList(permutations[i + j], outputSignal);
                outputSignal = (int) Day05.interp(inputs, Arrays.copyOf(code, code.length));
            }
            
            if (outputSignal > maxSignal) {
                maxSignal = outputSignal;
            }
            
            i += 5;
        }
        
        return maxSignal;
    }
    
    public static int part2(long[] code) {
        int[] feedBackPermutations = getPermutations(5, new int[] { 5,6,7,8,9 });
        
        int maxSignal = Integer.MIN_VALUE;
        
        for (int i = 0; i < feedBackPermutations.length; i += 5) {
            int[] phaseSettings = new int[5];
            
            for (int j = 0; j < 5; j++) {
                phaseSettings[j] = feedBackPermutations[i + j];
            }
            
            int signal = runPhase(phaseSettings, code);
            
            if (signal > maxSignal) {
                maxSignal = signal;
            }
        }

        return maxSignal;
    }
    
    public static int runPhase(int[] phaseSettings, long[] code) {
        var computers = new Day05.IntCodeComputer[5];
        
        for (int i = 0; i < 5; i++) {
            var comp = new Day05.IntCodeComputer(Arrays.copyOf(code, code.length));
            comp.addInput(phaseSettings[i]);
            computers[i] = comp;
        }
        
        int signal = 0;
        int i = 0;
        
        while (true) {
            var computer = computers[i];
            computer.addInput(signal);
            
            if (computer.run() == 99) {
                return signal;
            }
            
            signal = (int) computer.getOutput();
            i = (i + 1) % 5;
        }
    }
    
    public static int[] getPermutations(int n, int[] sequence) {
        int[] indexes = new int[n];
        int[] permutations = new int[factorial(n)*n];
        int permutationsIndex = 0;
        
        for (int i = 0; i < n; i++) {
            permutations[permutationsIndex++] = sequence[i];
        }
        
        int i = 0;
        while (i < n) {
            if (indexes[i] < i) {
                swap(sequence, i % 2 == 0 ? 0 : indexes[i], i);
                
                for (int j = 0; j < n; j++) {
                    permutations[permutationsIndex++] = sequence[j];
                }
                
                indexes[i]++;
                i = 0;
            }
            else {
                indexes[i] = 0;
                i++;
            }
        }
        
        return permutations;
    }
    
    private static int factorial(int n) {
        if (n <= 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }
    
    private static void swap(int[] sequence, int a, int b) {
        int temp = sequence[a];
        sequence[a] = sequence[b];
        sequence[b] = temp;
    }
}
