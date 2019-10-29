package com.abbyy.homework1;

public class Main {
    public static double CalcDistance(Point first, Point second) {
        return Math.sqrt(Math.pow((first.X - second.X), 2) + Math.pow((first.Y - second.Y), 2));
    }

    public static void main(String[] args) {

        Point first = new Point(10, 0);
        Point second = new Point(5, -12);

        System.out.println(CalcDistance(first, second));
    }
}