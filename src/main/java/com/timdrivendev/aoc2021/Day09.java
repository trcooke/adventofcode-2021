package com.timdrivendev.aoc2021;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Day09 {

    int part1() throws IOException {
        BufferedReader reader = getInput("InputDay09");
        Map<Point, Integer> heightMap = new HashMap<>();
        int rowCount = 0;
        int colCount = 0;
        for (String line; (line = reader.readLine()) != null;) {
            colCount = line.length();
            for (int i = 0; i < line.length(); i++) {
                heightMap.put(new Point(rowCount, i), Integer.parseInt(line.substring(i, i + 1)));
            }
            rowCount++;
        }
        List<Integer> lowPoints = new ArrayList<>();
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {
                Point thisLocation = new Point(row, col);
                List<Point> adjacentLocations = adjacentLocationsOf(new Point(row, col));
                boolean isLowPoint = true;
                for (Point adjacentLocation : adjacentLocations) {
                    if (heightMap.getOrDefault(thisLocation, 9) >= heightMap.getOrDefault(adjacentLocation, 9)) {
                        isLowPoint = false;
                    }
                }
                if (isLowPoint) {
                    lowPoints.add(heightMap.get(thisLocation));
                }
            }
        }
        return lowPoints.stream().map(x -> x + 1).mapToInt(v -> v).sum();
    }

    int part2() throws IOException {
        BufferedReader reader = getInput("InputDay09");
        Map<Point, Integer> heightMap = new HashMap<>();
        int rowCount = 0;
        int colCount = 0;
        for (String line; (line = reader.readLine()) != null;) {
            colCount = line.length();
            for (int i = 0; i < line.length(); i++) {
                heightMap.put(new Point(rowCount, i), Integer.parseInt(line.substring(i, i + 1)));
            }
            rowCount++;
        }
        List<Integer> basins = new ArrayList<>();
        Set<Point> visitedPoints = new HashSet<>();
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {
                Point thisLocation = new Point(row, col);
                basins.add(basinSize(thisLocation, visitedPoints, heightMap));
            }
        }
        Collections.sort(basins);
        List<Integer> topThreeBasins = basins.subList(basins.size() - 3, basins.size());
        return topThreeBasins.stream().reduce(1, (x,y) -> x * y);
    }

    private int basinSize(Point thisLocation, Set<Point> visitedPoints, Map<Point, Integer> heightMap) {
        if (heightMap.getOrDefault(thisLocation, 9) == 9 || visitedPoints.contains(thisLocation)) {
            return 0;
        } else {
            visitedPoints.add(thisLocation);
            int basinSizes = 1;
            for (Point point : adjacentLocationsOf(thisLocation)) {
                basinSizes += basinSize(point, visitedPoints, heightMap);
            }

            return basinSizes;
        }
    }

    private List<Point> adjacentLocationsOf(Point point) {
        return List.of(
                new Point(point.row, point.col - 1),
                new Point(point.row, point.col + 1),
                new Point(point.row - 1, point.col),
                new Point(point.row + 1, point.col)
        );
    }

    private BufferedReader getInput(String inputFile) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream(inputFile);
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        return new BufferedReader(isr);
    }

    public static void main(String[] args) throws IOException {
        Day09 solution = new Day09();
        System.out.println(solution.part1());
        System.out.println(solution.part2());
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
}
