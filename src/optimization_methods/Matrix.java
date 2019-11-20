package optimization_methods;

public class Matrix {
    public double x1, x2, y1, y2;
    public Matrix(double x1, double x2, double y1, double y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    public Matrix multiplyByNumber(double number) {
        return new Matrix(x1 * number, x2 * number ,y1 * number, y2 * number);

    }
    public Point timesPoint(Point point) {
        return new Point(x1 * point.getX() + x2 * point.getY(),
                y1 * point.getX() + y2 * point.getY());
    }
    public void minusNumber(double number) {
        this.x1 = x1 - number;
        this.x2 = x2 - number;
        this.y1 = y1 - number;
        this.y2 = y2 - number;
    }
    public Matrix sum(Matrix h) {
        return new Matrix(x1 + h.x1, x2 + h.x2, y1 + h.y1, y2 + h.y2);
    }

    public Matrix minus(Matrix m) {
        return new Matrix(this.x1 - m.x1, this.x2 - m.x2, this.y1 - m.y1, this.x2 - m.y2);
    }

    public Matrix times(Matrix m) {
        return new Matrix(x1 * m.x1 + x2 * y1, x1 * m.x2 + x2 * m.y2, y1 * m.x1 + y2 * m.y1, y1 * m.x2 + y2 * m.y2);
    }

    public void show() {
        System.out.println(x1 + "   " + x2);
        System.out.println(y1 + "   " + y2);
    }

    public static void main(String[] args) {
        Matrix h1 = new Matrix(1, 2, 3, 4);
        Matrix h2 = new Matrix(1, 2, 3, 4);
        h1.show();
        System.out.println();
        h2.show();
        Matrix h = h1.times(h2);
        System.out.println();
        h.show();

        Point p1 = new Point(1, 2);
        Point p2 = new Point(1, 2);
        Matrix p = p1.columnTimesRow(p2);
        p.show();



    }

}


