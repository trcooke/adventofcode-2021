package com.timdrivendev.aoc2021;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day17 {

    int part1() throws IOException {
        BufferedReader reader = getInput("InputDay17");
        String input = reader.readLine().substring(12);
        String[] targetArea = input.split(",");
        int xmin = Integer.parseInt(targetArea[0].substring(3).split("\\.\\.")[0]);
        int xmax = Integer.parseInt(targetArea[0].substring(3).split("\\.\\.")[1]);
        int ymin = Integer.parseInt(targetArea[1].substring(3).split("\\.\\.")[0]);
        int ymax = Integer.parseInt(targetArea[1].substring(3).split("\\.\\.")[1]);
        Point targetAreaStart = new Point(xmin, ymin);
        Point targetAreaStop = new Point(xmax, ymax);
        int largestY = 0;
        for (int xvel = 0; xvel < 100; xvel++) {
            for (int yvel = 0; yvel < 5000; yvel++) {
                Point currentPoint = new Point(0, 0);
                List<Point> path = findPath(currentPoint, xvel, yvel, targetAreaStart, targetAreaStop);
                if (hitsTarget(path, targetAreaStart, targetAreaStop)) {
                    if (largestYOf(path) > largestY) {
                        largestY = largestYOf(path);
                    }
                }
            }
        }
        return largestY;
    }

    int part2() throws IOException {
        BufferedReader reader = getInput("InputDay17");
        String input = reader.readLine().substring(12);
        String[] targetArea = input.split(",");
        int xmin = Integer.parseInt(targetArea[0].substring(3).split("\\.\\.")[0]);
        int xmax = Integer.parseInt(targetArea[0].substring(3).split("\\.\\.")[1]);
        int ymin = Integer.parseInt(targetArea[1].substring(3).split("\\.\\.")[0]);
        int ymax = Integer.parseInt(targetArea[1].substring(3).split("\\.\\.")[1]);
        Point targetAreaStart = new Point(xmin, ymin);
        Point targetAreaStop = new Point(xmax, ymax);
        int hitCount = 0;
        for (int xvel = 0; xvel < 156; xvel++) {
            for (int yvel = -103; yvel < 5000; yvel++) {
                Point currentPoint = new Point(0, 0);
                List<Point> path = findPath(currentPoint, xvel, yvel, targetAreaStart, targetAreaStop);
                if (hitsTarget(path, targetAreaStart, targetAreaStop)) {
                    hitCount++;
                }
            }
        }
        System.out.println();
        return hitCount;
    }

    private int largestYOf(List<Point> path) {
        int largestY = 0;
        for (Point point : path) {
            if (point.y > largestY) {
                largestY = point.y;
            }
        }
        return largestY;
    }

    private boolean hitsTarget(List<Point> path, Point targetAreaStart, Point targetAreaStop) {
        Point lastPoint = path.get(path.size() - 1);
        return lastPoint.x >= targetAreaStart.x
                && lastPoint.x <= targetAreaStop.x
                && lastPoint.y >= targetAreaStart.y
                && lastPoint.y <= targetAreaStop.y;
    }

    private List<Point> findPath(Point currentPoint, int xvel, int yvel, Point targetAreaStart, Point targetAreaStop) {
        ArrayList<Point> startingPoint = new ArrayList<>();
        startingPoint.add(currentPoint);
        return findPathIter(currentPoint, xvel, yvel, targetAreaStart, targetAreaStop, startingPoint);
    }

    private List<Point> findPathIter(Point currentPoint, int xvel, int yvel, Point targetAreaStart, Point targetAreaStop, List<Point> path) {
        Point nextPoint = new Point(currentPoint.x + xvel, currentPoint.y + yvel);
        if (overshot(nextPoint, targetAreaStart, targetAreaStop)) {
            return path;
        }
        path.add(nextPoint);
        int nextXVel;
        if (xvel == 0) {
            nextXVel = 0;
        } else if (xvel > 0) {
            nextXVel = xvel - 1;
        } else {
            nextXVel = xvel + 1;
        }
        return findPathIter(nextPoint, nextXVel, yvel - 1, targetAreaStart, targetAreaStop, path);
    }


    private boolean overshot(Point currentPoint, Point targetAreaStart, Point targetAreaStop) {
        return currentPoint.x > targetAreaStop.x || currentPoint.y < targetAreaStart.y;
    }

    private BufferedReader getInput(String inputFile) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream(inputFile);
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        return new BufferedReader(isr);
    }

    public static void main(String[] args) throws IOException {
        Day17 solution = new Day17();
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
            return "(" + x + "," + y + ")";
        }
    }
}
