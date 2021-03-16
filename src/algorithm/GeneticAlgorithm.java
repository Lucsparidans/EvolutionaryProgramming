package algorithm;

import java.util.*;

import static algorithm.Genotype.*;
import static algorithm.Genotype.CrossoverType.*;
import static algorithm.Genotype.MutationMethod.*;

public abstract class GeneticAlgorithm {
    /**
     * Genetic Algorithm class
     * Parameters that have to be set:
     * 1. Population Size
     *      Number of things in generation
     * 2. Number of generations
     *      Number of iterations of (possible) improvement
     * 3. Mutation Rate
     *      Rate of mutation (expressed as decimals)
     * 4. Crossover Operator
     *      Method used when doing crossover
     * 5. Mutation Method
     *      Method used when doing mutation
     * 6. Elitism
     *      Either apply elitism or not
     */
    public static boolean Verbose = true;
    private static final int populationSize = 10;
    private static final float mutationRate = .1f;
    private static final int gens = 10;
    private static final boolean elitism = true;
    private static int copiedIndividuals = 1;
    public static final CrossoverType crossoverType = MultiPoint;
    public static final int numCrossover = 5;
    public static final MutationMethod mutationMethod = Bitflip;

    private static final Random RNG = new Random();

    public static Genotype<?> getBest(GenomeFactory genomeFactory) {
        // 1. Construct initial population
        ArrayList<Genotype<?>> population = genomeFactory.generatePopulation(populationSize);
        ArrayList<Genotype<?>> matingPool = new ArrayList<>();
        ArrayList<Genotype<?>> kBest = new ArrayList<>();

        if(!elitism)
            copiedIndividuals = 0;

        if (population == null)
            return null;
        for (int g = 0; g < gens; g++) {
            population.sort(Comparator.comparing(Genotype::getFitness,Comparator.reverseOrder()));
            if(Verbose) {
                System.out.printf("Generation: %d\n", g);
                population.forEach(System.out::println);
            }
            // 2. Selection for mating pool (with replacement)
            // 2.1 Copy k-best individuals
            for (int i = 0; i < copiedIndividuals; i++) {
                kBest.add(population.get(i));
            }

            // 2.2 Selection
            // TODO: - Boltzmann & Gibbs
            matingPool.addAll(selectRoulette(population)); // Roulette wheel selection
            population.clear();

            // 2.3 Copy the k best individuals to the new population (Elitism)
            population.addAll(kBest);
            kBest.clear();
        // 3. Crossover
            // Random selection from mating pool for crossover
            // Randomly select n pairs of individuals for crossover from the mating pool where n is the population size
            for (int i = 0; i < populationSize - copiedIndividuals; i++) {
                int[] ind = {RNG.nextInt(matingPool.size()), RNG.nextInt(matingPool.size())};
                population.add(matingPool.get(ind[0]).crossover(matingPool.get(ind[1])));
            }
        // 4. Mutation
            population.forEach(e->e.mutate(mutationRate));
        }
        population.sort(Comparator.comparing(Genotype::getFitness,Comparator.reverseOrder()));
        return population.get(0);
    }

    private static ArrayList<Genotype<?>> selectRoulette(ArrayList<Genotype<?>> population){
        int targetNum = populationSize - copiedIndividuals;
        ArrayList<Genotype<?>> matingCandidates = new ArrayList<>();
        population.sort(Comparator.comparing(Genotype::getFitness,Comparator.reverseOrder()));

        /* The following solution was inspired by:
        https://github.com/dwdyer/watchmaker/blob/master/framework/src/java/main/org/uncommons/watchmaker/framework/selection/RouletteWheelSelection.java */
        double[] scale = new double[population.size()];
        scale[0] = population.get(0).getFitness();
        for (int i = 1; i < scale.length; i++) {
            scale[i] = scale[i-1] + population.get(i).getFitness();
        }
        for (int i = 0; i < targetNum; i++) {
            // Select an individual from the population for the mating population
            double val = RNG.nextDouble() * scale[scale.length - 1]; // Find value within range of roulette wheel scale
            int ind = Arrays.binarySearch(scale,val);
            if(ind < 0){
                matingCandidates.add(population.get(Math.abs(ind + 1)));
            }else{
                matingCandidates.add(population.get(ind));
            }
        }
        return matingCandidates;
    }

    private static ArrayList<Genotype<?>> selectBoltzmannGibbs(ArrayList<Genotype<?>> population){
        return null;
    }
}
