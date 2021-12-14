package com.timdrivendev.aoc2021;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day14 {

    long part1() throws IOException {
        BufferedReader reader = getInput("InputDay14");
        List<String> lines = new ArrayList<>();
        for (String line; (line = reader.readLine()) != null;) {
            lines.add(line);
        }
        String polymer = lines.get(0);
        Map<String, String> pairInsertionRules = new HashMap<>();
        for (int i = 2; i < lines.size(); i++) {
            String[] rule = lines.get(i).split(" -> ");
            pairInsertionRules.put(rule[0], rule[1]);
        }
        for (int step = 0; step < 10; step++) {
            StringBuilder nextPolymer = new StringBuilder(polymer.substring(0, 1));
            for (int i = 0; i < polymer.length() - 1; i++) {
                if (pairInsertionRules.containsKey(polymer.substring(i, i + 2))) {
                    nextPolymer.append(pairInsertionRules.get(polymer.substring(i, i + 2))).append(polymer.charAt(i + 1));
                } else {
                    nextPolymer.append(polymer.charAt(i + 1));
                }
            }
            polymer = nextPolymer.toString();
        }
        Map<Character, Long> characterCounts = polymer.chars().mapToObj(c -> (char) c).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Long min = Collections.min(characterCounts.values());
        Long max = Collections.max(characterCounts.values());
        return max - min;
    }

    long part2() throws IOException {
        BufferedReader reader = getInput("InputDay14");
        List<String> lines = new ArrayList<>();
        for (String line; (line = reader.readLine()) != null;) {
            lines.add(line);
        }
        String polymer = lines.get(0);
        Map<String, String> pairInsertionRules = new HashMap<>();
        for (int i = 2; i < lines.size(); i++) {
            String[] rule = lines.get(i).split(" -> ");
            pairInsertionRules.put(rule[0], rule[1]);
        }
        Map<Character, Long> charCounts = getCharCounts(polymer, pairInsertionRules, 40);
        Long min = Collections.min(charCounts.values());
        Long max = Collections.max(charCounts.values());
        return max - min;
    }

    private Map<Character, Long> getCharCounts(String polymer, Map<String, String> pairInsertionRules, int steps) {
        Map<PolymerCacheKey, Map<Character, Long>> polymerCache = new HashMap<>();
        Map<Character, Long> charCount = new HashMap<>();
        charCount.put(polymer.charAt(0), 1L);
        for (int i = 0; i < polymer.length() - 1; i++) {
            Map<Character, Long> nextCharCount = getCharCountsIter(polymer.substring(i, i + 2), pairInsertionRules, steps, polymerCache);
            for (Character character : nextCharCount.keySet()) {
                charCount.put(character, charCount.getOrDefault(character, 0L) + nextCharCount.get(character));
            }
        }
        return charCount;
    }

    private Map<Character, Long> getCharCountsIter(String polymerPair, Map<String, String> pairInsertionRules, int steps, Map<PolymerCacheKey, Map<Character, Long>> polymerCache) {
        Map<Character, Long> charCount = new HashMap<>();
        if (steps == 0) {
            charCount.put(polymerPair.charAt(1), charCount.getOrDefault(polymerPair.charAt(1), 0L) + 1L);
        } else if (polymerCache.containsKey(new PolymerCacheKey(polymerPair, steps))) {
            charCount = new HashMap<>(polymerCache.get(new PolymerCacheKey(polymerPair, steps)));
        } else if (!pairInsertionRules.containsKey(polymerPair)) {
            charCount.put(polymerPair.charAt(1), charCount.getOrDefault(polymerPair.charAt(1), 0L) + 1L);
        } else {
            String nextPolymer = polymerPair.charAt(0) + pairInsertionRules.get(polymerPair) + polymerPair.charAt(1);
            for (int i = 0; i < nextPolymer.length() - 1; i++) {
                Map<Character, Long> nextCharCount = getCharCountsIter(nextPolymer.substring(i, i + 2), pairInsertionRules, steps - 1, polymerCache);
                for (Character character : nextCharCount.keySet()) {
                    charCount.put(character, charCount.getOrDefault(character, 0L) + nextCharCount.get(character));
                }
            }
        }
        polymerCache.put(new PolymerCacheKey(polymerPair, steps), new HashMap<>(charCount));
        return charCount;
    }

    private BufferedReader getInput(String inputFile) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream(inputFile);
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        return new BufferedReader(isr);
    }

    public static void main(String[] args) throws IOException {
        Day14 solution = new Day14();
        System.out.println(solution.part1());
        System.out.println(solution.part2());
    }

    private class PolymerCacheKey {
        private String polymerPair;
        private int steps;

        public PolymerCacheKey(String polymerPair, int steps) {
            this.polymerPair = polymerPair;
            this.steps = steps;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PolymerCacheKey that = (PolymerCacheKey) o;

            if (steps != that.steps) return false;
            return polymerPair != null ? polymerPair.equals(that.polymerPair) : that.polymerPair == null;
        }

        @Override
        public int hashCode() {
            int result = polymerPair != null ? polymerPair.hashCode() : 0;
            result = 31 * result + steps;
            return result;
        }
    }
}
