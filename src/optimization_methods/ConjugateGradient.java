package optimization_methods;

import java.util.function.BiFunction;

public class ConjugateGradient implements BiFunctionOptimizationAlgorithm {
    private double epsilon;
    private BiFunction<Double, Double, Double> f;
    private Point x0;
    public ConjugateGradient(BiFunction<Double, Double, Double> f, Point x0, double epsilon) {
        this.f = f;
        this.epsilon = epsilon;
        this.x0 = x0;
    }
    @Override
    public double[] minimize() {
        double  a = 0,
                b = 10,
                iter = 1,
                x1,
                x2,
                alp,
                beta;
        Point anti_grad = Point.getGradient(f, x0, epsilon).multiply(-1);
        Point dir_prev = anti_grad;
        Point dir_cur;

        while (Point.getGradient(f, x0, epsilon).getNorm() > epsilon) {
            anti_grad = Point.getGradient(f, x0, epsilon).multiply(-1);
//            System.out.println("Антиградиент " + anti_grad.represent());
            // ортогонализация Грамма-Шмидта
            if (iter == 1) {
                //первый вектор ортогон-ть не нужно
                dir_cur = anti_grad;
            } else {
                //коэффициент для ортогонализации (x,y)/(y,y)
                beta = (anti_grad.skal(dir_prev)) / (dir_prev.skal(dir_prev));
//                System.out.println("коэф " + beta);
                //найдем направление спуска g = x - beta*y
                dir_cur = anti_grad.subtraction(dir_prev.multiply(beta));
//                System.out.println("Новое напрвление: " + dir_cur.represent());
            }
            do {
                double middle = (a + b) / 2;
                x1 = middle - epsilon / 3;
                x2 = middle + epsilon / 3;
                double i1 = f.apply(x0.sum(dir_cur.multiply(x1)).getX(), x0.sum(dir_cur.multiply(x1)).getY());
                double i2 = f.apply(x0.sum(dir_cur.multiply(x2)).getX(), x0.sum(dir_cur.multiply(x2)).getY());
                if (i1 < i2) {
                    b = x2;
                } else {
                    a = x1;
                }
//                System.out.println(Math.abs(b - a));
            } while (Math.abs(b - a) > epsilon);
            alp = (a + b) / 2;
            x0 = x0.sum(dir_cur.multiply(alp));
            dir_prev = dir_cur;
//            System.out.println("Точка " + x0.getX() + "     " + x0.getY());
            iter++;

        }
//        System.out.println(iter);
        return new double[]{x0.getX(), x0.getY()};
    }


}
