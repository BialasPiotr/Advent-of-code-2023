package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CubeConundrum {

    public static void main(String[] args) {
        int sumOfValidGameIds = 0;

        Pattern gamePattern = Pattern.compile("Game (\\d+): (.+)");
        Pattern cubePattern = Pattern.compile("(\\d+) (red|green|blue)");

        try (InputStream inputStream = CubeConundrum.class.getClassLoader().getResourceAsStream("input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                Matcher gameMatcher = gamePattern.matcher(line);
                if (gameMatcher.find()) {
                    int gameId = Integer.parseInt(gameMatcher.group(1));
                    String[] combinations = gameMatcher.group(2).split(";");
                    boolean isValidGame = true;

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

                        if (red > 12 || green > 13 || blue > 14) {
                            isValidGame = false;
                            break;
                        }
                    }

                    if (isValidGame) {
                        sumOfValidGameIds += gameId;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Sum of possible games: " + sumOfValidGameIds);
    }
}
