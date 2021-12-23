package com.timdrivendev.aoc2021;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Day15 {

    Long part1() throws IOException {
        BufferedReader reader = getInput("InputDay15");
        Map<Node, Long> grid = new HashMap<>();
        final Long INF = Long.MAX_VALUE;
        int y = 0;
        for (String line; (line = reader.readLine()) != null;) {
            for (int x = 0; x < line.length(); x++) {
                grid.put(new Node(x, y), Long.parseLong(line.substring(x, x + 1)));
            }
            y++;
        }
        Map<Node, Map<Node, Long>> graph = new HashMap<>();
        for (Node node : grid.keySet()) {
            Map<Node, Long> connected = new HashMap<>();
            Node left = new Node(node.x, node.y - 1);
            Node right = new Node(node.x, node.y + 1);
            Node above = new Node(node.x - 1, node.y);
            Node below = new Node(node.x + 1, node.y);
            if (grid.containsKey(left)) {
                connected.put(left, grid.get(left));
            }
            if (grid.containsKey(right)) {
                connected.put(right, grid.get(right));
            }
            if (grid.containsKey(above)) {
                connected.put(above, grid.get(above));
            }
            if (grid.containsKey(below)) {
                connected.put(below, grid.get(below));
            }
            graph.put(node, connected);
        }
        Map<Node, CostAndPrev> unvisited = new HashMap<>();
        Map<Node, CostAndPrev> visited = new HashMap<>();
        for (Node node : graph.keySet()) {
            unvisited.put(node, new CostAndPrev(INF, null));
        }
        unvisited.put(new Node(0, 0), new CostAndPrev(0L, null));
        while (unvisited.size() > 0) {
            Node currentNode = getLowestCost(unvisited);
            for (Node neighbour : graph.get(currentNode).keySet()) {
                if (!visited.containsKey(neighbour)) {
                    Long cost = unvisited.get(currentNode).cost + graph.get(currentNode).get(neighbour);
                    if (cost < unvisited.get(neighbour).cost) {
                        unvisited.put(neighbour, new CostAndPrev(cost, currentNode));
                    }
                }
            }
            visited.put(currentNode, unvisited.get(currentNode));
            unvisited.remove(currentNode);
        }
        return visited.get(new Node(y - 1, y - 1)).cost;
    }

    private Node getLowestCost(Map<Node, CostAndPrev> unvisited) {
        Long lowestCost = Long.MAX_VALUE;
        Node nextNode = null;
        for (Node node : unvisited.keySet()) {
            Long nodeCost = unvisited.get(node).cost;
            if (nodeCost <= lowestCost) {
                nextNode = node;
                lowestCost = nodeCost;
            }
        }
        return nextNode;
    }

    Long part2() throws IOException {
        BufferedReader reader = getInput("InputDay15");
        Map<Node, Long> grid = new HashMap<>();
        final Long INF = Long.MAX_VALUE;
        int y = 0;
        for (String line; (line = reader.readLine()) != null;) {
            for (int x = 0; x < line.length(); x++) {
                grid.put(new Node(x, y), Long.parseLong(line.substring(x, x + 1)));
            }
            y++;
        }
        grid = expandBy5(grid, y);
        y *= 5;
        Map<Node, Map<Node, Long>> graph = new HashMap<>();
        for (Node node : grid.keySet()) {
            Map<Node, Long> connected = new HashMap<>();
            Node left = new Node(node.x, node.y - 1);
            Node right = new Node(node.x, node.y + 1);
            Node above = new Node(node.x - 1, node.y);
            Node below = new Node(node.x + 1, node.y);
            if (grid.containsKey(left)) {
                connected.put(left, grid.get(left));
            }
            if (grid.containsKey(right)) {
                connected.put(right, grid.get(right));
            }
            if (grid.containsKey(above)) {
                connected.put(above, grid.get(above));
            }
            if (grid.containsKey(below)) {
                connected.put(below, grid.get(below));
            }
            graph.put(node, connected);
        }
        Map<Node, CostAndPrev> unvisited = new HashMap<>();
        Map<Node, CostAndPrev> visited = new HashMap<>();
        for (Node node : graph.keySet()) {
            unvisited.put(node, new CostAndPrev(INF, null));
        }
        unvisited.put(new Node(0, 0), new CostAndPrev(0L, null));
        while (unvisited.size() > 0) {
            Node currentNode = getLowestCost(unvisited);
            for (Node neighbour : graph.get(currentNode).keySet()) {
                if (!visited.containsKey(neighbour)) {
                    Long cost = unvisited.get(currentNode).cost + graph.get(currentNode).get(neighbour);
                    if (cost < unvisited.get(neighbour).cost) {
                        unvisited.put(neighbour, new CostAndPrev(cost, currentNode));
                    }
                }
            }
            visited.put(currentNode, unvisited.get(currentNode));
            unvisited.remove(currentNode);
        }
        return visited.get(new Node(y - 1, y - 1)).cost;
    }

    private Map<Node, Long> expandBy5(Map<Node, Long> grid, int dim) {
        Map<Node, Long> newGrid = new HashMap<>();
        for (int dx = 0; dx < 5; dx++) {
            for (int x = 0; x < dim; x++) {
                for (int y = 0; y < dim; y++) {
                    long newVal = grid.get(new Node(x, y)) + dx;
                    if (newVal > 9) {
                        newVal -= 9;
                    }
                    newGrid.put(new Node(x + dim * dx, y), newVal);
                }
            }
        }
        for (int dy = 0; dy < 5; dy++) {
            for (int x = 0; x < dim * 5; x++) {
                for (int y = 0; y < dim; y++) {
                    long newVal = newGrid.get(new Node(x, y)) + dy;
                    if (newVal > 9) {
                        newVal -= 9;
                    }
                    newGrid.put(new Node(x, y + dim * dy), newVal);
                }
            }
        }
        return newGrid;
    }

    private class Node {
        private int x;
        private int y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return x == node.x && y == node.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ')';
        }
    }

    private BufferedReader getInput(String inputFile) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream(inputFile);
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        return new BufferedReader(isr);
    }

    public static void main(String[] args) throws IOException {
        Day15 solution = new Day15();
        System.out.println(solution.part1());
        System.out.println(solution.part2());
    }

    private class CostAndPrev {
        private final Long cost;
        private final Node prev;

        public CostAndPrev(Long cost, Node prev) {
            this.cost = cost;
            this.prev = prev;
        }

        @Override
        public String toString() {
            return "[" + cost + " " + prev + "]";
        }
    }
}
