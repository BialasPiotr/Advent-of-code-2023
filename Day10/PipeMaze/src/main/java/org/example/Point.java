package org.example;

import java.util.Objects;

public class Point {
    public int x;
    public int y;

    // Constructor to initialize x and y values
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Point: (" + x + ", " + y + ")";
    }

    public Point GetNorth() {
        return new Point(x, y - 1);
    }

    public Point GetSouth() {
        return new Point(x, y + 1);
    }

    public Point GetEast() {
        return new Point(x + 1, y);
    }

    public Point GetWest() {
        return new Point(x - 1, y);
    }
}
