public class DummyHeadLinkedList<T> implements List<T> {

    private class Node {
        T data;
        Node next;

        Node(T data) {
            this.data = data;
        }
    }

    private Node head; // dummy head
    private int size;

    public DummyHeadLinkedList() {
        head = new Node(null); // dummy head has no data
        size = 0;
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        Node prev = head;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }

        Node newNode = new Node(element);
        newNode.next = prev.next;
        prev.next = newNode;
        size++;
    }

    @Override
    public boolean add(T element) {
        add(size, element);
        return true;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        Node curr = head.next;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr.data;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        Node prev = head;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }

        Node toRemove = prev.next;
        prev.next = toRemove.next;
        size--;

        return toRemove.data;
    }

    @Override
    public int size() {
        return size;
    }
}
