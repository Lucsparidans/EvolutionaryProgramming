package knapsack;

import algorithm.GenomeFactory;
import algorithm.Genotype;

import java.util.ArrayList;

public class KSFactory implements GenomeFactory {
    private final KSModel MODEL;
    public KSFactory(KSModel model) {
        MODEL = model;
    }

    @Override
    public ArrayList<Genotype<?>> generatePopulation(int populationSize) {
        ArrayList<Genotype<?>> pop = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            pop.add(generateIndividual());
        }
        return pop;
    }

    @Override
    public Genotype<?> generateIndividual() {
        int size = MODEL.N_ITEMS;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append((Math.random() < .5 ? "1" : "0"));
        }
        return new KSGene(sb.toString(),MODEL);
    }
}
