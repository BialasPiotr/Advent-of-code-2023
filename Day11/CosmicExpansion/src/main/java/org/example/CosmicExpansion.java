package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CosmicExpansion {
    public static class Star {
        public int x, y;

        public Star(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

    private char[][] sky;
    private List<Star> stars = new ArrayList<>();
    private List<Star> starsOriginal = new ArrayList<>();

    private void expandSky(int amount) {
        List<Integer> emptyRows = new ArrayList<>();
        List<Integer> emptyColumns = new ArrayList<>();

        // Identifying empty rows
        for (int y = 0; y < sky.length; y++) {
            boolean rowIsEmpty = true;
            for (int x = 0; x < sky[0].length; x++) {
                if (sky[y][x] == '#') {
                    rowIsEmpty = false;
                    break;
                }
            }
            if (rowIsEmpty) {
                emptyRows.add(y);
            }
        }

        // Identifying empty columns
        for (int x = 0; x < sky[0].length; x++) {
            boolean columnIsEmpty = true;
            for (int y = 0; y < sky.length; y++) {
                if (sky[y][x] == '#') {
                    columnIsEmpty = false;
                    break;
                }
            }
            if (columnIsEmpty) {
                emptyColumns.add(x);
            }
        }

        // Expanding stars' positions
        for (Star p : stars) {
            int rowCount = 0;
            for (int emptyRow : emptyRows) {
                if (p.y >= emptyRow) rowCount++;
            }
            p.y += amount * rowCount;

            int columnCount = 0;
            for (int emptyColumn : emptyColumns) {
                if (p.x >= emptyColumn) columnCount++;
            }
            p.x += amount * columnCount;
        }
    }

    private void sumDistances() {
        long sum = 0;
        for (int i = 0; i < stars.size(); i++) {
            for (int j = i + 1; j < stars.size(); j++) {
                long dx = Math.abs(stars.get(i).x - stars.get(j).x);
                long dy = Math.abs(stars.get(i).y - stars.get(j).y);
                sum += dx + dy;
            }
        }
        System.out.println("Sum of distances: " + sum);
    }

    private void processLine(String line, int lineNumber) {
        for (int i = 0; i < line.length(); i++) {
            sky[lineNumber][i] = line.charAt(i);
            if (line.charAt(i) == '#') {
                starsOriginal.add(new Star(i, lineNumber));
            }
        }
    }

    private void processFile() {
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("input.txt");
            if (inputStream == null) {
                throw new IOException("input.txt file not found in resources.");
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                if (lineNumber == 0) {
                    sky = new char[line.length()][line.length()];
                }
                processLine(line, lineNumber);
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void findAnswer() {
        processFile();

        stars.clear();
        stars.addAll(starsOriginal);
        expandSky(2);
        sumDistances();
    }

    public static void main(String[] args) {
        CosmicExpansion cosmicExpansion = new CosmicExpansion();
        cosmicExpansion.findAnswer();
    }
}
