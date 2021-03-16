package tsp;

import algorithm.GenomeFactory;
import algorithm.Genotype;

import java.util.ArrayList;

public class TSPFactory implements GenomeFactory {
    private TSPModel Model;

    public TSPFactory(TSPModel model) {
        Model = model;
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
        int size = (int) Math.pow(Model.N_CITIES,2);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append((Math.random() < .5 ? "1" : "0"));
        }
        return new TSPGene(sb.toString(),Model);
    }
}
