package optimization_methods;

import java.util.function.DoubleFunction;

public class GoldenRatio implements OptimizationAlgorithm{
    final double PHI = (1 + Math.sqrt(5)) / 2;
    public GoldenRatio() {
    }

    @Override
    public double minimize(double a, double b, double epsilon, DoubleFunction<Double> f){
        double x1, x2;
        while (true){
            if (Math.abs(b - a) < epsilon)
                break;
            x1 = b - (b - a) / PHI;
            x2 = a + (b - a) / PHI;
            if (f.apply(x1) >= f.apply(x2))
                a = x1;
            else
                b = x2;
        }
        return (a + b) / 2;
    }
}
