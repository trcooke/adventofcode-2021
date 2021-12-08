package com.timdrivendev.aoc2021;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

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
        BufferedReader reader = getInput("InputDay08Test");
        for (String line; (line = reader.readLine()) != null;) {
            String[] patterns = line.split("\\|")[0].trim().split(" ");
            String[] digits = line.split("\\|")[1].trim().split(" ");
            
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
        Day08 solution = new Day08();
        System.out.println(solution.part1());
        System.out.println(solution.part2());
    }
}
