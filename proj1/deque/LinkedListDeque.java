package deque;

import net.sf.saxon.om.Item;

public class LinkedListDeque {
    private static class ItemNode {
        public int item;
        public ItemNode prev, next;
        public ItemNode(int i, ItemNode p, ItemNode n) {
            this.item = i;
            this.prev = p;
            this.next = n;
        }
    }

    private ItemNode first;
    private int size;

    /** create an empty LLDeque. */
    public LinkedListDeque() {
        first = new ItemNode(0, null, null); // arbitrary i.
        first.next = first;
        first.prev = first;
        size = 0;
    }

    /** create an LLDeque with size 1. */
    public LinkedListDeque(int i) {
        first = new ItemNode(0, new ItemNode(i, first, first), null);
        first.next = first.prev;
        size = 1;
    }

    public int getFirst() {
        return first.next.item;
    }

    public int getLast() {
        return first.prev.item;
    }

    public void addFirst(int i) {
        ItemNode temp = first.next;
        first.next = new ItemNode(i, first, temp);
        first.next.next.prev = first.next;
        size += 1;
    }

    public void addLast(int i) {
        ItemNode temp = first.prev;
        first.prev = new ItemNode(i, temp, first);
        first.prev.prev.next = first.prev;
        size += 1;
    }

    private String toString(ItemNode items) {
        if (items.next == null) {
            return String.valueOf(items.item);
        } else {
            return items.item + " <--> " + toString(items.next);
        }
    }

    /** get a "visualized LinkedListDeque. "*/
    public String toString() {
        return toString(first.next);
    }

    public int size() {
        return size;
    }
}