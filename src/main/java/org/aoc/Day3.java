package org.aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day3 {

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> wires = Files.readAllLines(Path.of(Day3.class.getResource("day3/input.txt").toURI()));
        
        System.out.println("Part 1 = " + part1(wires.get(0), wires.get(1)));
        System.out.println("Part 2 = " + part2(wires.get(0), wires.get(1)));
    }
    
    public static int part2(String wire1, String wire2) {
        Map<Point, Integer> wire1Points = getPoints(wire1);
        Map<Point, Integer> wire2Points = getPoints(wire2);
        
        Set<Point> intersections = new HashSet<>(wire1Points.keySet());
        intersections.retainAll(wire2Points.keySet());
        
        return intersections.stream()
            .mapToInt(p -> wire1Points.get(p) + wire2Points.get(p))
            .min()
            .getAsInt();
    }

    public static int part1(String wire1, String wire2) {
        Map<Point, Integer> wire1Points = getPoints(wire1);
        Map<Point, Integer> wire2Points = getPoints(wire2);
        
        Set<Point> intersections = new HashSet<>(wire1Points.keySet());
        intersections.retainAll(wire2Points.keySet());
        
        return intersections.stream()
            .mapToInt(p -> Math.abs(p.getX()) + Math.abs(p.getY()))
            .min()
            .getAsInt();
    }

    public static Map<Point, Integer> getPoints(String wirePath) {
        int x = 0;
        int y = 0;
        int length = 0;
        Map<Point, Integer> points = new HashMap<>();

        for (String path : wirePath.split("\\,")) {
            char direction = path.charAt(0);
            int n = Integer.parseInt(path.substring(1));

            for (int i = 0; i < n; i++) {
                switch (direction) {
                case 'U':
                    y += 1;
                    break;
                case 'L':
                    x -= 1;
                    break;
                case 'D':
                    y -= 1;
                    break;
                case 'R':
                    x += 1;
                    break;
                }

                length += 1;
                Point point = new Point(x, y);
                points.putIfAbsent(point, length);
            }
        }

        return points;
    }

    static class Point {
        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
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
