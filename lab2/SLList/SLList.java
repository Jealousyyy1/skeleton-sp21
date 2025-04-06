package SLList;

import jh61b.junit.In;

public class SLList {
    public static class IntNode {
        public int item;
        public IntNode next;

        public IntNode(int i, IntNode n) {
            item = i;
            next = n;
        }

        public String toString() {
            if (next == null) {
                return String.valueOf(item);
            } else {
                return item + " --> " + next.toString();
            }
        }
    }

    public IntNode first;

    public SLList(int x) {
        first = new IntNode(x, null);
    }

    public void addFirst(int x) {
        first = new IntNode(x, first);
    }

    public int getFirst() {
        return first.item;
    }

    public int size() {
        IntNode p = first;
        int size = 0;
        while (p != null) {
            p = p.next;
            size += 1;
        }
        return size;
    }

    public void addEnd(int x) {
        IntNode p = first;
        while (p.next != null) {
            p = p.next;
        }
        p.next = new IntNode(x, null);
    }

    @Override
    public String toString() {
        if (first == null) {
            return "Empty List";
        } else {
            return first.toString();
        }
    }

    public static void main(String[] args) {
        SLList lst = new SLList(6);
        lst.addFirst(10);
        lst.addEnd(12);
        System.out.println(lst.toString());
    }


}