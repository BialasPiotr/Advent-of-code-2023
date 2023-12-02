package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CubeConundrum {

    public static void main(String[] args) {
        long sumOfPowers = 0;

        Pattern gamePattern = Pattern.compile("Game (\\d+): (.+)");
        Pattern cubePattern = Pattern.compile("(\\d+) (red|green|blue)");

        try (InputStream inputStream = CubeConundrum.class.getClassLoader().getResourceAsStream("input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                Matcher gameMatcher = gamePattern.matcher(line);
                if (gameMatcher.find()) {
                    String[] combinations = gameMatcher.group(2).split(";");
                    int maxRed = 0, maxGreen = 0, maxBlue = 0;

                    for (String combo : combinations) {
                        Matcher cubeMatcher = cubePattern.matcher(combo);
                        int red = 0, green = 0, blue = 0;

                        while (cubeMatcher.find()) {
                            int count = Integer.parseInt(cubeMatcher.group(1));
                            String color = cubeMatcher.group(2);

                            switch (color) {
                                case "red":
                                    red += count;
                                    break;
                                case "green":
                                    green += count;
                                    break;
                                case "blue":
                                    blue += count;
                                    break;
                            }
                        }

                        maxRed = Math.max(maxRed, red);
                        maxGreen = Math.max(maxGreen, green);
                        maxBlue = Math.max(maxBlue, blue);
                    }

                    long power = (long) maxRed * maxGreen * maxBlue;
                    sumOfPowers += power;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Sum of the power of minimum sets: " + sumOfPowers);
    }
}
