package optimization_methods;

import java.util.function.DoubleFunction;

public class Dichotomy implements OptimizationAlgorithm {
    public Dichotomy() {
    }

    @Override
    public double minimize(double a, double b, double epsilon, DoubleFunction<Double> f) {
        while(true) {
            double mid = (a + b) / 2;
            double f1 = f.apply(mid - epsilon);
            double f2 = f.apply(mid + epsilon);
            if (f1 < f2) {
                b = mid;
            }
            else {
                a = mid;
            }
            if (Math.abs(b - a) < epsilon)
                return (a + b) / 2;
        }
    }
}
