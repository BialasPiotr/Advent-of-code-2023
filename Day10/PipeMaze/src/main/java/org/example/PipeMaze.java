package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class PipeMaze {
    private static char[][] charArray = null;
    private static int numRows = 0;
    private static int numCols = 0;

    public static void main(String[] args) {
        try (
                InputStream inputStream = PipeMaze.class.getClassLoader().getResourceAsStream("input.txt");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            List<String> lines = new ArrayList<>();
            String line;
            numCols = 0;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
                numCols = Math.max(numCols, line.length());
            }

            numRows = lines.size();

            charArray = new char[numRows][numCols];
            Point startPoint = new Point(0,0);

            for (int i = 0; i < numRows; i++) {
                line = lines.get(i);
                char[] chars = line.toCharArray();
                for (int j = 0; j < numCols; j++) {
                    var c = chars[j];
                    charArray[i][j] = c;
                    if(c == 'S'){
                        startPoint = new Point(j, i);
                    }
                }
            }

            Boolean isConnectedNorth = canGoNorth(startPoint);
            Boolean isConnectedSouth = canGoSouth(startPoint);
            Boolean isConnectedWest = canGoWest(startPoint);
            Boolean isConnectedEast = canGoEast(startPoint);
            var nextPoint = startPoint;
            var startChar = 'X';
            if(isConnectedNorth && isConnectedSouth){
                startChar = '|';
            }else if(isConnectedWest && isConnectedEast){
                startChar = '-';
            }else if(isConnectedNorth && isConnectedEast){
                startChar = 'L';
            }else if(isConnectedNorth && isConnectedWest){
                startChar = 'J';
            }else if(isConnectedSouth && isConnectedEast){
                startChar = 'F';
            }else if(isConnectedSouth && isConnectedWest){
                startChar = '7';
            }

            charArray[startPoint.y][startPoint.x] = startChar;

            FindPath(startPoint);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void PrintMap(){
        System.out.println("##############");
        for (char[] rowChars : charArray) {
            for (char ch : rowChars) {
                System.out.print(ch + " ");
            }
            System.out.println();
        }
    }

    public static void FindPath(Point start) {
        Map<Point, Point> pointMap = new HashMap<>();
        var current = start;
        var visitedCells = 0;
        while(true){
            pointMap.put(current, current);
            visitedCells += 1;

            if(isConnectedNorth(current)){
                var pos = current.GetNorth();
                if(pos.equals(start) && visitedCells > 2){
                    break;
                }
                if(!pointMap.containsKey(pos)){
                    current = pos;
                    continue;
                }
            }

            if(isConnectedSouth(current)){
                var pos = current.GetSouth();
                if(pos.equals(start) && visitedCells > 2){
                    break;
                }
                if(!pointMap.containsKey(pos)){
                    current = pos;
                    continue;
                }
            }

            if(isConnectedEast(current)){
                var pos = current.GetEast();
                if(pos.equals(start) && visitedCells > 2){
                    break;
                }
                if(!pointMap.containsKey(pos)){
                    current = pos;
                    continue;
                }
            }

            if(isConnectedWest(current)){
                var pos = current.GetWest();
                if(pos.equals(start) && visitedCells > 2){
                    break;
                }
                if(!pointMap.containsKey(pos)){
                    current = pos;
                    continue;
                }
            }
        }

        System.out.println(visitedCells / 2);

    }

    private static boolean isConnectedNorth(Point point) {
        if(point.y == 0)
            return false;
        if(!"|JL".contains(String.valueOf(charArray[point.y][point.x]))){
            return false;
        }
        return "|7F".contains(String.valueOf(charArray[point.y - 1][point.x]));
    }
    private static boolean isConnectedSouth(Point point) {
        if(point.y + 1 >= numRows)
            return false;

        if(!"|7F".contains(String.valueOf(charArray[point.y][point.x]))){
            return false;
        }
        return "|LJ".contains(String.valueOf(charArray[point.y + 1][point.x]));
    }
    private static boolean isConnectedWest(Point point) {
        if(point.x == 0)
            return false;

        if(!"-J7".contains(String.valueOf(charArray[point.y][point.x]))){
            return false;
        }
        return "-LF".contains(String.valueOf(charArray[point.y][point.x - 1]));
    }
    private static boolean isConnectedEast(Point point) {
        if(point.x + 1 >= numCols)
            return false;
        if(!"-LF".contains(String.valueOf(charArray[point.y][point.x]))){
            return false;
        }
        return "-J7".contains(String.valueOf(charArray[point.y][point.x + 1]));
    }

    private static boolean canGoNorth(Point point) {
        if(point.y == 0)
            return false;
        return "|7F".contains(String.valueOf(charArray[point.y - 1][point.x]));
    }
    private static boolean canGoSouth(Point point) {
        if(point.y + 1 >= numRows)
            return false;
        return "|LJ".contains(String.valueOf(charArray[point.y + 1][point.x]));
    }
    private static boolean canGoWest(Point point) {
        if(point.x == 0)
            return false;
        return "-LF".contains(String.valueOf(charArray[point.y][point.x - 1]));
    }
    private static boolean canGoEast(Point point) {
        if(point.x + 1 >= numCols)
            return false;
        return "-J7".contains(String.valueOf(charArray[point.y][point.x + 1]));
    }
}

