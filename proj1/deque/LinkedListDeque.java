package deque;

import net.sf.saxon.om.Item;

public class LinkedListDeque<T> {
    private class ItemNode {
        public T item;
        public ItemNode prev, next;
        public ItemNode(T i, ItemNode p, ItemNode n) {
            this.item = i;
            this.prev = p;
            this.next = n;
        }
    }

    private ItemNode sentinel;
    private int size;

    /** create an empty LLDeque. */
    public LinkedListDeque() {
        sentinel = new ItemNode(null, null, null); // arbitrary i.
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /** create an LLDeque with size 1. */
    public LinkedListDeque(T item) {
        sentinel = new ItemNode(null, new ItemNode(item, null, null), null);
        sentinel.next = sentinel.prev;
        size = 1;
    }

    public T getFirst() {
        return sentinel.next.item;
    }

    public T getLast() {
        return sentinel.prev.item;
    }

    public void addFirst(T item) {
        ItemNode temp = sentinel.next;
        sentinel.next = new ItemNode(item, sentinel, temp);
        temp.prev = sentinel.next;
        size += 1;
    }

    public void addLast(T item) {
        ItemNode temp = sentinel.prev;
        sentinel.prev = new ItemNode(item, temp, sentinel);
        temp.next = sentinel.prev;
        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }



    private void printDeque(ItemNode items) {
        if (items.next == null) {
            System.out.println(items.item);
            System.out.println();
        } else {
            System.out.print(items.item + " ");
            printDeque(items.next);
        }
    }

    public void printDeque() {
        printDeque(sentinel.next);
    }

    public int size() {
        return size;
    }
}