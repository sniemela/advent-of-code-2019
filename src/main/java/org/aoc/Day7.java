package org.aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class Day7 {

    public static void main(String[] args) throws IOException, URISyntaxException {
        String fileContent = Files.readString(Path.of(Day5.class.getResource("day7/input.txt").toURI()));
        int[] code = Arrays.stream(fileContent.split("\\,")).mapToInt(Integer::parseInt).toArray();
        
        System.out.println("Part 1 = " + part1(code));
        System.out.println("Part 2 = " + part2(code));
    }
    
    public static int part1(int[] code) {
        int[] permutations = getPermutations(5, new int[] { 0,1,2,3,4 });
        
        int maxSignal = Integer.MIN_VALUE;
        
        int i = 0;
        while (i < permutations.length) {
            int outputSignal = 0;
            
            for (int j = 0; j < 5; j++) {
                var inputs = new ArrayDeque<Integer>(2);
                inputs.add(permutations[i + j]);
                inputs.add(outputSignal);
                outputSignal = Day5.interp(inputs, Arrays.copyOf(code, code.length));
            }
            
            if (outputSignal > maxSignal) {
                maxSignal = outputSignal;
            }
            
            i += 5;
        }
        
        return maxSignal;
    }
    
    public static int part2(int[] code) {
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
    
    public static int runPhase(int[] phaseSettings, int[] code) {
        var computers = new int[5][];
        var computerStates = new int[5];
        
        for (int i = 0; i < 5; i++) {
            computers[i] = Arrays.copyOf(code, code.length);
        }
        
        int signal = 0;
        int loop = 0;
        int state = 0;
        
        while (state != InterpResult.STATE_HALT) {
            for (int i = 0; i < 5; i++) {
                var inputs = new ArrayDeque<Integer>();
                if (loop == 0) {
                    inputs.add(phaseSettings[i]);
                }
                inputs.add(signal);
                
                var result = interp(inputs, computerStates[i], computers[i]);
                state = result.getState();
                
                if (state != InterpResult.STATE_HALT) {
                    signal = result.getResult();
                    computerStates[i] = result.getIndex();
                }
            }
            
            loop++;
        }
        
        return signal;
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
    
    public static InterpResult interp(Deque<Integer> inputs, int startIndex, int[] code) {
        int ip = startIndex;
        
        Deque<Integer> output = new ArrayDeque<Integer>();

        while (ip < code.length) {
            int op = code[ip];
            int m1 = 0;
            int m2 = 0;
            
            if (op > 1000) {
                m2 = 1;
                if (op - 1000 > 100) {
                    m1 = 1;
                }
            } else if (op > 100) {
                m1 = 1;
            }
            
            if (op > 100) {
                op = op % 10;
            }

            switch (op) {
            case 1:
            case 2: {
                int a = getValue(code, ip + 1, m1);
                int b = getValue(code, ip + 2, m2);
                code[code[ip + 3]] = op == 1 ? a + b : a * b;
                ip += 4;
                break;
            }
            case 3: {
                if (!inputs.isEmpty()) {
                    code[code[ip + 1]] = inputs.poll();
                    ip += 2;
                } else {
                    return new InterpResult(InterpResult.STATE_OUTPUT, ip, output.pop());
                }
                break;
            }
            case 4: {
                output.push(getValue(code, ip + 1, m1));
                ip += 2;
                return new InterpResult(InterpResult.STATE_OUTPUT, ip, output.pop());
            }
            case 5: {
                int a = getValue(code, ip + 1, m1);
                if (a != 0) {
                    ip = getValue(code, ip + 2, m2);
                } else {
                    ip += 3;
                }
                break;
            }
            case 6: {
                int a = getValue(code, ip + 1, m1);
                if (a == 0) {
                    ip = getValue(code, ip + 2, m2);
                } else {
                    ip += 3;
                }
                break;
            }
            case 7: {
                int a = getValue(code, ip + 1, m1);
                int b = getValue(code, ip + 2, m2);
                code[code[ip+3]] = a < b ? 1 : 0;
                ip += 4;
                break;
            }
            case 8: {
                int a = getValue(code, ip + 1, m1);
                int b = getValue(code, ip + 2, m2);
                code[code[ip+3]] = a == b ? 1 : 0;
                ip += 4;
                break;
            }
            case 99:
                return new InterpResult(InterpResult.STATE_HALT, ip, -1);
            }
        }

        return new InterpResult(InterpResult.STATE_OUTPUT, ip, output.pop());
    }
    
    private static int getValue(int[] code, int index, int mode) {
        if (mode == 1) {
            return code[index];
        }
        return code[code[index]];
    }
    
    public static final class InterpResult {
        public static final int STATE_OUTPUT = 1;
        public static final int STATE_HALT = 2;
        
        private final int state;
        private final int result;
        private final int index;
        
        public InterpResult(int state, int index, int result) {
            this.state = state;
            this.result = result;
            this.index = index;
        }
        
        public int getIndex() {
            return index;
        }
        
        public int getState() {
            return state;
        }
        
        public int getResult() {
            return result;
        }
    }
}
