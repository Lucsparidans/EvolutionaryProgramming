package knapsack;

import algorithm.GeneticAlgorithm;

public class Client {
    public static void main(String[] args) {
        // 1. Encode problem somehow
        double[] weights = {5,6,4,9,3,1};
        double[] values = {65,54,12,95,21,32};
        KSModel model = new KSModel(weights,values);
        // 2. Use the GA to Solve
        KSGene best = (KSGene) GeneticAlgorithm.getBest(new KSFactory(model));
        // 3. Execute GA best solution
        System.out.printf("Best solution: %s",best);
        // 4. Evaluate best solution
    }
}
