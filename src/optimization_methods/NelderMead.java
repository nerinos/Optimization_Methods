package optimization_methods;

import java.util.function.BiFunction;

public class NelderMead implements BiFunctionOptimizationAlgorithm {
    private double alpha, beta, gamma;
    private int maxiter;
    private BiFunction<Double, Double, Double> f;
    private Point p1, p2, p3;
    public NelderMead(BiFunction<Double, Double, Double> f, double alpha, double beta, double gamma, int maxiter,
                      Point p1, Point p2, Point p3) {
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
        this.maxiter = maxiter;
        this.f = f;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public NelderMead(BiFunction<Double, Double, Double> f, Point p1, Point p2, Point p3, int maxiter) {
       this.alpha = 1;
       this.beta = 0.5;
       this.gamma = 2;
       this.maxiter = maxiter;
       this.f = f;
       this.p1 = p1;
       this.p2 = p2;
       this.p3 = p3;
    }
    @Override
    public double[] minimize() {

        for (int i = 0; i < maxiter; i++) {
            Point b = p2;
            Point g = p1;
            Point w = p3;
            Point mid = new Point(g.getX() + b.getX(), g.getY() + b.getY()).multiply(0.5);

            Point xr = mid.sum(mid.subtraction(w).multiply(alpha));
            // Отражение
            if (f.apply(xr.getX(), xr.getY()) < f.apply(g.getX(), g.getY())) {
                w = xr;
            }
            else {
                if (f.apply(xr.getX(), xr.getY()) < f.apply(w.getX(), w.getY())) {
                    w = xr;
                }
                Point c = w.sum(mid).multiply(0.5);
                if (f.apply(c.getX(), c.getY()) < f.apply(w.getX(), w.getY())) {
                    w = c;
                }
            }
            if (f.apply(xr.getX(), xr.getY()) < f.apply(b.getX(), b.getY())) {
                // Растяжение
                Point xe = mid.sum(xr.subtraction(mid).multiply(gamma));
                if (f.apply(xe.getX(), xe.getY()) < f.apply(xr.getX(), xr.getY())) {
                    w = xe;
                }
                else {
                    w = xr;
                }
            }
            if (f.apply(xr.getX(), xr.getY()) > f.apply(g.getX(), g.getY())) {
                // Сжатие
                Point xc = mid.sum(w.subtraction(mid).multiply(beta));
                if (f.apply(xc.getX(), xc.getY()) < f.apply(w.getX(), w.getY())) {
                    w = xc;
                }
            }
            // Обновление точек
            p1 = w;
            p2 = g;
            p3 = b;
//            System.out.println("Точка : " + p3.getX() + "   " +  p3.getY());
        }
        return new double[]{p3.getX(), p3.getY()};
    }
}
