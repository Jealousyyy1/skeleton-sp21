package deque;

import net.sf.saxon.om.Item;

import java.util.Iterator;

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
    private int index = 0;

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

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        ItemNode result = sentinel.next;
        sentinel.next.prev = new ItemNode(null, null, null);
        ItemNode temp = sentinel.next.next;
        sentinel.next = temp;
        temp.prev = sentinel;
        size -= 1;
        return result.item;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        ItemNode result = sentinel.prev;
        sentinel.prev.next = new ItemNode(null, null, null); // make all references of this node to others than sth in the list.
        ItemNode temp = sentinel.prev.prev;
        sentinel.prev = temp;
        temp.next = sentinel;
        size -= 1;
        return result.item;
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

    public T get(int i) {
        if (i > size || i < 0) {
            return null;
        } else {
            int count = 0;
            ItemNode p = sentinel.next;
            while (count < i) {
                p = p.next;
                count += 1;
            }
            return p.item;
        }
    }

    /**
     * public interface Iterator<T> {
     *  boolean hasNext();
        T next();
     }
     In order to create an iterator, first implement hasNext() and next() method.
     */
    public boolean hasNext() {
        return index < size;
    }

    public T next() {
        if (!hasNext()) {
            throw new IndexOutOfBoundsException();
        }
        index += 1;
        return get(index);
    }

    public Iterator<T> iterator() {
        return new
    }
}