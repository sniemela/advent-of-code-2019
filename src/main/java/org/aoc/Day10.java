package org.aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day10 {

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> mapLines = Files.readAllLines(Path.of(Day10.class.getResource("day10/input.txt").toURI()));
        List<Asteroid> asteroids = new ArrayList<>();
        for (int i = 0; i < mapLines.size(); i++) {
            String line = mapLines.get(i);
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == '#') {
                    asteroids.add(new Asteroid(j, i));
                }
            }
        }
        
        Asteroid best = null;
        int max = Integer.MIN_VALUE;
        
        for (Asteroid a : asteroids) {
            int visible = getVisibleAsteroids(a, asteroids);
            if (visible > max) {
                max = visible;
                best = a;
            }
        }
        
        System.out.println("Part 1 =  " + max);
        System.out.println("Part 2 = " + part2(best, asteroids));
    }
    
    private static int part2(Asteroid best, List<Asteroid> asteroids) {
        Deque<Asteroid> vaporizeQueue = new ArrayDeque<>();
        int vaporized = 0;
        
        int i = 90;
        while (!allVaporized(best, asteroids)) {
            if (i == 90) {
                Asteroid a = null;
                
                while((a = vaporizeQueue.poll()) != null) {
                    if (!a.isVaporized()) {
                        a.setVaporized(true);
                        vaporized++;
                        
                        if (vaporized == 200) {
                            return a.getX()*100+a.getY();
                        }
                    }
                }
            }
            
            vaporizeQueue.addAll(getAllAsteroids(i, i + 90, best, asteroids));
            
            if (i + 90 == 360) {
                i = 0;
            } else {
                i += 90;
            }
        }
        
        return 0;
    }
    
    private static boolean allVaporized(Asteroid origin, List<Asteroid> asteroids) {
        for (Asteroid a : asteroids) {
            if (a != origin) {
                if (!a.isVaporized()) {
                    return false;
                }
            }
        }
        return true;
    }

    private static List<Asteroid> getAllAsteroids(int fromAngle, int toAngle, Asteroid origin, List<Asteroid> asteroids) {
        Map<Double, List<Asteroid>> result = new HashMap<>();
        for (Asteroid a : asteroids) {
            double angle = origin.getAngle(a);
            if (a != origin && !a.isVaporized() && angle >= fromAngle && angle <= toAngle) {
                result.computeIfAbsent(angle, k -> new ArrayList<>()).add(a);
            }
        }
        return result.values().stream()
                .map(a -> closestAsteroidToOrigin(origin, a))
                .sorted((a, b) -> Double.compare(origin.getAngle(a), origin.getAngle(b)))
                .collect(Collectors.toList());
    }
    
    private static Asteroid closestAsteroidToOrigin(Asteroid origin, List<Asteroid> asteroids) {
        int minDistance = Integer.MAX_VALUE;
        Asteroid best = null;
        for (Asteroid a : asteroids) {
            int distance = (int) origin.getDistanceTo(a);
            if (distance < minDistance) {
                minDistance = distance;
                best = a;
            }
        }
        return best;
    }

    private static int getVisibleAsteroids(Asteroid asteroid, List<Asteroid> asteroids) {
        Set<Double> uniqueAngles = new HashSet<>();
        for (Asteroid a : asteroids) {
            if (a == asteroid) {
                continue;
            }
            uniqueAngles.add(asteroid.getAngle(a));
        }
        return uniqueAngles.size();
    }

    static class Asteroid {
        private int x;
        private int y;
        private boolean vaporized;
        
        public Asteroid(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public boolean isVaporized() {
            return vaporized;
        }
        
        public void setVaporized(boolean vaporized) {
            this.vaporized = vaporized;
        }
        
        public double getAngle(Asteroid other) {
            double dy = getY() - other.getY();
            double dx = getX() - other.getX();
            double result = Math.toDegrees(Math.atan2(dy, dx));
            return (result < 0) ? (360d + result) : result;
        }
        
        public int getY() {
            return y;
        }
        
        public int getX() {
            return x;
        }
        
        public double getDistanceTo(Asteroid other) {
            return Math.sqrt(Math.pow(other.getX() - getX(), 2) + Math.pow(other.getY() - getY(), 2));
        }

        @Override
        public String toString() {
            return "Asteroid [x=" + x + ", y=" + y + "]";
        }
    }
}
