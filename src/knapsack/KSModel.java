package knapsack;

import java.util.Random;

public class KSModel {

    public final Random RNG = new Random();
    public final int MAX_W = 10;
    public final int N_ITEMS;
    public final double[] WEIGHTS;
    public final double[] VALUES;

    public KSModel(double[] WEIGHTS, double[] VALUES) {
        this.WEIGHTS = WEIGHTS;
        this.VALUES = VALUES;
        N_ITEMS = WEIGHTS.length;
    }
}
