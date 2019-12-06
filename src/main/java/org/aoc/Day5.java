package org.aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Day5 {

    public static void main(String[] args) throws IOException, URISyntaxException {
        String fileContent = Files.readString(Path.of(Day5.class.getResource("day5/input.txt").toURI()));
        int[] code = Arrays.stream(fileContent.split("\\,")).mapToInt(Integer::parseInt).toArray();
        
        System.out.println("Part 1 = " + interp(1, Arrays.copyOf(code, code.length)));
        System.out.println("Part 2 = " + interp(5, Arrays.copyOf(code, code.length)));
    }

    public static int interp(int noun, int[] code) {
        int ip = 0;
        int output = 0;

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
                code[code[ip + 1]] = noun;
                ip += 2;
                break;
            }
            case 4: {
                output = getValue(code, ip + 1, m1);
                System.out.println(output);
                ip += 2;
                break;
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
                return output;
            }
        }

        return output;
    }

    private static int getValue(int[] code, int index, int mode) {
        if (mode == 1) {
            return code[index];
        }
        return code[code[index]];
    }
}
