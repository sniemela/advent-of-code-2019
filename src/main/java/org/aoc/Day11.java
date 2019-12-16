package org.aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Day11 {

    public static void main(String[] args) throws IOException, URISyntaxException {
        String content = Files.readString(Path.of(Day11.class.getResource("day11/input.txt").toURI()));
        long[] program = Arrays.stream(content.split("\\,")).mapToLong(Long::parseLong).toArray();
        
        System.out.println("Part 1 = " + part1(program));
        System.out.println("part 2 = \n" + part2(program));
    }
    
    private static int part1(long[] program) {
        return partX(0, program).panelsPainted;
    }
    
    private static String part2(long[] program) {
        Result result = partX(1, program);
        
        int width = result.maxX - result.minX + 1;
        int height = result.maxY - result.minY + 1;
        int[][] decodedMap = new int[height][width];
        
        for (Entry<Point, Integer> entry : result.map.entrySet()) {
            var point = entry.getKey();
            decodedMap[point.getY()][point.getX()] = entry.getValue();
        }
        
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                sb.append(decodedMap[y][x] == 0 ? " " : "#");
            }
            
            sb.append("\n");
        }
        
        return sb.toString();
    }
    
    private static Result partX(int input, long[] program) {
        Map<Point, Integer> map = new HashMap<>();
        Robot robot = new Robot();
        Day05.IntCodeComputer computer = new Day05.IntCodeComputer(program);
        
        int exitCode = 0;
        List<Integer> instructions = new ArrayList<>();
        Result result = new Result();
        result.map = map;
        
        computer.addInput(map.getOrDefault(robot.getPosition(), input));
        
        while (exitCode != 99) {
            exitCode = computer.run();
            instructions.add((int)computer.getOutput());
            
            if (instructions.size() == 2) {
                if (map.put(robot.getPosition(), instructions.get(0)) == null) {
                    result.panelsPainted++;
                }
                
                int direction = instructions.get(1);
                
                if (direction == 0) {
                    robot.turnLeft();
                }
                else if (direction == 1) {
                    robot.turnRight();
                }
                
                robot.move();
                
                Point position = robot.getPosition();
                result.minX = Math.min(position.getX(), result.minX);
                result.maxX = Math.max(position.getX(), result.maxX);
                result.minY = Math.min(position.getY(), result.minY);
                result.maxY = Math.max(position.getY(), result.maxY);
                
                computer.addInput(map.getOrDefault(position, 0));
                
                instructions.clear();
            }
        }
        
        return result;
    }
    
    static class Result {
        Map<Point, Integer> map;
        int minX = 0;
        int maxX = 0;
        int minY = 0;
        int maxY = 0;
        int panelsPainted = 0;
    }
    
    static class Robot {
        private int x;
        private int y;
        private int dx;
        private int dy;
        
        public Robot() {
            this.x = 0;
            this.y = 0;
            this.dx = 0;
            this.dy = -1;
        }
        
        public void turnLeft() {
            int temp = dx;
            dx = dy;
            dy = temp * -1;
        }
        
        public void turnRight() {
            int temp = dx;
            dx = dy * -1;
            dy = temp;
        }
        
        public void move() {
            x += dx;
            y += dy;
        }
        
        public Point getPosition() {
            return new Point(x, y);
        }
    }
    
    static class Point {
        private int x;
        private int y;
        
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public int getY() {
            return y;
        }
        
        public int getX() {
            return x;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + x;
            result = prime * result + y;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Point other = (Point) obj;
            if (x != other.x)
                return false;
            if (y != other.y)
                return false;
            return true;
        }
    }
}
