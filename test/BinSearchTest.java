import java.util.Arrays;

public class BinSearchTest {
    public static void main(String[] args) {
        double[] test = {1,2,3,4,5,6,7,8,9};
        int ind = Arrays.binarySearch(test,3.2);
        if(ind < 0){
            ind = Math.abs(ind+1);
            System.out.printf("(Insert) Index: %d",ind);
        }else{
            System.out.printf("Index: %d",ind);
        }
    }
}
