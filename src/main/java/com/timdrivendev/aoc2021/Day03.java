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
        BufferedReader reader = getInput("InputDay03");
        List<String> input = new ArrayList<>();
        for (String line; (line = reader.readLine()) != null;) {
            input.add(line);
        }
        int lineLen = input.get(0).length();
        List<String> oxygenGeneratorRating = new ArrayList<>(input);
        for (int i = 0; i < lineLen; i++) {
            String mostCommon = mostCommonAt(oxygenGeneratorRating, i);
            oxygenGeneratorRating = filterByCharAt(oxygenGeneratorRating, mostCommon, i);
            if (oxygenGeneratorRating.size() == 1) {
                break;
            }
        }
        List<String> co2scrubberRating = new ArrayList<>(input);
        for (int i = 0; i < lineLen; i++) {
            String mostCommon = leastCommonAt(co2scrubberRating, i);
            co2scrubberRating = filterByCharAt(co2scrubberRating, mostCommon, i);
            if (co2scrubberRating.size() == 1) {
                break;
            }
        }
        return Integer.parseInt(oxygenGeneratorRating.get(0), 2) * Integer.parseInt(co2scrubberRating.get(0), 2);
    }

    private String mostCommonAt(List<String> input, final int i) {
        int total = input.size();
        long count = input.stream().filter(x -> "1".equals(String.valueOf(x.charAt(i)))).count();
        return count >= (total - count) ? "1" : "0";
    }

    private String leastCommonAt(List<String> input, final int i) {
        int total = input.size();
        long count = input.stream().filter(x -> "1".equals(String.valueOf(x.charAt(i)))).count();
        return count < (total - count) ? "1" : "0";
    }

    private List<String> filterByCharAt(List<String> oxygenGeneratorRating, String mostCommon, int i) {
        return oxygenGeneratorRating.stream().filter(x -> mostCommon.equals(String.valueOf(x.charAt(i)))).collect(Collectors.toList());
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
