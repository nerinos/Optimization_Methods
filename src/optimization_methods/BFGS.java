package optimization_methods;

import org.w3c.dom.ls.LSOutput;

import java.util.Random;
import java.util.function.BiFunction;
import static optimization_methods.Point.getGradient;

public class BFGS implements BiFunctionOptimizationAlgorithm {

    private BiFunction<Double, Double, Double> f;
    private Point curpoint, pk;
    private double epsilon;
    private int maxiter;
    private double alpha;
    public static final double ARGUMENT_DELTA = 1e-6;
    public static final double EPSILON = 1e-3;
    public static final double DELTA_ONE = 1e-4;
    public static final double DELTA_TWO = 0.95;
    public static final double MAX_ALPHA = 1.0;
    public static final double MIN_ALPHA = 0.0;


    public BFGS(BiFunction<Double, Double, Double> f, Point x0,  double epsilon, int maxiter) {
        this.f = f;
        this.epsilon = epsilon;
        this.curpoint = x0;
        this.maxiter = maxiter;
    }

    @Override
    public double[] minimize() {
        int iter = 0;
        Matrix Hk = new Matrix(1, 0, 1, 0);
        Matrix I = new Matrix(1, 0, 1, 0);
        GoldenRatio gd = new GoldenRatio();

        while (getGradient(f, curpoint, ARGUMENT_DELTA).getNorm() > EPSILON && iter < maxiter) {
            pk = Hk.timesPoint(getGradient(f, curpoint, ARGUMENT_DELTA)).multiply(-1); // Направление
            double alpha = gd.minimize(0.0, 1.0, 1e-05,
                    (x) -> {
                        Point point1 = curpoint.sum(pk.multiply(x));
                        return f.apply(point1.getX(), point1.getY());
                    });
            Point next = new Point(curpoint.sum(pk.multiply(alpha)));


            // Разность между следующим и предыдущим приближениями
            Point sk = new Point(next.subtraction(curpoint));
            // Разность градиентов функции для следующего и текущего приближения
            Point yk = new Point(getGradient(f, next, ARGUMENT_DELTA)).subtraction(getGradient(f, curpoint, ARGUMENT_DELTA));

            double ro = 1.0 / yk.skal(sk);

            Matrix temp1 = (I.minus(sk.columnTimesRow(yk).multiplyByNumber(ro)));
            Matrix temp2 = (I.minus(yk.columnTimesRow(sk).multiplyByNumber(ro)));
            Hk = temp1.times(Hk).times(temp2).sum(sk.columnTimesRow(sk).multiplyByNumber(ro));
            curpoint = next;
            System.out.println("current point is");
            System.out.println(curpoint.getX() + "   " + curpoint.getY());
        }
        return new double[] {curpoint.getX(), curpoint.getY()};
    }

}
