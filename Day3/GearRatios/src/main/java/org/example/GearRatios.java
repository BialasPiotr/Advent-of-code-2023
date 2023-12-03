package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GearRatios {

    public static void main(String[] args) throws IOException {

        try (InputStream inputStream = GearRatios.class.getClassLoader().getResourceAsStream("input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            var pattern = Pattern.compile("\\d+");
            var parts = new ArrayList<Part>();
            var lines = reader.lines().collect(Collectors.toList());

            for (var y = 0; y < lines.size(); y++) {
                var line = lines.get(y);
                var matcher = pattern.matcher(line);

                while (matcher.find()) {
                    if (matcher.group().isBlank())
                        continue;

                    parts.add(new Part(Integer.parseInt(matcher.group()), Position.range(y, matcher.start(), matcher.end())));
                }
            }

            var part1 = parts.stream()
                    .filter(part -> part.positions().stream()
                            .flatMap(p -> Position.neighbours(p).stream())
                            .filter(n -> Position.overlaps(n, 0, lines.get(0).length(), 0, lines.size()))
                            .anyMatch(p -> {
                                var c = lines.get(p.y()).charAt(p.x());
                                return !(c == '.' || Character.isDigit(c));
                            }))
                    .mapToInt(Part::number)
                    .sum();

            System.out.println("Part 01: " + part1);

            var ratios = new ArrayList<Integer>();

            for (int y = 0; y < lines.size(); y++) {
                for (int x = 0; x < lines.get(y).length(); x++) {
                    var c = lines.get(y).charAt(x);

                    if (c != '*')
                        continue;

                    var gear = new Position(x, y);

                    var neighbours = parts.stream()
                            .filter(part -> part.positions().stream()
                                    .flatMap(p -> Position.neighbours(p).stream())
                                    .filter(n -> Position.overlaps(n, 0, lines.get(0).length(), 0, lines.size()))
                                    .anyMatch(n -> n.equals(gear)))
                            .toList();

                    if (neighbours.size() > 1) {
                        ratios.add(neighbours.stream().mapToInt(Part::number).reduce(1, Math::multiplyExact));
                    }
                }
            }

            var part2 = ratios.stream().mapToInt(Integer::intValue).sum();

            System.out.println("Part 02: " + part2);
        }
    }

    record Position(int x, int y) {
        static List<Position> range(int y, int minX, int maxX) {
            return IntStream.range(minX, maxX).mapToObj(x -> new Position(x, y)).toList();
        }

        static List<Position> neighbours(Position p) {
            return Arrays.stream(new int[][]{{0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}})
                    .map(arr -> new Position(p.x() + arr[0], p.y() + arr[1]))
                    .toList();
        }

        static boolean overlaps(Position p, int minX, int maxX, int minY, int maxY) {
            return p.x() >= minX && p.x() < maxX && p.y() >= minY && p.y() < maxY;
        }
    }

    record Part(int number, List<Position> positions) {
    }
}
