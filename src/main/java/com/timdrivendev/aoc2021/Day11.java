package com.timdrivendev.aoc2021;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Day11 {

    int part1() throws IOException {
        BufferedReader reader = getInput("InputDay11");
        Map<Point, Integer> octopusEnergyLevels = new HashMap<>();
        int lineNo = 0;
        for (String line; (line = reader.readLine()) != null;) {
            char[] charArray = line.toCharArray();
            for (int colNo = 0; colNo < charArray.length; colNo++) {
                char c = charArray[colNo];
                octopusEnergyLevels.put(new Point(lineNo, colNo), Integer.parseInt(String.valueOf(c)));
            }
            lineNo++;
        }
        int flashCount = 0;
        for (int step = 0; step < 100; step++) {
            Set<Point> flashedOctopuses = new HashSet<>();
            Map<Point, Integer> finalOctopusEnergyLevels = octopusEnergyLevels;
            octopusEnergyLevels.replaceAll((o, v) -> finalOctopusEnergyLevels.get(o) + 1);
            boolean checkForMoreFlashes;
            do {
                Map<Point, Integer> nextOctopusEnergyLevels = octopusEnergyLevels;
                checkForMoreFlashes = false;
                for (Point octopus : octopusEnergyLevels.keySet()) {
                    if (octopusEnergyLevels.get(octopus) <= 9 || flashedOctopuses.contains(octopus)) {
                        continue;
                    }
                    checkForMoreFlashes = true;
                    flashedOctopuses.add(octopus);
                    for (int dr = -1; dr <= 1; dr++) {
                        for (int dc = -1; dc <= 1; dc++) {
                            Point adjacentOctopus = new Point(octopus.row + dr, octopus.col + dc);
                            if (nextOctopusEnergyLevels.get(adjacentOctopus) != null) {
                                nextOctopusEnergyLevels.put(adjacentOctopus, nextOctopusEnergyLevels.get(adjacentOctopus) + 1);
                            }
                        }
                    }
                }
                octopusEnergyLevels = nextOctopusEnergyLevels;
            } while (checkForMoreFlashes);
            for (Point flashedOctopus : flashedOctopuses) {
                octopusEnergyLevels.put(flashedOctopus, 0);
            }
            flashCount += flashedOctopuses.size();
        }
        return flashCount;
    }

    int part2() throws IOException {
        BufferedReader reader = getInput("InputDay11Test");
        Map<Point, Integer> octopusEnergyLevels = new HashMap<>();
        int lineNo = 0;
        for (String line; (line = reader.readLine()) != null;) {
            char[] charArray = line.toCharArray();
            for (int colNo = 0; colNo < charArray.length; colNo++) {
                char c = charArray[colNo];
                octopusEnergyLevels.put(new Point(lineNo, colNo), Integer.parseInt(String.valueOf(c)));
            }
            lineNo++;
        }
        int flashCount = 0;
        int stepCount = 0;
        boolean hasAllFlashed = false;
        do  {
            Set<Point> flashedOctopuses = new HashSet<>();
            Map<Point, Integer> finalOctopusEnergyLevels = octopusEnergyLevels;
            octopusEnergyLevels.replaceAll((o, v) -> finalOctopusEnergyLevels.get(o) + 1);
            boolean checkForMoreFlashes;
            do {
                hasAllFlashed = true;
                Map<Point, Integer> nextOctopusEnergyLevels = octopusEnergyLevels;
                checkForMoreFlashes = false;
                for (Point octopus : octopusEnergyLevels.keySet()) {
                    if (octopusEnergyLevels.get(octopus) <= 9 || flashedOctopuses.contains(octopus)) {
                        hasAllFlashed = false;
                        continue;
                    }
                    checkForMoreFlashes = true;
                    flashedOctopuses.add(octopus);
                    for (int dr = -1; dr <= 1; dr++) {
                        for (int dc = -1; dc <= 1; dc++) {
                            Point adjacentOctopus = new Point(octopus.row + dr, octopus.col + dc);
                            if (nextOctopusEnergyLevels.get(adjacentOctopus) != null) {
                                nextOctopusEnergyLevels.put(adjacentOctopus, nextOctopusEnergyLevels.get(adjacentOctopus) + 1);
                            }
                        }
                    }
                }
                octopusEnergyLevels = nextOctopusEnergyLevels;
            } while (checkForMoreFlashes);
            for (Point flashedOctopus : flashedOctopuses) {
                octopusEnergyLevels.put(flashedOctopus, 0);
            }
            flashCount += flashedOctopuses.size();
            stepCount++;
        } while (!hasAllFlashed);
        return stepCount;
    }

    private class Point {
        private int row;
        private int col;

        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return row == point.row && col == point.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }

        @Override
        public String toString() {
            return "(" + row + ", " + col + ')';
        }
    }


    private BufferedReader getInput(String inputFile) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream(inputFile);
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        return new BufferedReader(isr);
    }

    public static void main(String[] args) throws IOException {
        Day11 solution = new Day11();
        System.out.println(solution.part1());
        System.out.println(solution.part2());
    }
}
