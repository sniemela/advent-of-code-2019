package org.aoc;

import java.util.Arrays;

public class Day02 {
    
    public static void main(String[] args) {
        int[] code = new int[] { 1, 0, 0, 3, 1, 1, 2, 3, 1, 3, 4, 3, 1, 5, 0, 3, 2, 1, 9, 19, 1, 19, 5, 23, 2, 6, 23,
                27, 1, 6, 27, 31, 2, 31, 9, 35, 1, 35, 6, 39, 1, 10, 39, 43, 2, 9, 43, 47, 1, 5, 47, 51, 2, 51, 6, 55,
                1, 5, 55, 59, 2, 13, 59, 63, 1, 63, 5, 67, 2, 67, 13, 71, 1, 71, 9, 75, 1, 75, 6, 79, 2, 79, 6, 83, 1,
                83, 5, 87, 2, 87, 9, 91, 2, 9, 91, 95, 1, 5, 95, 99, 2, 99, 13, 103, 1, 103, 5, 107, 1, 2, 107, 111, 1,
                111, 5, 0, 99, 2, 14, 0, 0 };
        part1(code);
        part2(code);
    }
    
    public static void part1(int[] code) {
        System.out.println("Part 1 = " + interp(12, 2, Arrays.copyOf(code, code.length))[0]);
    }
    
    public static void part2(int[] code) {
        int[] program = new int[code.length];
        
        for (int noun = 0; noun < 100; noun++) {
            for (int verb = 0; verb < 100; verb++) {
                System.arraycopy(code, 0, program, 0, code.length);
                interp(noun, verb, program);
                
                if (program[0] == 19690720) {
                    System.out.println("Part 2 = " + (100 * noun + verb));
                    return;
                }
            }
        }
    }
    
    public static int[] interp(int[] code) {
        return interp(code[1], code[2], code);
    }
    
    public static int[] interp(int noun, int verb, int[] code) {
        int ip = 0;
        code[1] = noun;
        code[2] = verb;
        
        while (ip < code.length) {
            int op = code[ip];
            
            switch (op) {
            case 1:
            case 2:
                int outputPosition = code[ip+3];
                int a = code[code[ip+1]];
                int b = code[code[ip+2]];
                code[outputPosition] = op == 1 ? a + b : a * b;
                ip += 4;
                break;
            case 99:
                return code;
            }
        }
        
        return code;
    }
}
