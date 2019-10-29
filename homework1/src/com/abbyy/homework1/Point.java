package com.abbyy.homework1;

import java.util.Objects;

public class Point {
    public int X;
    public int Y;

    public Point(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    // автосгенерированные:
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return X == point.X &&
                Y == point.Y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(X, Y);
    }

    // кастомные:
//    @Override
//    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + X;
//        result = prime * result + Y;
//        return result;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//
//        if (obj == null || getClass() != obj.getClass())
//            return false;
//
//        Point other = (Point) obj;
//        return X == other.X && Y == other.Y;
//    }
}
