package com.abbyy.homework2;

public class Rect {
    public Point topLeft;
    public Point bottomRight;

    Rect(Point _topLeft, Point _bottomRight) {
        if (_topLeft.X > _bottomRight.X || _topLeft.Y < _bottomRight.Y) {
            throw new IllegalArgumentException();
        }
        this.topLeft = _topLeft;
        this.bottomRight = _bottomRight;
    }
}
