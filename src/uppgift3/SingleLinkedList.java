package uppgift3;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SingleLinkedList<E> {
    private Node<E> head;
    private int size;

    public SingleLinkedList() {
        head = null;
        size = 0;
    }

    public void add(int index, E item) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        } else if (index == 0) // front
        {
            // new node, head as next
            head = new Node<E>(item, head);
        } else if (index == size) // back
        {
            // new node, no next
            Node<E> node = getNode(index - 1);
            node.next = new Node<E>(item, null);
        } else // middle
        {
            Node<E> node = getNode(index - 1);
            node.next = new Node<E>(item, node.next);
        }
        size++;
    }

    public void add(E item)
    {
        addBack(item);
    }

    // originally add last
    public void addBack(E item) {
        add(size, item);
    }

    // originally addFirst
    public void addFront(E item) {
        add(0, item);
    }

    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        Node<E> node = getNode(index);
        return node.data;
    }

    //Returnerar null om noden saknas
    private Node<E> getNode(int index) {
        if (index == 0) // first
            return head;

        // middle
        Node<E> node = head;
        for (int i = 0; i < index && node != null; i++) {
            node = node.next;
        }
        return node;
    }

    public E remove(int index) {
        E ret = null;
        if (index < 0 || index > size - 1)
            throw new IndexOutOfBoundsException(Integer.toString(index));
        else if (index == 0) // if head
        {
            ret = head.data;
            head = head.next; // head.next is null if head == tail
        } else // if middle or tail
        {
            // get the node before to be removed
            var before = getNode(index - 1);
            // get node to be removed
            var toRemove = before.next;
            ret = toRemove.data;

            // set before.next to the remove.next
            before.next = toRemove.next;
        }

        size--;
        return ret;
    }

    public Iterator<E> iterator() {
        return new Itr(head);
    }

    private static class Node<E> {
        private final E data;
        private Node<E> next;

        public Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }

    private class Itr implements Iterator<E> {
        Node<E> previousPrevious;
        Node<E> previous;
        Node<E> current;

        public Itr(Node<E> start) {
            previousPrevious = null;
            previous = null;
            current = start;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            if (current == null)
                throw new NoSuchElementException();
            E returnValue = current.data;
            if(previous != null)
                previousPrevious = previous;
            previous = current;
            current = current.next;
            return returnValue;
        }

        @Override
        public void remove() {
            if (previous == null)
                throw new IllegalStateException();
            else if (previous == head)
                head = previous.next;
            else
                previousPrevious.next = previous.next;

            // node in var previous was removed, reset
            previous = null;
            size--;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        Node<E> node = head;

        if(size != 0) {
            while (node != null) {
                sb.append(node.data);
                if (node.next != null) {
                    sb.append(", ");
                }
                node = node.next;
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
