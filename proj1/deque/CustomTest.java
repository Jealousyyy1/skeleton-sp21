package deque;

import org.junit.Test;
import static org.junit.Assert.*;

public class CustomTest {
    @Test
    public void getFirstTest() {
        LinkedListDeque lst1 = new LinkedListDeque();
        int expected = 0;
        int actual = lst1.getFirst();
        assertEquals(expected, actual);
    }

    @Test
    public void getFirstTest2() {
        LinkedListDeque lst1 = new LinkedListDeque(10);
        int expected = 10;
        int actual = lst1.getFirst();
        int actual2 = lst1.getLast();
        assertEquals(expected, actual);
        assertEquals(actual2, actual);
    }

    @Test
    public void addFirstTest() {
        LinkedListDeque lst1 = new LinkedListDeque(10);
        lst1.addFirst(5);
        lst1.addFirst(4);
        lst1.addFirst(3);
        lst1.addFirst(2);
        lst1.addFirst(1);
        int expected = 1;
        int actual = lst1.getFirst();
        int expectedSize = 6;
        int actual2 = lst1.size();
        assertEquals(expected, actual);
        assertEquals(expectedSize, actual2);
    }

}