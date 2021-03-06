package com.timdrivendev.aoc2021;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Day04 {

    int part1() throws IOException {
        BufferedReader reader = getInput("InputDay04");
        String calledNumbersStr = reader.readLine();
        Queue<Integer> numbersToBeCalled = Arrays.stream(calledNumbersStr.split(",")).map(Integer::parseInt).collect(Collectors.toCollection(ArrayDeque::new));
        List<BingoBoard> boards = new ArrayList<>();
        List<List<Integer>> boardLines = new ArrayList<>();
        int boardLineCount = 0;
        for (String line; (line = reader.readLine()) != null;) {
            if (line.isEmpty()) {
                continue;
            }
            List<Integer> boardLine = Arrays.stream(line.trim().split(" +")).map(Integer::parseInt).collect(Collectors.toList());
            boardLines.add(boardLine);
            boardLineCount++;
            if (boardLineCount == 5) {
                boards.add(new BingoBoard(boardLines));
                boardLines = new ArrayList<>();
                boardLineCount = 0;
            }
        }
        List<Integer> calledNumbers = new ArrayList<>();
        while (!numbersToBeCalled.isEmpty()) {
            Integer lastCalledNumber = numbersToBeCalled.poll();
            for (BingoBoard board : boards) {
                calledNumbers.add(lastCalledNumber);
                if (board.isWinner(calledNumbers)) {
                    return lastCalledNumber * board.uncalledNumbers(calledNumbers).stream().reduce(Integer::sum).get();
                }
            }
        }
        return 0;
    }

    int part2() throws IOException {
        BufferedReader reader = getInput("InputDay04");
        String calledNumbersStr = reader.readLine();
        Queue<Integer> numbersToBeCalled = Arrays.stream(calledNumbersStr.split(",")).map(Integer::parseInt).collect(Collectors.toCollection(ArrayDeque::new));
        List<BingoBoard> boards = new ArrayList<>();
        List<List<Integer>> boardLines = new ArrayList<>();
        int boardLineCount = 0;
        for (String line; (line = reader.readLine()) != null;) {
            if (line.isEmpty()) {
                continue;
            }
            List<Integer> boardLine = Arrays.stream(line.trim().split(" +")).map(Integer::parseInt).collect(Collectors.toList());
            boardLines.add(boardLine);
            boardLineCount++;
            if (boardLineCount == 5) {
                boards.add(new BingoBoard(boardLines));
                boardLines = new ArrayList<>();
                boardLineCount = 0;
            }
        }
        List<Integer> calledNumbers = new ArrayList<>();
        while (!numbersToBeCalled.isEmpty()) {
            Integer lastCalledNumber = numbersToBeCalled.poll();
            for (Iterator<BingoBoard> iterator = boards.iterator(); iterator.hasNext(); ) {
                BingoBoard bingoBoard = iterator.next();
                calledNumbers.add(lastCalledNumber);
                if (bingoBoard.isWinner(calledNumbers)) {
                    if (boards.size() == 1) {
                        return lastCalledNumber * bingoBoard.uncalledNumbers(calledNumbers).stream().reduce(Integer::sum).get();
                    }
                    iterator.remove();
                }
            }
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
        Day04 solution = new Day04();
        System.out.println(solution.part1());
        System.out.println(solution.part2());
    }

    private class BingoBoard {
        private List<Integer> boardNumbers = new ArrayList<>();
        private List<List<Integer>> winningLines = new ArrayList<>();

        public BingoBoard(List<List<Integer>> boardLines) {
            for (List<Integer> boardLine : boardLines) {
                winningLines.add(boardLine);
                boardNumbers.addAll(boardLine);
            }
            for (int i = 0; i < 5; i++) {
                List<Integer> verticalLine = new ArrayList<>();
                for (List<Integer> boardLine : boardLines) {
                    verticalLine.add(boardLine.get(i));
                }
                winningLines.add(verticalLine);
            }
        }

        public boolean isWinner(List<Integer> calledNumbers) {
            for (List<Integer> winningLine : winningLines) {
                if (calledNumbers.containsAll(winningLine)) {
                    return true;
                }
            }
            return false;
        }

        public List<Integer> uncalledNumbers(List<Integer> calledNumbers) {
            List<Integer> uncalledNumbers = new ArrayList<>(boardNumbers);
            uncalledNumbers.removeAll(calledNumbers);
            return uncalledNumbers;
        }
    }
}
