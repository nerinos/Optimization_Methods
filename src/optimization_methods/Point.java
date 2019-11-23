package optimization_methods;

import java.util.function.BiFunction;

public class Point {
    private double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point point) {
        this.x = point.getX();
        this.y = point.getY();
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public Point subtraction(Point b) {
        return new Point(x - b.getX(), y - b.getY());
    }

    public Point sum(Point b) {
        return new Point(x + b.getX(), y + b.getY());
    }

    public Point multiply(double s) {
        return new Point(x * s, y * s);
    }

    public static Point getGradient(BiFunction<Double, Double, Double> f, Point point, double epsilon) {
        double dfx = (f.apply(point.getX() + epsilon, point.getY()) - f.apply(point.getX(), point.getY())) / epsilon;
        double dfy = (f.apply(point.getX(), point.getY() + epsilon) - f.apply(point.getX(), point.getY())) / epsilon;
        return new Point(dfx, dfy);
    }

    public double skal(Point b) {
        return x * b.getX() + y * b.getY();
    }

    public double getNorm() {
        return Math.sqrt((x * x) + (y * y));
    }

    public Matrix pointMultiply(Point a) {
        return new Matrix(this.x * a.getX(), this.y * a.getX(), this.x * a.getY(), this.y * a.getY());
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Matrix columnTimesRow(Point point) {
        return new Matrix(this.x * point.getX(), this.y * point.getX(), this.x * point.getY(), this.y * point.getY());
    }

    public void show() {
        System.out.println(getX() + "   " + getY());
    }
}


