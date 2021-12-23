package com.timdrivendev.aoc2021;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day16 {

    private int packetVersionCount = 0;

    int part1() throws IOException {
        packetVersionCount = 0;
        BufferedReader reader = getInput("InputDay16");
        String inputHex = reader.readLine();
        String input = hexToBin(inputHex);

        int headPointer = 0;
        List<Long> vals = new ArrayList<>();
        nextPacket(input, headPointer, vals);
        return packetVersionCount;
    }

    long part2() throws IOException {
        BufferedReader reader = getInput("InputDay16");
        String inputHex = reader.readLine();
        String input = hexToBin(inputHex);

        int headPointer = 0;
        List<Long> vals = new ArrayList<>();
        nextPacket(input, headPointer, vals);
        return vals.get(0);
    }

    private int nextPacket(String input, int headPointer, List<Long> vals) {
        int packetVersion = Integer.parseInt(input.substring(headPointer, headPointer + 3), 2);
        packetVersionCount += packetVersion;
        headPointer += 3;
        int packetTypeId = Integer.parseInt(input.substring(headPointer, headPointer + 3), 2);
        headPointer += 3;
        if (packetTypeId == 4) {
            boolean lastOne = false;
            String bits = "";
            do {
                lastOne = "0".equals(input.substring(headPointer, headPointer + 1));
                headPointer++;
                bits += input.substring(headPointer, headPointer + 4);
                headPointer += 4;
            } while (!lastOne);
            vals.add(Long.parseLong(bits, 2));
        } else {
            int lengthTypeId = Integer.parseInt(input.substring(headPointer, headPointer + 1), 2);
            headPointer++;
            List<Long> theseVals = new ArrayList<>();
            switch (lengthTypeId) {
                case 0: {
                    long totalLengthInBits = Long.parseLong(input.substring(headPointer, headPointer + 15), 2);
                    headPointer += 15;
                    int startingHeadPointer = headPointer;
                    while (headPointer < startingHeadPointer + totalLengthInBits) {
                        headPointer = nextPacket(input, headPointer, theseVals);
                    }
                    break;
                }
                case 1: {
                    int numberOfSubPackets = Integer.parseInt(input.substring(headPointer, headPointer + 11), 2);
                    headPointer += 11;
                    for (int i = 0; i < numberOfSubPackets; i++) {
                        headPointer = nextPacket(input, headPointer, theseVals);
                    }
                }
            }
            long thisVal = 0;
            switch (packetTypeId) {
                case 0: thisVal = theseVals.stream().reduce(0L, Long::sum); break;
                case 1: thisVal = theseVals.stream().reduce(1L, (x,y) -> x * y); break;
                case 2: thisVal = theseVals.stream().min(Long::compareTo).get(); break;
                case 3: thisVal = theseVals.stream().max(Long::compareTo).get(); break;
                case 5: thisVal = theseVals.get(0) > theseVals.get(1) ? 1L : 0L; break;
                case 6: thisVal = theseVals.get(0) < theseVals.get(1) ? 1L : 0L; break;
                case 7: thisVal = theseVals.get(0).equals(theseVals.get(1)) ? 1L : 0L; break;
            }
            vals.add(thisVal);
        }
        return headPointer;
    }

    private String hexToBin(String hex){
        hex = hex.replaceAll("0", "0000");
        hex = hex.replaceAll("1", "0001");
        hex = hex.replaceAll("2", "0010");
        hex = hex.replaceAll("3", "0011");
        hex = hex.replaceAll("4", "0100");
        hex = hex.replaceAll("5", "0101");
        hex = hex.replaceAll("6", "0110");
        hex = hex.replaceAll("7", "0111");
        hex = hex.replaceAll("8", "1000");
        hex = hex.replaceAll("9", "1001");
        hex = hex.replaceAll("A", "1010");
        hex = hex.replaceAll("B", "1011");
        hex = hex.replaceAll("C", "1100");
        hex = hex.replaceAll("D", "1101");
        hex = hex.replaceAll("E", "1110");
        hex = hex.replaceAll("F", "1111");
        return hex;
    }

    private BufferedReader getInput(String inputFile) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream(inputFile);
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        return new BufferedReader(isr);
    }

    public static void main(String[] args) throws IOException {
        Day16 solution = new Day16();
        System.out.println(solution.part1());
        System.out.println(solution.part2());
    }
}
