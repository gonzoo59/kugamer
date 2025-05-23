package org.opencv.core;
/* loaded from: classes2.dex */
public class Rect {
    public int height;
    public int width;
    public int x;
    public int y;

    public Rect(int i, int i2, int i3, int i4) {
        this.x = i;
        this.y = i2;
        this.width = i3;
        this.height = i4;
    }

    public Rect() {
        this(0, 0, 0, 0);
    }

    public Rect(Point point, Point point2) {
        this.x = (int) (point.x < point2.x ? point.x : point2.x);
        this.y = (int) (point.y < point2.y ? point.y : point2.y);
        this.width = ((int) (point.x > point2.x ? point.x : point2.x)) - this.x;
        this.height = ((int) (point.y > point2.y ? point.y : point2.y)) - this.y;
    }

    public Rect(Point point, Size size) {
        this((int) point.x, (int) point.y, (int) size.width, (int) size.height);
    }

    public Rect(double[] dArr) {
        set(dArr);
    }

    public void set(double[] dArr) {
        if (dArr != null) {
            this.x = dArr.length > 0 ? (int) dArr[0] : 0;
            this.y = dArr.length > 1 ? (int) dArr[1] : 0;
            this.width = dArr.length > 2 ? (int) dArr[2] : 0;
            this.height = dArr.length > 3 ? (int) dArr[3] : 0;
            return;
        }
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
    }

    public Rect clone() {
        return new Rect(this.x, this.y, this.width, this.height);
    }

    public Point tl() {
        return new Point(this.x, this.y);
    }

    public Point br() {
        return new Point(this.x + this.width, this.y + this.height);
    }

    public Size size() {
        return new Size(this.width, this.height);
    }

    public double area() {
        return this.width * this.height;
    }

    public boolean empty() {
        return this.width <= 0 || this.height <= 0;
    }

    public boolean contains(Point point) {
        return ((double) this.x) <= point.x && point.x < ((double) (this.x + this.width)) && ((double) this.y) <= point.y && point.y < ((double) (this.y + this.height));
    }

    public int hashCode() {
        long doubleToLongBits = Double.doubleToLongBits(this.height);
        long doubleToLongBits2 = Double.doubleToLongBits(this.width);
        long doubleToLongBits3 = Double.doubleToLongBits(this.x);
        long doubleToLongBits4 = Double.doubleToLongBits(this.y);
        return ((((((((int) (doubleToLongBits ^ (doubleToLongBits >>> 32))) + 31) * 31) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >>> 32)))) * 31) + ((int) (doubleToLongBits3 ^ (doubleToLongBits3 >>> 32)))) * 31) + ((int) (doubleToLongBits4 ^ (doubleToLongBits4 >>> 32)));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Rect) {
            Rect rect = (Rect) obj;
            return this.x == rect.x && this.y == rect.y && this.width == rect.width && this.height == rect.height;
        }
        return false;
    }

    public String toString() {
        return "{" + this.x + ", " + this.y + ", " + this.width + "x" + this.height + "}";
    }
}
