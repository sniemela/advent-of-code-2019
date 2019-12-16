package org.aoc;

public class Day04 {

    public static void main(String[] args) {
        System.out.println("Part 1 = " + part1());
        System.out.println("Part 2 = " + part2());
    }
    
    public static int part1() {
        int validPasswords = 0;
        
        for (int i = 284639; i <= 748759; i++) {
            if (isValidPasswordPart1(String.valueOf(i))) {
                validPasswords++;
            }
        }
        
        return validPasswords;
    }
    
    public static int part2() {
        int validPasswords = 0;
        
        for (int i = 284639; i <= 748759; i++) {
            if (isValidPasswordPart2(String.valueOf(i))) {
                validPasswords++;
            }
        }
        
        return validPasswords;
    }

    public static boolean isValidPasswordPart2(String password) {
        int adjacent = 0;
        boolean hasDouble = false;
        
        for (int i = 0; i + 1 < 6; i++) {
            int a = password.charAt(i);
            int b = password.charAt(i + 1);
            
            if (a > b) {
                return false;
            }
            
            if (a == b) {
                adjacent++;
            } else {
                if (adjacent == 1) {
                    hasDouble = true;
                }
                adjacent = 0;
            }
        }
        
        return hasDouble || adjacent == 1;
    }
    
    public static boolean isValidPasswordPart1(String password) {
        boolean hasDouble = false;
        
        for (int i = 0; i + 1 < 6; i++) {
            int a = password.charAt(i);
            int b = password.charAt(i + 1);
            
            if (a > b) {
                return false;
            }
            
            if (a == b) {
                hasDouble = true;
            }
        }
        
        return hasDouble;
    }
}
