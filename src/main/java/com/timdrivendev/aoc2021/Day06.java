package com.timdrivendev.aoc2021;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class Day06 {

    int part1() throws IOException {
        BufferedReader reader = getInput("InputDay06");
        List<Integer> lanternFish = Arrays.stream(reader.readLine().split(",")).map(Integer::parseInt).collect(Collectors.toList());
        for (int i = 0; i < 80; i++) {
            List<Integer> spawnedLanternFish = new ArrayList<>();
            lanternFish = lanternFish.stream().map(x -> {
                if (x == 0) {
                    spawnedLanternFish.add(8);
                    return 6;
                } else {
                    return x - 1;
                }
            }).collect(Collectors.toList());
            lanternFish.addAll(spawnedLanternFish);
        }
        return lanternFish.size();
    }

    Long part2() throws IOException {
        BufferedReader reader = getInput("InputDay06");
        List<Integer> lanternFish = Arrays.stream(reader.readLine().split(",")).map(Integer::parseInt).collect(Collectors.toList());
        Map<Integer, Long> lanternFishAges = new HashMap<>();
        lanternFish.forEach(a -> lanternFishAges.put(a, lanternFishAges.getOrDefault(a, 0L) + 1));
        for (int i = 0; i < 256; i++) {
            Long tempSpawnedLanternFish = 0L;
            for (int j = 0; j <= 8; j++) {
                if (j == 0) {
                    tempSpawnedLanternFish = lanternFishAges.getOrDefault(0, 0L);
                } else {
                    lanternFishAges.put(j - 1, lanternFishAges.getOrDefault(j, 0L));
                }
            }
            lanternFishAges.put(6, tempSpawnedLanternFish + lanternFishAges.getOrDefault(6, 0L));
            lanternFishAges.put(8, tempSpawnedLanternFish);
        }
        return lanternFishAges.values().stream().reduce(Long::sum).get();
    }

    Long part2a() throws IOException {
        BufferedReader reader = getInput("InputDay06");
        long[] fish = {0L,0L,0L,0L,0L,0L,0L,0L,0L};
        Arrays.stream(reader.readLine().split(",")).map(Integer::parseInt).forEach(x -> fish[x] = fish[x] + 1);
        for (int i = 0; i < 256; i++) {
            fish[index(i + 7)] = fish[index(i + 7)] + fish[index(i)];
        }
        return Arrays.stream(fish).reduce(0L, Long::sum);
    }

    private int index(int i) {
        return i % 9;
    }

    private BufferedReader getInput(String inputFile) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream(inputFile);
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        return new BufferedReader(isr);
    }

    public static void main(String[] args) throws IOException {
        Day06 solution = new Day06();
        System.out.println(solution.part1());
        Instant start = Instant.now();
        System.out.println(solution.part2());
        Instant stop = Instant.now();
        System.out.println(Duration.between(start, stop).toNanos());
        start = Instant.now();
        System.out.println(solution.part2a());
        stop = Instant.now();
        System.out.println(Duration.between(start, stop).toNanos());
    }
}
