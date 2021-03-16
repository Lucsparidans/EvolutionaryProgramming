package tsp;

import algorithm.GeneticAlgorithm;
import algorithm.Genotype;

public class Client {
    public static void main(String[] args) {
        // 1. Encode problem somehow
        /**
         * PRESET MODEL:
         *  A   0  8     2
         *  B   8    0   3
         *  C   2    3     0
         *      A    B     C
         *      We set a big penalty on the diagonal to prevent cyclic behaviour since that never leads to the shortest
         *      route, and in the context of TSP is mostly meaningless.
         *      The same thing counts for the case where you would have to cities that are not directly connected, those
         *      need a distance of infinity as well.
         *
         *      Also, I made the assumption that for TSP we will only consider minimally connected graphs so it is not
         *      possible to have multiple edges between two nodes.
         */
        double[][] distances = {{0,8,2},{8,0,3},{2,3,0}};
        // 2. Use the GA to Solve
        TSPModel model = new TSPModel(distances);
        // 3. Execute GA best solution
        TSPGene best = (TSPGene) GeneticAlgorithm.getBest(new TSPFactory(model));
        // 4. Evaluate best solution
        int[][] bestI = best.getAsMatrix();
        System.out.printf("Best: %s\n",best);
        for (int[] ints : bestI) {
            for (int j = 0; j < bestI.length; j++) {
                System.out.print(ints[j]);
            }
            System.out.println();
        }

    }
}
