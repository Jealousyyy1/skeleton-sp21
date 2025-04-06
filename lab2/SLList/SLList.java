package SLList;

import jh61b.junit.In;

public class SLList {
    private static class IntNode {
        public int item;
        public IntNode next;

        public IntNode(int i, IntNode n) {
            item = i;
            next = n;
        }
    }

    private IntNode sentinel;
    private int size;

    public SLList() {
        sentinel = new IntNode(0, null);
        size = 0;
    }

    public SLList(int x) {
        sentinel = new IntNode(0, new IntNode(x, null));
        size = 1;
    }

    public void addFirst(int x) {
        sentinel.next = new IntNode(x, sentinel.next);
        size += 1;
    }

    public int getFirst() {
        return sentinel.next.item;
    }

    // size() method w/ O(1) complexity.
    public int size() {
        return size;
    }

    public void addEnd(int x) {

        IntNode p = sentinel;
        while (p.next != null) {
            p = p.next;
        }
        p.next = new IntNode(x, null);
        size += 1;
    }

    private static String toString(IntNode p) {
        if (p.next == null) {
            return String.valueOf(p.item);
        } else {
            return p.item + " --> " + toString(p.next);
        }
    }

    @Override
    public String toString() {
        return toString(sentinel.next);
    }

    public static void main(String[] args) {
        SLList lst = new SLList();
        lst.addEnd(15);
        lst.addFirst(10);
        lst.addEnd(12);
        System.out.println(lst.toString());
        System.out.println(lst.size());
    }
}