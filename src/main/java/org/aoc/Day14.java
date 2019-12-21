package org.aoc;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aoc.util.FileUtils;

public class Day14 {
    
    private static final Pattern CHEMICAL_PATTERN = Pattern.compile("(\\d+)\\s(\\w+)");
    
    public static void main(String[] args) {
        List<String> reactionData = FileUtils.readAllLines(Day14.class, "input.txt");
        System.out.println("Part 1 = " + part1(reactionData));
        System.out.println("Part 2 = " + part2(reactionData));
    }

    public static long part1(List<String> reactionData) {
        return partX(1, getReactions(reactionData));
    }
    
    public static long part2(List<String> reactionData) {
        var reactions = getReactions(reactionData);
        long low = 1000;
        long oreSupply = 1000000000000L;
        long high = oreSupply;
        
        while (true) {
            if (high - low <= 1) {
                return low;
            }
            
            long mid = (high + low) / 2;
            long fuel = partX(mid, reactions);
            
            if (fuel < oreSupply) {
                low = mid;
            }
            else if (fuel > oreSupply) {
                high = mid;
            }
        }
    }
    
    private static Map<String, Reaction> getReactions(List<String> reactionData) {
        var reactions = new HashMap<String, Reaction>();
        
        for (String reaction : reactionData) {
            Matcher matcher = CHEMICAL_PATTERN.matcher(reaction);
            var chemicals = new ArrayList<Chemical>();
            
            while (matcher.find()) {
                chemicals.add(new Chemical(matcher.group(2), Integer.parseInt(matcher.group(1))));
            }
            
            Chemical output = chemicals.remove(chemicals.size() - 1);
            reactions.put(output.getName(), new Reaction(output, chemicals));
        }
        
        return reactions;
    }

    public static long partX(long fuelRequired, Map<String, Reaction> reactions) {
        var supply = new HashMap<String, Long>();
        var orders = new ArrayDeque<Order>();
        orders.push(new Order("FUEL", fuelRequired));
        long oreRequired = 0;
        
        while (!orders.isEmpty()) {
            Order order = orders.pop();
            
            if ("ORE".equals(order.getName())) {
                oreRequired += order.getQty();
            }
            else if (order.getQty() <= supply.getOrDefault(order.getName(), 0L)) {
                supply.put(order.getName(), supply.getOrDefault(order.getName(), 0L) - order.getQty());
            }
            else {
                Reaction reaction = reactions.get(order.getName());
                double qtyNeeded = order.getQty() - supply.getOrDefault(order.getName(), 0L);
                long batches = (long)Math.ceil(qtyNeeded / reaction.getOutput().getQty());
                
                for (Chemical input : reaction.getInputs()) {
                    orders.push(new Order(input.getName(), input.getQty() * batches));
                }
                
                long leftover = (long) (batches * reaction.getOutput().getQty() - qtyNeeded);
                supply.put(reaction.getOutput().getName(), leftover);
            }
        }
        
        return oreRequired;
    }
    
    static class Order {
        private String name;
        private long qty;
        
        public Order(String name, long qty) {
            this.name = name;
            this.qty = qty;
        }
        
        public String getName() {
            return name;
        }
        
        public long getQty() {
            return qty;
        }
    }

    static class Chemical {
        private String name;
        private int qty;

        public Chemical(String name, int qty) {
            this.name = name;
            this.qty = qty;
        }

        public int getQty() {
            return qty;
        }
        
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Chemical [name=" + name + ", qty=" + qty + "]";
        }
    }

    static class Reaction {
        private Chemical output;
        private List<Chemical> inputs;
        
        public Reaction(Chemical output, List<Chemical> inputs) {
            this.output = output;
            this.inputs = inputs;
        }
        
        public List<Chemical> getInputs() {
            return inputs;
        }
        
        public Chemical getOutput() {
            return output;
        }

        @Override
        public String toString() {
            return "Reaction [output=" + output + ", inputs=" + inputs + "]";
        }
    }
}
