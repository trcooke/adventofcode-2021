package com.timdrivendev.aoc2021;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day03 {

    int part1() throws IOException {
        BufferedReader reader = getInput("InputDay03");
        int totalLines = 0;
        List<Integer> bitCount = new ArrayList<>();
        boolean first = true;
        for (String line; (line = reader.readLine()) != null;) {
            for (int i = 0; i < line.length(); i++) {
                if (first) {
                    bitCount.add(Integer.parseInt(line.substring(i, i+1)));
                } else {
                    bitCount.set(i, Integer.parseInt(line.substring(i, i+1)) + bitCount.get(i));
                }
            }
            totalLines++;
            first = false;
        }
        String gammaRate = "";
        String epsilonRate = "";
        for (Integer bit : bitCount) {
            if (bit > (totalLines - bit)) {
                gammaRate += "1";
                epsilonRate += "0";
            } else {
                gammaRate += "0";
                epsilonRate += "1";
            }
        }
        return Integer.parseInt(gammaRate, 2) * Integer.parseInt(epsilonRate, 2);
    }

    int part2() throws IOException {
        BufferedReader reader = getInput("InputDay03Test");
        for (String line; (line = reader.readLine()) != null;) {
        }
        return 0;
    }

    private BufferedReader getInput(String inputFile) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream(inputFile);
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        return new BufferedReader(isr);
    }

    public static void main(String[] args) throws IOException {
        Day03 solution = new Day03();
        System.out.println(solution.part1());
        System.out.println(solution.part2());
    }
}
