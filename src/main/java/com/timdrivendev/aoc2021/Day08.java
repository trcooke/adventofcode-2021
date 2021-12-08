package com.timdrivendev.aoc2021;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Day08 {

    int part1() throws IOException {
        BufferedReader reader = getInput("InputDay08");
        int countOfEasyDigits = 0;
        for (String line; (line = reader.readLine()) != null;) {
            String[] digits = line.split("\\|")[1].trim().split(" ");
            for (String digit : digits) {
                if (digit.length() == 2 || digit.length() == 4 || digit.length() == 3 || digit.length() == 7) {
                    countOfEasyDigits++;
                }
            }

        }
        return countOfEasyDigits;
    }
/*
 aaaa
b    c
b    c
 dddd
e    f
e    f
 gggg
 */
    int part2() throws IOException {
        BufferedReader reader = getInput("InputDay08");
        List<Integer> outputValues = new ArrayList<>();
        for (String line; (line = reader.readLine()) != null;) {
            List<String> patternStrings = Arrays.asList(line.split("\\|")[0].trim().split(" "));
            List<Set<String>> patterns = patternStrings.stream().map(p -> Set.of(p.split(""))).collect(Collectors.toList());
            String[] digitStrings = line.split("\\|")[1].trim().split(" ");
            List<Set<String>> digits = Arrays.stream(digitStrings).map(d -> Set.of(d.split(""))).collect(Collectors.toList());
            Map<String, Set<String>> knownPatterns = new HashMap<>();
            Map<Set<String>, String> decoded = new HashMap<>();
            for (Iterator<Set<String>> iterator = patterns.iterator(); iterator.hasNext(); ) {
                Set<String> pattern = iterator.next();
                switch (pattern.size()) {
                    case 2:
                        knownPatterns.put("1", pattern);
                        decoded.put(pattern, "1");
                        iterator.remove();
                        break;
                    case 3:
                        knownPatterns.put("7", pattern);
                        decoded.put(pattern, "7");
                        iterator.remove();
                        break;
                    case 4:
                        knownPatterns.put("4", pattern);
                        decoded.put(pattern, "4");
                        iterator.remove();
                        break;
                    case 7:
                        knownPatterns.put("8", pattern);
                        decoded.put(pattern, "8");
                        iterator.remove();
                        break;
                }
            }
            for (Set<String> pattern : patterns) {
                if (pattern.size() == 6
                        && pattern.containsAll(knownPatterns.get("1"))
                        && pattern.containsAll(knownPatterns.get("7"))
                        && !pattern.containsAll(knownPatterns.get("4"))) {
                    decoded.put(pattern, "0");
                } else if (pattern.size() == 6
                        && pattern.containsAll(knownPatterns.get("4"))) {
                    decoded.put(pattern, "9");
                } else if (pattern.size() == 6) {
                    decoded.put(pattern, "6");
                } else if (pattern.size() == 5
                        && pattern.containsAll(knownPatterns.get("7"))) {
                    decoded.put(pattern, "3");
                } else if (pattern.size() == 5) {
                    Set<String> tmpPattern = new HashSet<>(pattern);
                    tmpPattern.removeAll(knownPatterns.get("4"));
                    if (tmpPattern.size() == 3) {
                        decoded.put(pattern, "2");
                    } else {
                        decoded.put(pattern, "5");
                    }
                } else {
                    System.out.println("Missed!");
                }
            }
            String numberString = "";
            for (Set<String> digit : digits) {
                numberString += decoded.get(digit);
            }
            outputValues.add(Integer.parseInt(numberString));
        }
        return outputValues.stream().mapToInt(outputValue -> outputValue).sum();
    }

    private BufferedReader getInput(String inputFile) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream(inputFile);
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        return new BufferedReader(isr);
    }

    public static void main(String[] args) throws IOException {
        Day08 solution = new Day08();
        System.out.println(solution.part1());
        System.out.println(solution.part2());
    }
}
