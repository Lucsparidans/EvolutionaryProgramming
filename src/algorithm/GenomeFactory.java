package algorithm;


import java.util.ArrayList;

public interface GenomeFactory {
    ArrayList<Genotype<?>> generatePopulation(int populationSize);
    Genotype<?> generateIndividual();
}
