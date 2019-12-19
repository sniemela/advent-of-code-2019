package org.aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class Day05 {

    public static void main(String[] args) throws IOException, URISyntaxException {
        String fileContent = Files.readString(Path.of(Day05.class.getResource("day05/input.txt").toURI()));
        long[] code = Arrays.stream(fileContent.split("\\,")).mapToLong(Long::parseLong).toArray();
        
        System.out.println("Part 1 = " + interp(1, Arrays.copyOf(code, code.length)));
        System.out.println("Part 2 = " + interp(5, Arrays.copyOf(code, code.length)));
    }
    
    public static long interp(List<Integer> inputs, long[] code) {
        var computer = new IntCodeComputer(code);
        inputs.forEach(computer::addInput);
        
        int exitCode = 0;
        long output = 0;
        
        while (exitCode != 99) {
            exitCode = computer.run();
            output = computer.getOutput();
        }
        
        return output;
    }
    
    public static long interp(int input, long[] code) {
        return interp(Arrays.asList(input), code);
    }

    public static class IntCodeComputer {
        private long[] memory;
        private int ip;
        private Deque<Integer> inputs;
        private long output;
        private long relativeBase;

        public IntCodeComputer(long[] memory) {
            this.memory = memory;
            this.ip = 0;
            this.relativeBase = 0;
            this.inputs = new ArrayDeque<Integer>();
        }

        public void addInput(int input) {
            inputs.add(input);
        }
        
        public void setInputs(List<Integer> list) {
            inputs.clear();
            inputs.addAll(list);
        }

        public long getOutput() {
            return output;
        }

        public int run() {
            while (ip < memory.length) {
                int op = (int) memory[ip];
                int m1 = 0;
                int m2 = 0;
                int m3 = 0;

                if (op > 20000) {
                    m3 = 2;
                    op = op - 20000;
                } else if (op > 10000) {
                    m3 = 1;
                    op = op - 10000;
                }

                if (op > 2000) {
                    m2 = 2;
                    if (op - 2000 > 200) {
                        m1 = 2;
                    } else if (op - 2000 > 100) {
                        m1 = 1;
                    }
                } else if (op > 1000) {
                    m2 = 1;
                    if (op - 1000 > 200) {
                        m1 = 2;
                    } else if (op - 1000 > 100) {
                        m1 = 1;
                    }
                } else if (op > 200) {
                    m1 = 2;
                } else if (op > 100) {
                    m1 = 1;
                }

                if (op > 100) {
                    op = op % 10;
                }

                switch (op) {
                case 1:
                case 2: {
                    long a = getValue(ip + 1, m1);
                    long b = getValue(ip + 2, m2);
                    int output = getOutputPosition(ip + 3, m3);
                    memory[output] = op == 1 ? a + b : a * b;
                    ip += 4;
                    break;
                }
                case 3: {
                    if (!inputs.isEmpty()) {
                        int outputPosition = getOutputPosition(ip + 1, m1);
                        memory[outputPosition] = inputs.poll();
                        ip += 2;
                    } else {
                        return -1;
                    }
                    break;
                }
                case 4: {
                    output = getValue(ip + 1, m1);
                    ip += 2;
                    return -1;
                }
                case 5: {
                    long a = getValue(ip + 1, m1);
                    if (a != 0) {
                        ip = (int) getValue(ip + 2, m2);
                    } else {
                        ip += 3;
                    }
                    break;
                }
                case 6: {
                    long a = getValue(ip + 1, m1);
                    if (a == 0) {
                        ip = (int) getValue(ip + 2, m2);
                    } else {
                        ip += 3;
                    }
                    break;
                }
                case 7: {
                    long a = getValue(ip + 1, m1);
                    long b = getValue(ip + 2, m2);
                    int outputPosition = getOutputPosition(ip + 3, m3);
                    memory[outputPosition] = a < b ? 1 : 0;
                    ip += 4;
                    break;
                }
                case 8: {
                    long a = getValue(ip + 1, m1);
                    long b = getValue(ip + 2, m2);
                    int outputPosition = getOutputPosition(ip + 3, m3);
                    memory[outputPosition] = a == b ? 1 : 0;
                    ip += 4;
                    break;
                }
                case 9: {
                    relativeBase += getValue(ip + 1, m1);
                    ip += 2;
                    break;
                }
                case 99:
                    return 99;
                }
            }

            return -1;
        }

        private long getValue(int index, int mode) {
            int position = index;

            if (mode == 0) {
                position = (int) memory[index];
                growMemory(position);
            } else if (mode == 2) {
                position = (int) (relativeBase + memory[index]);
                growMemory(position);
            }

            return memory[position];
        }
        
        private int getOutputPosition(int index, int mode) {
            int position = (int)memory[index];
            
            if (mode == 2) {
                position = (int)relativeBase + position;
            }
            
            growMemory(position);
            
            return position;
        }

        private void growMemory(int index) {
            if (index >= memory.length) {
                int diff = index - memory.length;
                long[] copy = new long[memory.length + diff + 1];
                System.arraycopy(memory, 0, copy, 0, memory.length);
                memory = copy;
            }
        }
    }
}
