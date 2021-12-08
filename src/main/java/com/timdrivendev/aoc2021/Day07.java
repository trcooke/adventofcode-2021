package com.timdrivendev.aoc2021;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day07 {

    int part1() throws IOException {
        BufferedReader reader = getInput("InputDay07");
        List<Integer> crabPositions = Arrays.stream(reader.readLine().split(",")).map(Integer::parseInt).sorted(Integer::compareTo).collect(Collectors.toList());
        int leastFuelCost = Integer.MAX_VALUE;
        for (int i = crabPositions.get(0); i < crabPositions.get(crabPositions.size() - 1); i++) {
            int fuelCost = 0;
            for (Integer crabPosition : crabPositions) {
                fuelCost += Math.abs(i - crabPosition);
            }
            if (fuelCost < leastFuelCost) {
                leastFuelCost = fuelCost;
            }
        }
        return leastFuelCost;
    }

    int part2() throws IOException {
        BufferedReader reader = getInput("InputDay07");
        List<Integer> crabPositions = Arrays.stream(reader.readLine().split(",")).map(Integer::parseInt).sorted(Integer::compareTo).collect(Collectors.toList());
        int leastFuelCost = Integer.MAX_VALUE;
        for (int i = crabPositions.get(0); i < crabPositions.get(crabPositions.size() - 1); i++) {
            int fuelCost = 0;
            for (Integer crabPosition : crabPositions) {
                int steps = Math.abs(i - crabPosition);
                fuelCost += steps / 2.0 * (steps + 1);
            }
            if (fuelCost < leastFuelCost) {
                leastFuelCost = fuelCost;
            }
        }
        return leastFuelCost;
    }

    private BufferedReader getInput(String inputFile) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream(inputFile);
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        return new BufferedReader(isr);
    }

    public static void main(String[] args) throws IOException {
        Day07 solution = new Day07();
        System.out.println(solution.part1());
        System.out.println(solution.part2());
    }
}
