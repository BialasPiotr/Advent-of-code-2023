package org.example;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HauntedWasteland {

    public static void main(String[] args) {
        Map<String, String[]> mapData = new HashMap<>();

        try (InputStream inputStream = HauntedWasteland.class.getClassLoader().getResourceAsStream("input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String instructions = reader.readLine(); 
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || !line.contains(" = ")) {
                    continue;
                }

                String[] parts = line.split(" = ");
                if (parts.length != 2) {
                    throw new Exception("Invalid line format: " + line);
                }

                String node = parts[0];
                String[] connections = parts[1].replaceAll("[()]", "").split(", ");
                if (connections.length != 2) {
                    throw new Exception("Invalid connections format for node " + node);
                }

                mapData.put(node, connections);
            }

            int stepsPartOne = findPathToZZZ(instructions, mapData);
            System.out.println("Part one: " + stepsPartOne);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int findPathToZZZ(String instructions, Map<String, String[]> mapData) throws Exception {
        int stepCount = 0;
        String currentNode = "AAA";
        int instructionIndex = 0;

        while (!currentNode.equals("ZZZ")) {
            if (!mapData.containsKey(currentNode)) {
                throw new Exception("Node not found in mapData: " + currentNode);
            }

            char direction = instructions.charAt(instructionIndex);
            String[] nextNodes = mapData.get(currentNode);

            if (nextNodes == null || nextNodes.length < 2) {
                throw new Exception("Invalid mapping for node: " + currentNode);
            }

            currentNode = nextNodes[direction == 'L' ? 0 : 1];
            if (currentNode == null) {
                throw new Exception("Invalid direction for node: " + currentNode);
            }

            stepCount++;
            instructionIndex = (instructionIndex + 1) % instructions.length();
        }

        return stepCount;
    }
}
