package game2048;

import java.util.Arrays;

public class testCustom {
    public static void main(String[] args) {
        int[] numbers = new int[]{2, 2, 0, 2, 0, 2, 0, 2, 0, 2};
        System.out.println(Model.numberNonZero(numbers));
        System.out.println(Arrays.toString(Model.sameAdjPairs(numbers)));
    }
}
