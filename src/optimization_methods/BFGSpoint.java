//package optimization_methods;
//
//import Jama.Matrix;
//
//public class BFGSpoint extends Matrix {
//    /**
//     * Класс используется для описания точек приближения, градиента
//     * и результата произведения матрицы на градиент - вектора.
//     */
//
//    public BFGSpoint(double... args) {
//        super(args, args.length);
//    }
//
//    public BFGSpoint(int size) {
//        super(size, 1);
//    }
//
//    public BFGSpoint(BFGSpoint point) {
//        this(point.getPointArray());
//    }
//
//    public BFGSpoint(Matrix matrix) {
//        super(matrix.getArray());
//    }
//
//    public Double get(int i) {
//        return (Double) get(i, 0);
//    }
//
//    public int size() {
//        return getRowDimension();
//    }
//
//    public void set(int position, double arg) {
//        set(position, 0, arg);
//    }
//
//    public void print() {
//        System.out.println();
//        for (int i = 0; i < getRowDimension(); i++)
//            System.out.print("  " + get(i));
//        System.out.println();
//    }
//
//    public double[] getPointArray() {
//        double[] args = new double[getRowDimension()];
//        for (int i = 0; i < getRowDimension(); i++)
//            args[i] = get(i);
//        return args;
//    }
//}
