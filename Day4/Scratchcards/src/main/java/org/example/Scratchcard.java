package org.example;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Scratchcard {

    public static void main(String[] args) {
        try (InputStream inputStream = Scratchcard.class.getClassLoader().getResourceAsStream("input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            List<String> cards = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                cards.add(line);
            }

            // Part One
            int totalPoints = 0;
            for (String card : cards) {
                totalPoints += calculatePointsForCard(card);
            }
            System.out.println("Part One: Total Points: " + totalPoints);

            // Part Two
            Map<Integer, Integer> cardCopies = new HashMap<>();
            for (int i = 0; i < cards.size(); i++) {
                cardCopies.put(i + 1, 1);
            }

            for (int i = 0; i < cards.size(); i++) {
                int matches = calculateMatchesForCard(cards.get(i));
                for (int j = 1; j <= matches; j++) {
                    int nextCardIndex = i + j;
                    if (nextCardIndex < cards.size()) {
                        cardCopies.put(nextCardIndex + 1, cardCopies.getOrDefault(nextCardIndex + 1, 0) + cardCopies.get(i + 1));
                    }
                }
            }

            int totalCards = cardCopies.values().stream().mapToInt(Integer::intValue).sum();
            System.out.println("Part Two: Total Scratchcards: " + totalCards);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Part One: Method
    private static int calculatePointsForCard(String cardData) {
        String[] parts = cardData.split("\\|");
        Set<String> winningNumbers = new HashSet<>(Arrays.asList(parts[0].trim().split("\\s+")));
        String[] yourNumbers = parts[1].trim().split("\\s+");

        int points = 0;
        for (String number : yourNumbers) {
            if (winningNumbers.contains(number)) {
                points = points == 0 ? 1 : points * 2;
            }
        }

        return points;
    }

    // Part Two: Method
    private static int calculateMatchesForCard(String cardData) {
        String[] parts = cardData.split("\\|");
        Set<String> winningNumbers = new HashSet<>(Arrays.asList(parts[0].trim().split("\\s+")));
        String[] yourNumbers = parts[1].trim().split("\\s+");

        int matches = 0;
        for (String number : yourNumbers) {
            if (winningNumbers.contains(number)) {
                matches++;
            }
        }

        return matches;
    }
}
