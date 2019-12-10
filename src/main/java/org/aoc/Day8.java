package org.aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Day8 {

    public static void main(String[] args) throws IOException, URISyntaxException {
        String imageData = Files.readString(Path.of(Day8.class.getResource("day8/input.txt").toURI()));
        
        System.out.println("Part 1 = " + part1(25, 6, imageData));
        System.out.println("Part 2 = \n" + part2(25, 6, imageData));
    }

    private static String part2(int width, int height, String imageData) {
        int step = width*height;
        int[] decodedImage = new int[step];
        Arrays.fill(decodedImage, 2);
        
        for (int i = 0; i + step <= imageData.length(); i += step) {
            String layer = imageData.substring(i, i + step);
            
            for (int j = 0; j < layer.length(); j++) {
                int pixel = Character.getNumericValue(layer.charAt(j));
                int decodedPixel = decodedImage[j];
                
                if (pixel == 0 && decodedPixel != 1) {
                    decodedImage[j] = 0;
                }
                
                if (pixel == 1 && decodedPixel != 0) {
                    decodedImage[j] = 1;
                }
            }
        }
        
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < decodedImage.length; i++) {
            sb.append(decodedImage[i] == 1 ? "#" : " ");
            
            if ((i + 1) % width == 0) {
                sb.append("\n");
            }
        }
        
        return sb.toString();
    }

    private static int part1(int width, int height, String imageData) {
        int step = width * height;
        
        int minZeros = Integer.MAX_VALUE;
        String bestLayer = null;
        
        for (int i = 0; i + step < imageData.length(); i += step) {
            String layer = imageData.substring(i, i + step);
            int zeros = (int) layer.chars().map(Character::getNumericValue).filter(n -> n == 0).count();
            
            if (zeros < minZeros) {
                minZeros = zeros;
                bestLayer = layer;
            }
        }
        
        int ones = (int) bestLayer.chars().map(Character::getNumericValue).filter(n -> n == 1).count();
        int twos = (int) bestLayer.chars().map(Character::getNumericValue).filter(n -> n == 2).count();
        
        return ones * twos;
    }
}
