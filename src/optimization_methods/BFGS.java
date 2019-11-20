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

        while (getGradient(f, curpoint, ARGUMENT_DELTA).getNorm() > EPSILON && iter < maxiter) {
            pk = Hk.timesPoint(getGradient(f, curpoint, ARGUMENT_DELTA)).multiply(-1); // Направление
            Point next = getNextPoint(f, curpoint); // следующая точка

            // Разность между следующим и предыдущим приближениями
            Point sk = new Point(next.subtraction(curpoint));
            // Разность градиентов функции для следующего и текущего приближения
            Point yk = new Point(getGradient(f, next, ARGUMENT_DELTA)).subtraction(getGradient(f, curpoint, ARGUMENT_DELTA));

            double ro = 1.0 / yk.skal(sk);
            Matrix temp1 = (I.minus(sk.columnTimesRow(yk).multiplyByNumber(ro)));
            Matrix temp2 = (I.minus(yk.columnTimesRow(sk).multiplyByNumber(ro)));
//            temp1.show();
//            System.out.println();
//            temp2.show();
            Hk = temp1.times(Hk).times(temp2).sum(sk.columnTimesRow(sk).multiplyByNumber(ro));
            curpoint = next;
//            System.out.println("current point is");
//            System.out.println(curpoint.getX() + "   " + curpoint.getY());
        }
        return new double[] {curpoint.getX(), curpoint.getY()};
    }

    private Point getNextPoint(BiFunction<Double, Double, Double> f, Point point) {
//        setAlpha(1.0); // Изначально пробуем длину шага alpha = 1;
        GoldenRatio gd = new GoldenRatio();
        while(!wolfeConditions(f, point)) {

        }
        Point nextpoint = new Point(point.sum(getGradient(f, point, ARGUMENT_DELTA).multiply(alpha)));
//        System.out.println(wolfeConditions(f, point));
        return nextpoint;
    }


    private boolean wolfeConditions(BiFunction<Double, Double, Double> f, Point point) {
        /**
         *  Запускаем рандомизацию для alpha, чтобы найти такую длину шага, которая
         *  будет удовлетворять условиям Wolfe.
         */
        System.out.println("alpha is " + alpha);
        Point nextpoint = new Point(point.sum(pk.multiply(alpha))); // следующая точка (с учетом текущего шага)
        double matrixpoint = getGradient(f, point, ARGUMENT_DELTA).skal(pk);

        /**
         * matrixpoint - результат перемножения матриц размерностей (1, 3)x(3, 1) к примеру.
         * Задаем левую и правую части первого условия Wolfe
         */
        double firstcondleft = f.apply(nextpoint.getX(), nextpoint.getY());
        double firstcondright = f.apply(point.getX(), point.getY()) + DELTA_ONE * alpha * matrixpoint;
        /**
         * Далее задаем левую и правую части второго условия Wolfe
         */
        double secondcondleft = (getGradient(f, nextpoint, ARGUMENT_DELTA).skal(pk));
        double secondcondright = DELTA_TWO * matrixpoint;
        /**
         * Описываем сами условия
         */
        System.out.println("FIRST CONDITION: ");
        System.out.println(firstcondleft + "   " + firstcondright);
        boolean firstcond = firstcondleft <= firstcondright;
        boolean secondcond = secondcondleft >= secondcondright;
        System.out.println("SECOND CONDITION: ");
        System.out.println(secondcondleft + "   " + secondcondright);
        System.out.println(firstcond + "   " + secondcond);
        return firstcond && secondcond;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

}
