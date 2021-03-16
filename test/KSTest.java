import algorithm.Genotype;
import knapsack.KSFactory;
import knapsack.KSModel;

import java.util.List;

public class KSTest {
    public static void main(String[] args) {
        double[] weights = {5,6,4,9,3,1};
        double[] values = {65,54,12,95,21,32};
        KSModel model = new KSModel(weights,values);
        KSFactory factory = new KSFactory(model);
        List<Genotype<?>> list = factory.generatePopulation(6);
        list.forEach(System.out::println);
    }
}
