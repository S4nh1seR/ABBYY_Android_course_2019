package com.abbyy.homework2;

public class Part2 {

    public void Run() {
        Rect rect1 = new Rect(new Point(0, 8), new Point(8, 0));
        Rect rect2 = new Rect(new Point(0, 1), new Point(1, -1));

        System.out.println(Utility.RectArea(rect1));
        System.out.println(Utility.IsSquare(rect1));

        System.out.println(Utility.RectArea(rect2));
        System.out.println(Utility.IsSquare(rect2));

        if (Utility.HaveOverlap(rect1, rect2)) {
            System.out.println("Rect-s overlap");
        } else {
            System.out.println("Rect-s don't overlap");
        }
    }
}
