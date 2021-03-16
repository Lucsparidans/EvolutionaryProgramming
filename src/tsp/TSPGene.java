package tsp;

import algorithm.BinString;
import algorithm.GeneticAlgorithm;
import algorithm.Genotype;

import java.util.Arrays;

public class TSPGene extends BinString {

    private final TSPModel model;
    private final double[][] distance;

    public TSPGene(String genotype, TSPModel model) {
        super(genotype);
        this.model = model;
        distance = model.DISTANCE;
    }

    @Override
    public double getFitness() {
        // Check if solution is valid
        int[][] genotype = listToMatrix(super.genotype);
        int dim = genotype.length;
        int[] colSum = new int[dim];
        int[] rowSum = new int[dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                rowSum[i] += genotype[i][j];
                colSum[j] += genotype[j][i];
            }
        }
        for (int i = 0; i < dim; i++) {
            if(colSum[i] == 0 || rowSum[0] == 0){
                return Double.NEGATIVE_INFINITY; // Invalid solution
            }
            if(colSum[0] > 1 || rowSum[0] > 1){
                return Double.NEGATIVE_INFINITY; // Invalid solution
            }
            if(colSum[i] != rowSum[i]){
                return Double.NEGATIVE_INFINITY; // Invalid solution
            }
        }
        double totalDst = 0;
        for (int i = 0; i < distance.length; i++) {
            for (int j = 0; j < distance.length; j++) {
                if(i == j && genotype[i][j] == 1){
                    return Double.NEGATIVE_INFINITY;
                }
                totalDst += genotype[i][j] * distance[i][j];
            }
        }
        return model.TOT_DST - totalDst; // Smaller routes are better (Higher fitness)
    }

    @Override
    public Genotype<String> crossover(Genotype<?> genotype) {
        switch (GeneticAlgorithm.crossoverType){
            case SinglePoint: {
                int ind = (int) (Math.random() * super.genotype.length);
                int choice = model.RNG.nextInt(2);
                char[] newString;
                if (choice == 0) {
                    newString = super.getData().toCharArray();
                    newString[ind] = genotype.getData().toString().toCharArray()[ind];
                } else {
                    newString = genotype.getData().toString().toCharArray();
                    newString[ind] = Character.forDigit(super.genotype[ind], 2);
                }
                return new TSPGene(new String(newString), model);
            }
            case MultiPoint: {
                int numCrossover = GeneticAlgorithm.numCrossover;
                int choice = model.RNG.nextInt(2);
                char[] newString;
                if (choice == 0) {
                    newString = super.getData().toCharArray();
                    for (int i = 0; i < numCrossover; i++) {
                        int ind = (int) (Math.random() * super.genotype.length);
                        newString[ind] = genotype.getData().toString().toCharArray()[ind];
                    }
                }else {
                    newString = genotype.getData().toString().toCharArray();
                    for (int i = 0; i < numCrossover; i++) {
                        int ind = (int) (Math.random() * super.genotype.length);
                        newString[ind] = Character.forDigit(super.genotype[ind], 2);
                    }
                }
                return new TSPGene(new String(newString),model);
            }
            case Uniform: {
                char[] newString = new char[super.genotype.length];
                for (int i = 0; i < super.genotype.length; i++) {
                    if(model.RNG.nextBoolean()){
                        newString[i] = Character.forDigit(super.genotype[i],2);
                    }else{
                        newString[i] = genotype.getData().toString().toCharArray()[i];
                    }
                }
                return new TSPGene(new String(newString),model);
            }
        }
        return this;
    }

    @Override
    public void mutate(float mutationRate) {
        for (int i = 0; i < genotype.length; i++) {
            if (model.RNG.nextDouble() < mutationRate) {
                // TODO: 15/03/2021 Implement various mutation methods
                if(genotype[i] == 0)
                    genotype[i] = 1;
                else
                    genotype[i] = 0;
            }
        }
    }

    private double[][] listToMatrix(double[] genotype){
        int dim = (int) Math.sqrt(genotype.length);
        double[][] mat = new double[dim][dim];
        for (int i = 0; i < dim; i++) {
            System.arraycopy(genotype, i * dim, mat[i], 0, dim);
        }
        return mat;
    }
    private int[][] listToMatrix(int[] genotype){
        int dim = (int) Math.sqrt(genotype.length);
        int[][] mat = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            System.arraycopy(genotype, i * dim, mat[i], 0, dim);
        }
        return mat;
    }

    public int[][] getAsMatrix(){
        return listToMatrix(genotype);
    }

    @Override
    public String toString() {
        return "TSPGene{" +
                "genotype=" + Arrays.toString(genotype) +
                ", Fitness=" + getFitness() +
                '}';
    }
}
