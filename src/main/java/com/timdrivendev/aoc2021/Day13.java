package com.timdrivendev.aoc2021;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Day13 {

    int part1() throws IOException {
        BufferedReader reader = getInput("InputDay13");
        Set<Point> dots = new HashSet<>();
        List<Fold> folds = new ArrayList<>();
        boolean dotsDone = false;
        for (String line; (line = reader.readLine()) != null;) {
            if (!dotsDone) {
                if (line.isEmpty()) {
                    dotsDone = true;
                    continue;
                }
                String[] dotCoords = line.split(",");
                dots.add(new Point(Integer.parseInt(dotCoords[1]), Integer.parseInt(dotCoords[0])));
            } else {
                String[] foldInstruction = line.split(" ")[2].split("=");
                folds.add(new Fold(foldInstruction[0], Integer.parseInt(foldInstruction[1])));
            }
        }
        Fold firstFold = folds.get(0);
        Set<Point> nextDots = new HashSet<>();
        for (Point dot : dots) {
            if ("x".equals(firstFold.direction)) {
                if (dot.col > firstFold.position) {
                    nextDots.add(new Point(dot.row, dot.col - ((dot.col - firstFold.position) * 2)));
                } else {
                    nextDots.add(dot);
                }
            }
            if ("y".equals(firstFold.direction)) {
                if (dot.row > firstFold.position) {
                    nextDots.add(new Point(dot.row - ((dot.row - firstFold.position) * 2), dot.col));
                } else {
                    nextDots.add(dot);
                }
            }
        }
        return nextDots.size();
    }

    int part2() throws IOException {
        BufferedReader reader = getInput("InputDay13");
        Set<Point> dots = new HashSet<>();
        List<Fold> folds = new ArrayList<>();
        boolean dotsDone = false;
        for (String line; (line = reader.readLine()) != null;) {
            if (!dotsDone) {
                if (line.isEmpty()) {
                    dotsDone = true;
                    continue;
                }
                String[] dotCoords = line.split(",");
                dots.add(new Point(Integer.parseInt(dotCoords[1]), Integer.parseInt(dotCoords[0])));
            } else {
                String[] foldInstruction = line.split(" ")[2].split("=");
                folds.add(new Fold(foldInstruction[0], Integer.parseInt(foldInstruction[1])));
            }
        }
        for (Fold fold : folds) {
            Set<Point> nextDots = new HashSet<>();
            for (Point dot : dots) {
                if ("x".equals(fold.direction)) {
                    if (dot.col > fold.position) {
                        nextDots.add(new Point(dot.row, dot.col - ((dot.col - fold.position) * 2)));
                    } else {
                        nextDots.add(dot);
                    }
                }
                if ("y".equals(fold.direction)) {
                    if (dot.row > fold.position) {
                        nextDots.add(new Point(dot.row - ((dot.row - fold.position) * 2), dot.col));
                    } else {
                        nextDots.add(dot);
                    }
                }
            }
            dots = new HashSet<>(nextDots);
        }
        printDots(dots);
        return dots.size();
    }

    private void printDots(Set<Point> dots) {
        int maxX = dots.stream().map(p -> p.col).max(Integer::compareTo).get();
        int maxY = dots.stream().map(p -> p.row).max(Integer::compareTo).get();
        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                if (dots.contains(new Point(y, x))) {
                    System.out.print("#");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        System.out.println();
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

    private class Fold {
        private String direction;
        private int position;

        public Fold(String direction, int position) {
            this.direction = direction;
            this.position = position;
        }
    }

    private BufferedReader getInput(String inputFile) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream(inputFile);
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        return new BufferedReader(isr);
    }

    public static void main(String[] args) throws IOException {
        Day13 solution = new Day13();
        System.out.println(solution.part1());
        System.out.println(solution.part2());
    }
}
