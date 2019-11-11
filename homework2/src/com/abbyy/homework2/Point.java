package com.abbyy.homework2;

public final class Point {
    public final int X;
    public final int Y;

    public Point(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    public Point() {
        this.X = 0;
        this.Y = 0;
    }

    public Point(Point other) {
        this.X = other.X;
        this.Y = other.Y;
    }
}
