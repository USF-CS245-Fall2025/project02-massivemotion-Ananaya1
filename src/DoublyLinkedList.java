public class DoublyLinkedList<T> implements List<T> {
    public class Node{ //Node Class
         T data;
        Node next;
        Node prev;

        Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }
    
    private Node head;
    private Node tail;
    private int size;

    public DoublyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }
  public void add(int index, T element) {
    if (index < 0 || index > size) { // Valid index checking
        throw new IndexOutOfBoundsException();
    }

    Node newNode = new Node(element);

    if (head == null) { // Empty list
        head = tail = newNode;
    } else if (index == 0) { // Insert at head
        newNode.next = head;
        head.prev = newNode;
        head = newNode;
    } else if (index == size) { // Insert at tail
        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
    } else { // Insert at some index
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        newNode.next = current;
        newNode.prev = current.prev;
        current.prev.next = newNode;
        current.prev = newNode;
    }
    size++;
}

   
    public boolean add (T element)
    {
        Node newNode = new Node(element); 
        if (head == null) {  //Empty List
            head = tail = newNode;
            return true;
        } else { 
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
        return true;
    }
    
    public T get (int index)
    {
        if (index < 0 || index >= size) {
        throw new IndexOutOfBoundsException();
    }
        int i=0;
          Node current = head;
        while(i!=index) // Traversing through the Doubly Linked List
            {
                current=current.next;
                i++;
            }
            
        
        return current.data;
    }
   public T remove(int index) {
    if (index < 0 || index >= size) {
        throw new IndexOutOfBoundsException();
    }

    Node current = head;
    for (int i = 0; i < index; i++) {
        current = current.next;
    }

    // Removing head
    if (current.prev == null) {
        head = current.next;
        if (head != null) {
            head.prev = null;
        }
    } 
    // Removing tail
    else if (current.next == null) {
        tail = current.prev;
        tail.next = null;
    } 
    // Removing index
    else {
        current.prev.next = current.next;
        current.next.prev = current.prev;
    }

    size--;
    return current.data;
}

    public int size ()
    {   return size;
    }

}