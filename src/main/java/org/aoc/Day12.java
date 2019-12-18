package org.aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day12 {

    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("Part 1 = " + part1(getMoons(), 1000));
        System.out.println("Part 2 = " + part2(getMoons()));
    }
    
    public static int part1(List<Moon> moons, int steps) {
        for (int i = 0; i < steps; i++) {
            simulateMoons(moons);
        }
        
        return moons.stream().mapToInt(Moon::getTotalEnergy).sum();
    }
    
    private static List<Moon> getMoons() throws IOException, URISyntaxException {
        return Files.readAllLines(Path.of(Day12.class.getResource("day12/input.txt").toURI())).stream()
                .map(Day12::convertToMoon)
                .collect(Collectors.toList());
    }

    private static void simulateMoons(List<Moon> moons) {
        for (Moon moon : moons) {
            for (Moon other : moons) {
                moon.addVelocity(other);
            }
        }
        
        for (Moon moon : moons) {
            moon.applyVelocity();
        }
    }

    public static long part2(List<Moon> moons) {
        int[] stepsWhenRepeat = new int[3];
        
        for (int i = 0; i < 3; i++) {
            var seen = new HashSet<String>();
            
            while (true) {
                final int index = i;
                
                String hash = moons.stream()
                        .map(m -> Arrays.asList(m.getPosition(index), m.getVelocity(index)))
                        .flatMap(List::stream)
                        .map(String::valueOf)
                        .collect(Collectors.joining(","));
                
                if (seen.contains(hash)) {
                    break;
                }
                
                seen.add(hash);
                simulateMoons(moons);
            }
            
            stepsWhenRepeat[i] = seen.size();
        }
        
        return getLcm(stepsWhenRepeat[0], getLcm(stepsWhenRepeat[1], stepsWhenRepeat[2]));
    }
    
    private static long getLcm(long x, long y) {
        long a = x;
        long b = y;
        while (a > 0) {
            long temp = a;
            a = b % a;
            b = temp;
        }
        return x / b * y;
    }

    public static Moon convertToMoon(String spec) {
        Pattern compile = Pattern.compile("(?:x|y|z)\\=(-?\\d+)");
        Matcher matcher = compile.matcher(spec);
        matcher.find();
        int x = Integer.parseInt(matcher.group(1));
        matcher.find();
        int y = Integer.parseInt(matcher.group(1));
        matcher.find();
        int z = Integer.parseInt(matcher.group(1));
        return new Moon(x, y, z);
    }

    static class Moon {
        private int[] position;
        private int[] velocity;

        public Moon(int x, int y, int z) {
            position = new int[] { x, y, z };
            velocity = new int[3];
        }

        public void addVelocity(Moon other) {
            for (int i = 0; i < 3; i++) {
                if (position[i] > other.getPosition(i)) {
                    velocity[i] -= 1;
                }
                else if (position[i] < other.getPosition(i)) {
                    velocity[i] += 1;
                }
            }
        }

        public void applyVelocity() {
            for (int i = 0; i < 3; i++) {
                position[i] += velocity[i];
            }
        }
        
        public int getPosition(int index) {
            return position[index];
        }
        
        public int getVelocity(int index) {
            return velocity[index];
        }
        
        public int getTotalEnergy() {
            return Arrays.stream(position).map(Math::abs).sum() * Arrays.stream(velocity).map(Math::abs).sum();
        }
    }
}
