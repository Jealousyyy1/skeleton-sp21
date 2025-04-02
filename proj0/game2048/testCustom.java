package game2048;

public class testCustom {
    public static void main(String[] args) {
        int[] numbers = new int[]{2, 2, 0, 2, 0, 0, 10, 0, 11};
        System.out.println(Model.numberNonZero(numbers));
    }
}
