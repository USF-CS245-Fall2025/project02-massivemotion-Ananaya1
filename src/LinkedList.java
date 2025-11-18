public class LinkedList<T> implements List<T> {
    private Node head = null;
    private Node tail = null;
    private int size = 0;

    // Inner Node class (no $ nonsense)
    private class Node {
        T data;
        Node next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    // Constructor
    public LinkedList() {}

    // Add element at a specific index
    @Override
    public void add(int index, T value) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        Node newNode = new Node(value);

        if (head == null) {
            // Empty list
            head = tail = newNode;
        } else if (index == 0) {
            // Insert at head
            newNode.next = head;
            head = newNode;
        } else if (index == size) {
            // Insert at tail
            tail.next = newNode;
            tail = newNode;
        } else {
            // Insert in the middle
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }

        size++;
    }

    // Add element at end
    @Override
    public boolean add(T value) {
        Node newNode = new Node(value);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
        return true;
    }

    // Get element at index
    @Override
    public T get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    // Remove element at index
    @Override
    public T remove(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        Node removedNode;
        if (index == 0) {
            removedNode = head;
            head = head.next;
            if (head == null) tail = null;
        } else {
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            removedNode = current.next;
            current.next = removedNode.next;
            if (removedNode == tail) {
                tail = current;
            }
        }
        size--;
        return removedNode.data;
    }

    // Return number of elements
    @Override
    public int size() {
        return size;
    }
}
