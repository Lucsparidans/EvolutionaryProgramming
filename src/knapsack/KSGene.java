package knapsack;

import algorithm.BinString;
import algorithm.GeneticAlgorithm;
import algorithm.Genotype;

import java.util.Arrays;

public class KSGene extends BinString implements Genotype<String>{

    private final KSModel model;
    private final double[] Weights;
    private final double[] Values;

    public KSGene(String genotype, KSModel model) {
        super(genotype);
        this.model = model;
        this.Weights = model.WEIGHTS;
        this.Values = model.VALUES;
    }

    @Override
    public double getFitness() {
        // Test if valid solution
        double totalWeight = 0;
        double totalValue = 0;
        for (int i = 0; i < genotype.length; i++) {
            if(genotype[i] != 0 && genotype[i] != 1){
                return Double.NEGATIVE_INFINITY; // Invalid bit-string so low fitness
            }
            totalWeight += genotype[i] * Weights[i];
            totalValue += genotype[i] * Values[i];
        }
        if(totalWeight > model.MAX_W)
            return 0;
        return totalValue;
    }

    @Override
    public Genotype<String> crossover(Genotype<?> genotype) {
        switch (GeneticAlgorithm.crossoverType) {
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
                return new KSGene(new String(newString), model);
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
                return new KSGene(new String(newString),model);
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
                return new KSGene(new String(newString),model);
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

    @Override
    public String toString() {
        return "KSGene{" +
                "genotype=" + Arrays.toString(genotype) +
                ", fitness=" + getFitness() + "}";
    }
}
