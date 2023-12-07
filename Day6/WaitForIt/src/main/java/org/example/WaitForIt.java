package org.example;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WaitForIt {

    public static void main(String[] args) {
        try {
            List<Race> racesPartOne = loadRacesPartOne();
            long resultPartOne = calculateTotalWays(racesPartOne);
            System.out.println("Part One: Total number of ways to beat the records: " + resultPartOne);

            Race racePartTwo = loadRacePartTwo();
            long resultPartTwo = racePartTwo.calculateWaysToWin();
            System.out.println("Part Two: Total number of ways to beat the record: " + resultPartTwo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<Race> loadRacesPartOne() throws Exception {
        List<Race> races = new ArrayList<>();
        try (InputStream inputStream = WaitForIt.class.getClassLoader().getResourceAsStream("input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String timeLine = reader.readLine().trim().split(":")[1].trim();
            String distanceLine = reader.readLine().trim().split(":")[1].trim();

            String[] times = timeLine.split("\\s+");
            String[] distances = distanceLine.split("\\s+");

            for (int i = 0; i < times.length; i++) {
                long time = Long.parseLong(times[i]);
                long distance = Long.parseLong(distances[i]);
                races.add(new Race(time, distance));
            }
        }
        return races;
    }

    private static Race loadRacePartTwo() throws Exception {
        try (InputStream inputStream = WaitForIt.class.getClassLoader().getResourceAsStream("input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String timeLine = reader.readLine().trim().split(":")[1].trim().replaceAll("\\s+", "");
            String distanceLine = reader.readLine().trim().split(":")[1].trim().replaceAll("\\s+", "");

            long time = Long.parseLong(timeLine);
            long distance = Long.parseLong(distanceLine);

            return new Race(time, distance);
        }
    }

    private static long calculateTotalWays(List<Race> races) {
        long totalWays = 1;
        for (Race race : races) {
            totalWays *= race.calculateWaysToWin();
        }
        return totalWays;
    }

    private static class Race {
        long time;
        long recordDistance;

        public Race(long time, long recordDistance) {
            this.time = time;
            this.recordDistance = recordDistance;
        }

        public long calculateWaysToWin() {
            long ways = 0;
            for (long i = 0; i < time; i++) {
                long distance = i * (time - i);
                if (distance > recordDistance) {
                    ways++;
                }
            }
            return ways;
        }
    }
}
