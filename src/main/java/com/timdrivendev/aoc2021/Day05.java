package com.timdrivendev.aoc2021;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

public class Day05 {

    long part1() throws IOException {
        BufferedReader reader = getInput("InputDay05");
        Map<Point, Integer> occupiedPoints = new HashMap<>();
        for (String line; (line = reader.readLine()) != null;) {
            String[] coords = line.split(" -> ");
            int x1 = Integer.parseInt(coords[0].split(",")[0]);
            int y1 = Integer.parseInt(coords[0].split(",")[1]);
            int x2 = Integer.parseInt(coords[1].split(",")[0]);
            int y2 = Integer.parseInt(coords[1].split(",")[1]);
            if (x1 == x2 || y1 == y2) {
                if (x1 == x2) {
                    if (y1 < y2) {
                        IntStream.rangeClosed(y1, y2).forEach(y -> occupiedPoints.put(new Point(x1, y), occupiedPoints.getOrDefault(new Point(x1, y),0) + 1));
                    } else {
                        IntStream.rangeClosed(y2, y1).forEach(y -> occupiedPoints.put(new Point(x1, y), occupiedPoints.getOrDefault(new Point(x1, y),0) + 1));
                    }
                }
                if (y1 == y2) {
                    if (x1 < x2) {
                        IntStream.rangeClosed(x1, x2).forEach(x -> occupiedPoints.put(new Point(x, y1), occupiedPoints.getOrDefault(new Point(x, y1),0) + 1));
                    } else {
                        IntStream.rangeClosed(x2, x1).forEach(x -> occupiedPoints.put(new Point(x, y1), occupiedPoints.getOrDefault(new Point(x, y1),0) + 1));
                    }
                }
            }
        }
        return occupiedPoints.values().stream().filter(cnt -> cnt > 1).count();
    }

    long part2() throws IOException {
        BufferedReader reader = getInput("InputDay05");
        Map<Point, Integer> occupiedPoints = new HashMap<>();
        for (String line; (line = reader.readLine()) != null; ) {
            String[] coords = line.split(" -> ");
            int x1 = Integer.parseInt(coords[0].split(",")[0]);
            int y1 = Integer.parseInt(coords[0].split(",")[1]);
            int x2 = Integer.parseInt(coords[1].split(",")[0]);
            int y2 = Integer.parseInt(coords[1].split(",")[1]);
            if (x1 == x2) {
                if (y1 < y2) {
                    IntStream.rangeClosed(y1, y2).forEach(y -> occupiedPoints.put(new Point(x1, y), occupiedPoints.getOrDefault(new Point(x1, y), 0) + 1));
                } else {
                    IntStream.rangeClosed(y2, y1).forEach(y -> occupiedPoints.put(new Point(x1, y), occupiedPoints.getOrDefault(new Point(x1, y), 0) + 1));
                }
            } else if (y1 == y2) {
                if (x1 < x2) {
                    IntStream.rangeClosed(x1, x2).forEach(x -> occupiedPoints.put(new Point(x, y1), occupiedPoints.getOrDefault(new Point(x, y1), 0) + 1));
                } else {
                    IntStream.rangeClosed(x2, x1).forEach(x -> occupiedPoints.put(new Point(x, y1), occupiedPoints.getOrDefault(new Point(x, y1), 0) + 1));
                }
            } else if (x1 < x2 && y1 < y2) {
                for (int i = 0; i <= x2 - x1; i++) {
                    occupiedPoints.put(new Point(x1 + i, y1 + i), occupiedPoints.getOrDefault(new Point(x1 + i, y1 + i), 0) + 1);
                }
            } else if (x1 < x2) {
                for (int i = 0; i <= x2 - x1; i++) {
                    occupiedPoints.put(new Point(x1 + i, y1 - i), occupiedPoints.getOrDefault(new Point(x1 + i, y1 - i), 0) + 1);
                }
            } else if (y1 < y2) {
                for (int i = 0; i <= x1 - x2; i++) {
                    occupiedPoints.put(new Point(x1 - i, y1 + i), occupiedPoints.getOrDefault(new Point(x1 - i, y1 + i), 0) + 1);
                }
            } else {
                for (int i = 0; i <= x1 - x2; i++) {
                    occupiedPoints.put(new Point(x1 - i, y1 - i), occupiedPoints.getOrDefault(new Point(x1 - i, y1 - i), 0) + 1);
                }
            }
        }
        return occupiedPoints.values().stream().filter(cnt -> cnt > 1).count();
    }

    private BufferedReader getInput(String inputFile) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream(inputFile);
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        return new BufferedReader(isr);
    }

    public static void main(String[] args) throws IOException {
        Day05 solution = new Day05();
        System.out.println(solution.part1());
        System.out.println(solution.part2());
    }

    private static class Point {
        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Point[" + x +
                    "," + y +
                    ']';
        }
    }
}
