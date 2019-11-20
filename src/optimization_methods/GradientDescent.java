package optimization_methods;

import java.util.function.BiFunction;

public class GradientDescent implements BiFunctionOptimizationAlgorithm {
    private double epsilon;
    private Point x0;
    private BiFunction<Double, Double, Double> f;
    private BiFunction<Double, Double, Double> df = (dfx, dfy) -> dfx + dfy;


    public GradientDescent(BiFunction<Double, Double, Double> f, Point startPoint, double epsilon) {
        this.epsilon = epsilon;
        this.f = f;
        this.x0 = startPoint;
    }

    @Override
    public double[] minimize() {
        Point nextPoint;
        GoldenRatio gd = new GoldenRatio();
        int iter = 0;
        while (true) {
            Point grad = Point.getGradient(f, x0, epsilon);
            final Point finalStartPoint = x0;
            double stepSize = gd.minimize(0.0, 1.0, 1e-05,
                    (x) -> {
                        Point point = finalStartPoint.subtraction(grad.multiply(x));
                        return f.apply(point.getX(), point.getY());
                    });


            nextPoint = x0.subtraction(grad.multiply(stepSize));
//            System.out.println("Точка : " + x0.getX() + "   " +  x0.getY());
            iter++;
            Boolean stopCriteria1 = nextPoint.subtraction(x0).getNorm() < epsilon;
            Boolean stopCriteria2 = Math.abs(f.apply(nextPoint.getX(), nextPoint.getY()) - f.apply(x0.getX(), x0.getY())) < epsilon;
            if (stopCriteria1 || stopCriteria2) {
//                System.out.println("Количество итераций: " + iter);
                return new double[]{nextPoint.getX(), nextPoint.getY()};
            }

            x0 = new Point(nextPoint.getX(), nextPoint.getY());


        }


    }


}
