package org.example;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MirageMaintenance {

    private List<List<Integer>> getSequences() {
        List<List<Integer>> sequences = new ArrayList<>();
        Pattern nrPattern = Pattern.compile("-?\\d+");

        try (InputStream inputStream = MirageMaintenance.class.getClassLoader().getResourceAsStream("input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                List<Integer> sequence = new ArrayList<>();
                Matcher matcher = nrPattern.matcher(line);
                while (matcher.find()) {
                    sequence.add(Integer.valueOf(matcher.group()));
                }
                sequences.add(sequence);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sequences;
    }

    private List<Integer> getDifferences(List<Integer> sequence) {
        List<Integer> differences = new ArrayList<>();
        for (int i = 0; i < sequence.size() - 1; i++) {
            differences.add(sequence.get(i + 1) - sequence.get(i));
        }
        return differences;
    }

    private int getNextNumber(List<Integer> sequence) {
        List<Integer> differences = getDifferences(sequence);
        if (differences.stream().anyMatch(x -> x != 0)) {
            return sequence.get(sequence.size() - 1) + getNextNumber(differences);
        } else {
            return sequence.get(sequence.size() - 1);
        }
    }

    private int processInput() {
        List<List<Integer>> sequences = getSequences();
        return sequences.stream().mapToInt(this::getNextNumber).sum();
    }

    public static void main(String[] args) {
        MirageMaintenance mirageMaintenance = new MirageMaintenance();
        int result = mirageMaintenance.processInput();
        System.out.println("Part one: " + result);
    }
}
