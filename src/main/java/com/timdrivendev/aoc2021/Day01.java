package com.timdrivendev.aoc2021;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Day01 {

    int part1() throws IOException {
        BufferedReader reader = getInput("InputDay01");
        int countIncreased = 0;
        int lastDepth = -1;
        for (String line; (line = reader.readLine()) != null;) {
            int depth = Integer.parseInt(line);
            if (lastDepth == -1) {
                lastDepth = depth;
            }
            if (depth > lastDepth) {
                countIncreased++;
            }
            lastDepth = depth;
        }
        return countIncreased;
    }

    int part2() throws IOException {
        BufferedReader reader = getInput("InputDay01");
        int countIncreased = 0;
        List<Integer> depths = new ArrayList<>();
        for (String line; (line = reader.readLine()) != null;) {
            depths.add(Integer.parseInt(line));
        }
        Integer lastAveDepth = -1;
        for (int i = 0; i < depths.size() - 2; i++) {
            Integer aveDepth = depths.get(i) + depths.get(i + 1) + depths.get(i + 2);
            if (lastAveDepth == -1) {
                lastAveDepth = aveDepth;
            }
            if (aveDepth > lastAveDepth) {
                countIncreased++;
            }
            lastAveDepth = aveDepth;
        }
        return countIncreased;
    }

    private BufferedReader getInput(String inputFile) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream(inputFile);
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        return new BufferedReader(isr);
    }

    public static void main(String[] args) throws IOException {
        Day01 solution = new Day01();
        System.out.println(solution.part1());
        System.out.println(solution.part2());
    }
}
