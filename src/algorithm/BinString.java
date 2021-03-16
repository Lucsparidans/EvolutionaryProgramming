package algorithm;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class BinString implements Genotype<String>{
    protected final int[] genotype;

    public BinString(String genotype){
        this.genotype = genotype.chars().map(Character::getNumericValue).toArray();
    }

    @Override
    public String getData() {
        return Arrays.stream(genotype).mapToObj(String::valueOf).collect(Collectors.joining());
    }
}
