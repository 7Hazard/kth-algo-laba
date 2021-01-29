package uppgift2;

public class SingleLinkedList<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public SingleLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public void add(int index, E item) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        } 
        else if (index == 0) // front
        {
            addFront(item);
        } 
        else if(index == size) // back
        {
            addBack(item);
        }
        else // middle
        {
            Node<E> node = getNode(index - 1);
            node.next = new Node<E>(item, node.next);
            size++;
        }
    }

    // originally add last
    public void addBack(E item) {
        // new node, no next
        var newnode = new Node<>(item, null);

        // if empty list, set tail
        if(size == 0)
            head = newnode;
        else {
            // before changing tail, set tail.next
            tail.next = newnode;
        }
        
        // change tail
        tail = newnode;
        
        size++;
    }

    // originally addFirst
    public void addFront(E item) {
        // new node, head as next
        head = new Node<E>(item, head);
        
        // if empty, head is also tail
        if(size == 0)
            tail = head;
        
        size++;
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
        if(index == 0) // first
            return head;
        if(index == size-1) // last
            return tail;
        
        // middle
        Node<E> node = head;
        for (int i = 0; i < index && node != null; i++) {
            node = node.next;
        }
        return node;
    }

    public E remove(int index) {
        E ret = null;
        if(index < 0 || index > size-1)
            throw new IndexOutOfBoundsException(Integer.toString(index));
        else if(index == 0) // if head
        {
            if(head == tail)
                tail = null;
            head = head.next; // head.next is null if head == tail
        }
        else // if middle or tail
        {
            // get the node before to be removed
            var before = getNode(index-1);
            // get node to be removed
            var toRemove = before.next;
            ret = toRemove.data;

            if(toRemove == tail)
                tail = before;
            
            // set before.next to the remove.next
            before.next = toRemove.next;
        }

        size--;
        return ret;
    }

    private static class Node<E> {
        private final E data;
        private Node<E> next;

        public Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }
}
