public class TSPTest {
    public static void main(String[] args) {
        double[] genotype = {1,0,0,1,1,0,1,0,0};
        double[][] mat = listToMatrix(genotype);
        System.out.println(mat);
    }
    private static double[][] listToMatrix(double[] genotype){
        int dim = (int) Math.sqrt(genotype.length);
        double[][] mat = new double[dim][dim];
        for (int i = 0; i < dim; i++) {
            System.arraycopy(genotype, i * dim, mat[i], 0, dim);
        }
        return mat;
    }
}
