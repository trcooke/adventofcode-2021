package com.timdrivendev.aoc2021;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Day02 {

    int part1() throws IOException {
        BufferedReader reader = getInput("InputDay02");
        int horizontalPosition = 0;
        int depth = 0;
        for (String line; (line = reader.readLine()) != null;) {
            String[] command = line.split(" ");
            switch (command[0]) {
                case "forward": horizontalPosition += Integer.parseInt(command[1]); break;
                case "down": depth += Integer.parseInt(command[1]); break;
                case "up": depth -= Integer.parseInt(command[1]); break;
            }
        }
        return horizontalPosition * depth;
    }

    int part2() throws IOException {
        BufferedReader reader = getInput("InputDay02");
        int horizontalPosition = 0;
        int depth = 0;
        int aim = 0;
        for (String line; (line = reader.readLine()) != null;) {
            String[] command = line.split(" ");
            switch (command[0]) {
                case "forward": {
                    horizontalPosition += Integer.parseInt(command[1]);
                    depth += (aim * Integer.parseInt(command[1]));
                    break;
                }
                case "down": aim += Integer.parseInt(command[1]); break;
                case "up": aim -= Integer.parseInt(command[1]); break;
            }
        }
        return horizontalPosition * depth;
    }

    private BufferedReader getInput(String inputFile) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream(inputFile);
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        return new BufferedReader(isr);
    }

    public static void main(String[] args) throws IOException {
        Day02 solution = new Day02();
        System.out.println(solution.part1());
        System.out.println(solution.part2());
    }
}
