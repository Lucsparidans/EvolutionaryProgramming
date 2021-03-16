package algorithm;

import java.util.ArrayList;

public interface Genotype<T>{
    double getFitness();
    Genotype<T> crossover(Genotype<?> genotype);
    void mutate(float mutationRate);
    T getData();

    enum CrossoverType{
        SinglePoint,
        MultiPoint,
        Uniform;
    }

    enum MutationMethod{
        Bitflip
    }
}
