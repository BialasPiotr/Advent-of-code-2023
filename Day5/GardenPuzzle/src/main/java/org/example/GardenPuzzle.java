package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class GardenPuzzle {
    public static void main(String[] args) throws IOException {
        try (InputStream inputStream = GardenPuzzle.class.getClassLoader().getResourceAsStream("input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            Scanner s = new Scanner(reader);
            long minValue = Long.MAX_VALUE, minRangeValue = Long.MAX_VALUE;

            String l = s.nextLine();
            Scanner ss = new Scanner(l.substring(l.indexOf(':') + 2));
            ArrayList<Long> vals = new ArrayList<>();
            ArrayList<long[]> vals2 = new ArrayList<>();
            while (ss.hasNextLong()) {
                vals.add(ss.nextLong());
            }

            for (int i = 0; i < vals.size(); i += 2) {
                vals2.add(new long[]{vals.get(i), vals.get(i) + vals.get(i + 1) - 1});
            }
            ss.close();
            s.nextLine();

            while (s.hasNextLine()) {
                s.nextLine();
                ArrayList<Long> newVals = new ArrayList<>(vals);
                ArrayList<long[]> mapped = new ArrayList<>();

                while (s.hasNextLine() && !(l = s.nextLine()).isEmpty()) {
                    ss = new Scanner(l);
                    long[] L = new long[3];
                    for (int i = 0; i < 3; i++) {
                        L[i] = ss.nextLong();
                    }
                    ss.close();

                    final long dest = L[0], src = L[1], len = L[2];

                    for (int i = 0; i < vals.size(); i++) {
                        long v = vals.get(i);
                        if (v >= src && v < src + len) {
                            newVals.set(i, v + dest - src);
                        }
                    }

                    for (int i = 0; i < vals2.size(); i++) {
                        long[] range = vals2.get(i);
                        long start = range[0], end = range[1];
                        if (start < src + len && end >= src) {
                            long[] m = {Math.max(start, dest), Math.min(end + dest - src, dest + len - 1)};
                            boolean hasUnmapped = false;

                            if (start < src) {
                                range[1] = src - 1;
                                hasUnmapped = true;
                            }
                            if (end >= src + len) {
                                if (hasUnmapped) {
                                    vals2.add(new long[]{src + len, end});
                                } else {
                                    range[0] = src + len;
                                    hasUnmapped = true;
                                }
                            }

                            if (hasUnmapped) {
                                mapped.add(m);
                            } else {
                                vals2.remove(i--);
                            }
                        }
                    }
                }

                vals = newVals;
                vals2.addAll(mapped);
            }
            s.close();

            for (long i : vals) {
                if (i < minValue) minValue = i;
            }
            for (long[] i : vals2) {
                if (i[0] < minRangeValue) minRangeValue = i[0];
            }

            System.out.println("Part one: " + minValue);
            System.out.println("Part two: " + minRangeValue);
        }
    }
}
