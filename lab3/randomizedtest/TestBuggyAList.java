package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testBuggyGetLast() {
        BuggyAList<Integer> bugList = new BuggyAList<>();
        bugList.addLast(2);
        bugList.addLast(4);

        AListNoResizing<Integer> goodList = new AListNoResizing<>();
        goodList.addLast(2);
        goodList.addLast(4);

        int expected = goodList.getLast();
        int actual = bugList.getLast();

        assertEquals(expected, actual);
    }

    @Test
    public void testBuggyRemove() {
        BuggyAList<Integer> bugList = new BuggyAList<>();
        bugList.addLast(2);
        bugList.addLast(4);
        bugList.addLast(6);

        AListNoResizing<Integer> goodList = new AListNoResizing<>();
        goodList.addLast(2);
        goodList.addLast(4);
        goodList.addLast(6);

        int expected = goodList.removeLast();
        int actual = bugList.removeLast();
        assertEquals(expected, actual);

        int expected2 = goodList.removeLast();
        int actual2 = bugList.removeLast();
        assertEquals(expected2, actual2);

        int expected3 = goodList.removeLast();
        int actual3 = bugList.removeLast();
        assertEquals(expected3, actual3);

    }

    @Test
    public void testBuggyResize() {
        BuggyAList<Integer> bugList = new BuggyAList<>();
        for (int i = 0; i < 1000; i += 1) {
            bugList.addLast(i);
        }

        AListNoResizing<Integer> goodList = new AListNoResizing<>();
        for (int i = 0; i < 1000; i += 1) {
            goodList.addLast(i);
        }

        int expected = goodList.get(999);
        int actual = bugList.get(999);

        int expected_size = goodList.size();
        int actual_size = bugList.size();

        assertEquals(expected, actual);
        assertEquals(expected_size, actual_size);
    }

    @Test
    public void randomTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> Bug = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                Bug.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int size2 = Bug.size();
                assertEquals(size, size2);
            } else if (operationNumber == 2 && L.size() > 0 && Bug.size() > 0) {     // removeLast and getLast methods can only function when there is a last element.
                // removeLast
                int x = L.removeLast();
                int y = Bug.removeLast();
            } else if (operationNumber == 3 && L.size() > 0 && Bug.size() > 0) {
                // getLast
                int last = L.getLast();
                int last2 = Bug.getLast();
                assertEquals(last, last2);
            }
        }
    }

}
