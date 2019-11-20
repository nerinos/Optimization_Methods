package optimization_methods;

import java.util.function.DoubleFunction;

public interface OptimizationAlgorithm {
    public double minimize(double a, double b, double epsilon, DoubleFunction<Double> f);
}
