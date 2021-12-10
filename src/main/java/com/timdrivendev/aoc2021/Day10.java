package com.timdrivendev.aoc2021;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Day10 {

    int part1() throws IOException {
        BufferedReader reader = getInput("InputDay10");
        Set<Character> openingCharacters = Set.of('(', '[', '{', '<');
        Set<Character> closingCharacters = Set.of(')', ']', '}', '>');
        Map<Character, Character> closingChar = Map.of('(', ')', '[', ']', '{', '}', '<', '>');
        Map<Character, Integer> charScores = Map.of(')', 3, ']', 57, '}', 1197, '>', 25137);
        int syntaxErrorScore = 0;
        for (String line; (line = reader.readLine()) != null;) {
            Deque<Character> stack = new ArrayDeque<>();
            char[] characters = line.trim().toCharArray();
            for (char character : characters) {
                if (openingCharacters.contains(character)) {
                    stack.push(character);
                } else if (closingCharacters.contains(character)) {
                    Character popped = stack.pop();
                    if (character != closingChar.get(popped)) {
                        syntaxErrorScore += charScores.get(character);
                        break;
                    }
                }
            }
        }
        return syntaxErrorScore;
    }

    Long part2() throws IOException {
        BufferedReader reader = getInput("InputDay10");
        Set<Character> openingCharacters = Set.of('(', '[', '{', '<');
        Set<Character> closingCharacters = Set.of(')', ']', '}', '>');
        Map<Character, Character> closingChar = Map.of('(', ')', '[', ']', '{', '}', '<', '>');
        Map<Character, Integer> charScores = Map.of(')', 1, ']', 2, '}', 3, '>', 4);
        List<Long> errorScores = new ArrayList<>();
        for (String line; (line = reader.readLine()) != null;) {
            boolean isValid = true;
            Deque<Character> stack = new ArrayDeque<>();
            List<Character> characters = Arrays.stream(line.trim().split("")).map(c -> c.charAt(0)).collect(Collectors.toList());
            for (char character : characters) {
                if (openingCharacters.contains(character)) {
                    stack.push(character);
                } else if (closingCharacters.contains(character)) {
                    Character popped = stack.pop();
                    if (character != closingChar.get(popped)) {
                        isValid = false;
                        break;
                    }
                }
            }
            if (isValid) {
                long errorScore = 0;
                for (Character character : stack) {
                    errorScore *= 5;
                    errorScore += charScores.get(closingChar.get(character));
                }
                errorScores.add(errorScore);
            }
        }
        Collections.sort(errorScores);
        return errorScores.get(errorScores.size() / 2);
    }

    private BufferedReader getInput(String inputFile) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream(inputFile);
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        return new BufferedReader(isr);
    }

    public static void main(String[] args) throws IOException {
        Day10 solution = new Day10();
        System.out.println(solution.part1());
        System.out.println(solution.part2());
    }
}
