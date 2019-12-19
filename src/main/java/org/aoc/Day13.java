package org.aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class Day13 {

    public static void main(String[] args) throws IOException, URISyntaxException {
        String fileContent = Files.readString(Path.of(Day05.class.getResource("day13/input.txt").toURI()));
        long[] program = Arrays.stream(fileContent.split("\\,")).mapToLong(Long::parseLong).toArray();
        
        System.out.println("Part 1 = " + part1(program));
        System.out.println("Part 2 = " + part2(program));
    }

    private static int part2(long[] program) {
        var arcade = new Arcade(program);
        return arcade.play();
    }

    private static int part1(long[] program) {
        var arcade = new Arcade(program);
        return arcade.getTotalBlocks();
    }
    
    static class Arcade {
        private long[] program;

        public Arcade(long[] program) {
            this.program = program;
        }
        
        public int getTotalBlocks() {
            int exitCode = 0;
            var output = new ArrayList<Integer>();
            int blocks = 0;
            
            var computer = new Day05.IntCodeComputer(program);
            
            while (exitCode != 99) {
                exitCode = computer.run();
                output.add((int)computer.getOutput());
                
                if (output.size() == 3) {
                    if (output.get(2) == 2) {
                        blocks++;
                    }
                    output.clear();
                }
            }
            
            return blocks;
        }

        public int play() {
            int exitCode = 0;
            var output = new ArrayList<Integer>();
            int score = 0;
            program[0] = 2;
            var computer = new Day05.IntCodeComputer(program);

            int padX = -1;
            int ballX = -1;
            
            while (exitCode != 99) {
                if (output.size() == 0) {
                    int paddMovement = padX != -1 && ballX != -1 ? Math.max(-1, Math.min(ballX - padX, 1)) : 0;
                    computer.setInputs(Arrays.asList(paddMovement));
                }
                
                exitCode = computer.run();
                output.add((int)computer.getOutput());

                if (output.size() == 3) {
                    int x = output.get(0);
                    int y = output.get(1);
                    int tileId = output.get(2);
                    
                    if (x == -1 && y == 0) {
                        score = tileId;
                    }
                    else if (tileId == 3) {
                        padX = x;
                    }
                    else if (tileId == 4) {
                        ballX = x;
                    }
                    
                    output.clear();
                }
            }
            
            return score;
        }
    }
}
