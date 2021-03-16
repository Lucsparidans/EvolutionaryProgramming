package tsp;

import java.util.Random;

public class TSPModel {
    public final Random RNG = new Random();
    public final int N_CITIES;
    public final double[][] DISTANCE;
    public final double TOT_DST;

    public TSPModel(double[][] DISTANCE) {
        this.DISTANCE = DISTANCE;
        N_CITIES = DISTANCE.length;
        int tmp = 0;
        for (int i = 0; i < DISTANCE.length; i++) {
            for (int j = 0; j < DISTANCE.length; j++) {
                if(i!=j)
                    tmp+=DISTANCE[i][j];
            }
        }
        TOT_DST = tmp;
    }

}
