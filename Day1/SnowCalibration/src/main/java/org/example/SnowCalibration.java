package SnowCalibration.src.main.java.org.example;

import java.io.InputStream;
import java.util.Scanner;

public class SnowCalibration {
    public static void main(String[] args) {
        try {
            InputStream inputStream = SnowCalibration.class.getClassLoader().getResourceAsStream("input.txt");
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found!");
            } else {
                Scanner scanner = new Scanner(inputStream);
                int totalSum = 0;

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    int firstDigit = -1, lastDigit = -1;

                    for (int i = 0; i < line.length(); i++) {
                        if (Character.isDigit(line.charAt(i))) {
                            if (firstDigit == -1) {
                                firstDigit = Character.getNumericValue(line.charAt(i));
                            }
                            lastDigit = Character.getNumericValue(line.charAt(i));
                        }
                    }

                    if (firstDigit != -1 && lastDigit != -1) {
                        int calibrationValue = firstDigit * 10 + lastDigit;
                        totalSum += calibrationValue;
                    }
                }

                System.out.println("Sum of all calibration values: " + totalSum);
                scanner.close();
            }
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }
}
