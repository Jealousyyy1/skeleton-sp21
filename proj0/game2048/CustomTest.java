package game2048;

import java.util.Arrays;

public class CustomTest {
    public static void main(String[] args) {
        int[] numbers = new int[]{4, 4, 4, 4};
        System.out.println(Arrays.toString(Model.sameAdjPairs(numbers)));
    }
}
