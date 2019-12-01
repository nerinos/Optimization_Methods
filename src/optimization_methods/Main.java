package optimization_methods;

import java.util.function.BiFunction;
import java.util.function.DoubleFunction;

public class Main {
    public static void main(String[] args) {
        double a = 1.0;
        double b = 1.0;
        DoubleFunction<Double> f = (x) -> a * x + b / Math.exp(x);

        GoldenRatio gs = new GoldenRatio();
        Dichotomy dc = new Dichotomy();

        System.out.println("Метод золотого сечения: x = " + gs.minimize(-2, 2, 1e-5, f));
        System.out.println("Метод дихотомии: x = " + dc.minimize(-2, 2, 1e-5, f));

        BiFunction<Double, Double, Double> rosenbrock = (x1, x2) -> 100 * Math.pow(x2 - x1 * x1, 2) + 5 * Math.pow(1 - x1, 2);
        BiFunction<Double, Double, Double> himmelblau = (x1, x2) -> Math.pow(Math.pow(x1, 2) + x2 - 11, 2) + Math.pow(x1 + Math.pow(x2, 2) - 7, 2);

        GradientDescent gd1 = new GradientDescent(rosenbrock, new Point(1, 2), 1e-5);
        double[] result1 = gd1.minimize();
        System.out.println("Градиентный спуск для функции Розенброка: " + result1[0] + "   " + result1[1]);

        GradientDescent gd2 = new GradientDescent(himmelblau, new Point(1, 10), 1e-5);
        double[] result2 = gd2.minimize();
        System.out.println("Градиентный спуск для функции Химмельблау: " + result2[0] + "   " + result2[1]);

//      Начальные точки деформируемого треугольника
        Point p1 = new Point(21, 40);
        Point p2 = new Point(56, 18);
        Point p3 = new Point(37, 63);

        NelderMead nm1 = new NelderMead(rosenbrock, p1, p2, p3, 1000);
        double[] result3 = nm1.minimize();
        System.out.println("Метод деформируемого многогранника для функции Розенброка: " + result3[0] + "   " + result3[1]);

        NelderMead nm2 = new NelderMead(himmelblau, p1, p2, p3, 1000);
        double[] result4 = nm2.minimize();
        System.out.println("Метод деформируемого многогранника для функции Химмельблау: " + result4[0] + "   " + result4[1]);

        ConjugateGradient cg1 = new ConjugateGradient(rosenbrock, new Point(0, 1), 1e-5);
        double[] result5 = cg1.minimize();
        System.out.println("Метод сопряженных градиентов для функции Розенброка: " + result5[0] + "   " + result5[1]);

        ConjugateGradient cg2 = new ConjugateGradient(himmelblau, new Point(4, -2), 1e-5);
        double[] result6 = cg2.minimize();
        System.out.println("Метод сопряженных градиентов для функции Химмельблау: " + result6[0] + "   " + result6[1]);

        BFGS qn1 = new BFGS(rosenbrock, new Point(34, 34), 1e-6);
        double[] result7 = qn1.minimize();
        System.out.println("Квазиньютоновский метод для функции Розенброка: " + result7[0] + "   " + result7[1]);

        BFGS qn2 = new BFGS(himmelblau, new Point(-20, -100), 1e-6);
        double[] result8 = qn2.minimize();
        System.out.println("Квазиньютоновский метод для функции Химмельблау: " + result8[0] + "   " + result8[1]);


    }
}
