package com.timdrivendev.aoc2021;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Day12 {

    int part1() throws IOException {
        BufferedReader reader = getInput("InputDay12");
        Map<String, Set<String>> connectedCaves = new HashMap<>();
        for (String line; (line = reader.readLine()) != null;) {
            String[] caveConnection = line.split("-");
            Set<String> currentConnections = connectedCaves.getOrDefault(caveConnection[0], new HashSet<>());
            currentConnections.add(caveConnection[1]);
            connectedCaves.put(caveConnection[0], currentConnections);
            currentConnections = connectedCaves.getOrDefault(caveConnection[1], new HashSet<>());
            currentConnections.add(caveConnection[0]);
            connectedCaves.put(caveConnection[1], currentConnections);
        }
        Map<String, Integer> caveVisits = new HashMap<>();
        Set<List<String>> routes = new HashSet<>();
        findRoutes("start", connectedCaves, caveVisits, new ArrayList<>(), false, routes);
        return routes.size();
    }

    private void findRoutes(String cave, Map<String, Set<String>> connectedCaves, Map<String, Integer> caveVisits, ArrayList<String> path, boolean allowedExtraSmallCaveVisit, Set<List<String>> routes) {
        path.add(cave);
        if ("end".equals(cave)) {
            routes.add(path);
            return;
        }
        if ("start".equals(cave) && caveVisits.getOrDefault(cave, 0) > 0) {
            return;
        }
        if (cave.equals(cave.toLowerCase())
                && (!allowedExtraSmallCaveVisit && caveVisits.getOrDefault(cave, 0) > 0
                || allowedExtraSmallCaveVisit && caveVisits.getOrDefault(cave, 0) > 1)) {
            return;
        }
        caveVisits.put(cave, caveVisits.getOrDefault(cave, 0) + 1);
        if (cave.equals(cave.toLowerCase()) && caveVisits.getOrDefault(cave, 0) > 1) {
            allowedExtraSmallCaveVisit = false;
        }
        Set<String> nextCaves = connectedCaves.get(cave);
        for (String nextCave : nextCaves) {
            findRoutes(nextCave, connectedCaves, new HashMap<>(caveVisits), new ArrayList<>(path), allowedExtraSmallCaveVisit, routes);
        }
    }

    int part2() throws IOException {
        BufferedReader reader = getInput("InputDay12");
        Map<String, Set<String>> connectedCaves = new HashMap<>();
        for (String line; (line = reader.readLine()) != null;) {
            String[] caveConnection = line.split("-");
            Set<String> currentConnections = connectedCaves.getOrDefault(caveConnection[0], new HashSet<>());
            currentConnections.add(caveConnection[1]);
            connectedCaves.put(caveConnection[0], currentConnections);
            currentConnections = connectedCaves.getOrDefault(caveConnection[1], new HashSet<>());
            currentConnections.add(caveConnection[0]);
            connectedCaves.put(caveConnection[1], currentConnections);
        }
        Map<String, Integer> caveVisits = new HashMap<>();
        Set<List<String>> routes = new HashSet<>();
        findRoutes("start", connectedCaves, caveVisits, new ArrayList<>(), true, routes);
        return routes.size();
    }

    private BufferedReader getInput(String inputFile) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream(inputFile);
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        return new BufferedReader(isr);
    }

    public static void main(String[] args) throws IOException {
        Day12 solution = new Day12();
        System.out.println(solution.part1());
        System.out.println(solution.part2());
    }
}
