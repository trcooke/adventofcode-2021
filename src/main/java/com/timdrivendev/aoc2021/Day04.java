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
        Queue<Integer> calledNumbers = Arrays.stream(calledNumbersStr.split(",")).map(Integer::parseInt).collect(Collectors.toCollection(ArrayDeque::new));
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
        while (!calledNumbers.isEmpty()) {
            Integer lastCalledNumber = calledNumbers.poll();
            for (BingoBoard board : boards) {
                board.calledNumber(lastCalledNumber);
                if (board.isWinner()) {
                    return lastCalledNumber * board.uncalledNumbers().stream().reduce(Integer::sum).get();
                }
            }
        }
        return 0;
    }

    int part2() throws IOException {
        BufferedReader reader = getInput("InputDay04");
        String calledNumbersStr = reader.readLine();
        Queue<Integer> calledNumbers = Arrays.stream(calledNumbersStr.split(",")).map(Integer::parseInt).collect(Collectors.toCollection(ArrayDeque::new));
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
        while (!calledNumbers.isEmpty()) {
            Integer lastCalledNumber = calledNumbers.poll();
            for (BingoBoard bingoBoard : boards) {
                bingoBoard.calledNumber(lastCalledNumber);
                boolean allBoardsWinners = true;
                for (BingoBoard board1 : boards) {
                    if (!board1.isWinner()) {
                        allBoardsWinners = false;
                        break;
                    }
                }
                if (allBoardsWinners) {
                    return lastCalledNumber * bingoBoard.uncalledNumbers().stream().reduce(Integer::sum).get();
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
        private List<Integer> calledNumbers = new ArrayList<>();
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

        public void calledNumber(Integer calledNumber) {
            calledNumbers.add(calledNumber);
        }

        public boolean isWinner() {
            for (List<Integer> winningLine : winningLines) {
                if (calledNumbers.containsAll(winningLine)) {
                    return true;
                }
            }
            return false;
        }

        public List<Integer> uncalledNumbers() {
            List<Integer> uncalledNumbers = new ArrayList<>(boardNumbers);
            uncalledNumbers.removeAll(calledNumbers);
            return uncalledNumbers;
        }
    }
}
