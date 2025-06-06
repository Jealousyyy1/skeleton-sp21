package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        AList<Integer> Ns = new AList<>();
        AList<Integer> ops = new AList<>();
        int[] tests = new int[]{1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000};
        AList<Double> times = new AList<>();
        for (int test : tests) {
            SLList lst = new SLList();
            Ns.addLast(test);
            while (lst.size() < Ns.getLast()) {   // create a SLList with size N.
                lst.addLast(0);
            }
            ops.addLast(10000);
            Stopwatch sw = new Stopwatch();
            for (int rep = 0; rep < ops.get(0); rep += 1) {    // call getLast ops times.
                lst.getLast();
            }
            times.addLast(sw.elapsedTime());
        }
        printTimingTable(Ns, times, ops);
    }

}
