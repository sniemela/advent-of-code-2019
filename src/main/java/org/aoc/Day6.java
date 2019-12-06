package org.aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day6 {

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> map = Files.readAllLines(Path.of(Day6.class.getResource("day6/input.txt").toURI()));
        
        System.out.println("Part 1 = " + part1(map));
        System.out.println("Part 2 = " + part2(map));
    }
    
    public static int part1(List<String> map) {
        return createNodes(map).values().stream()
                .mapToInt(Node::getDepth)
                .sum();
    }
    
    public static int part2(List<String> map) {
        var nodes = createNodes(map);
        
        var youPath = getPath(nodes.get("YOU"));
        var sanPath = getPath(nodes.get("SAN"));
        
        int firstIntersection = getIntersection(youPath, sanPath);
        
        return youPath.size() - firstIntersection + sanPath.size() - firstIntersection - 2;
    }
    
    private static Map<String, Node> createNodes(List<String> map) {
        var nodes = new HashMap<String, Node>();
        
        for (String connection : map) {
            var objects = connection.split("\\)");
            
            Node parent = nodes.computeIfAbsent(objects[0], Node::new);
            Node child = nodes.computeIfAbsent(objects[1], Node::new);
            
            parent.addChild(child);
        }
        
        return nodes;
    }
    
    private static int getIntersection(List<String> youPath, List<String> sanPath) {
        int intersection = 0;
        
        for (int i = 0; i < youPath.size() && i < sanPath.size(); i++) {
            if (!youPath.get(i).equals(sanPath.get(i))) {
                break;
            } else {
                intersection = i;
            }
        }
        
        return intersection;
    }

    private static List<String> getPath(Node node) {
        var path = new ArrayList<String>();
        var parent = node.getParent();
        
        while (parent != null) {
            path.add(parent.getCode());
            parent = parent.getParent();
        }
        
        Collections.reverse(path);
        
        return path;
    }

    private static class Node {
        private String code;
        private Node parent;
        private List<Node> children;
        
        public Node(String code) {
            this.code = code;
            this.children = new ArrayList<>();
        }
        
        public String getCode() {
            return code;
        }
        
        public Node getParent() {
            return parent;
        }
        
        public void setParent(Node parent) {
            this.parent = parent;
        }
        
        public void addChild(Node node) {
            node.setParent(this);
            children.add(node);
        }
        
        public int getDepth() {
            int total = children.size();
            for (Node node : children) {
                total += node.getDepth();
            }
            return total;
        }
    }
}
