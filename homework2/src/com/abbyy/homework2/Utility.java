package com.abbyy.homework2;

public final class Utility {
    private Utility() {}

    public static int RectArea(Rect rect) {
        return (rect.topLeft.Y - rect.bottomRight.Y) * (rect.bottomRight.X - rect.topLeft.X);
    }

    public static boolean IsSquare(Rect rect) {
        return (rect.topLeft.Y - rect.bottomRight.Y) == (rect.bottomRight.X - rect.topLeft.X);
    }

    public static boolean HaveOverlap(Rect rect1, Rect rect2) {
        if (rect1.topLeft.X > rect2.bottomRight.X || rect2.topLeft.X > rect1.bottomRight.X) {
            return false;
        }
        if (rect1.topLeft.Y < rect2.bottomRight.Y || rect2.topLeft.Y < rect1.bottomRight.Y) {
            return false;
        }
        return true;
    }
}
