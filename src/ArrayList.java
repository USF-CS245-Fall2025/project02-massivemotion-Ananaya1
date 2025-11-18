import java.util.Arrays;

public class ArrayList<T> implements List<T> {
    private T[] array;
    private int size;

    public ArrayList() {
        array = (T[]) new Object[8];  
        size = 0;
    }

    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        if (size >= array.length) {
            grow();
        }
        for (int i = size; i > index; i--) {
            array[i] = array[i - 1];
        }
        array[index] = element;
        size++;
    }

    public boolean add(T element) {
        if (size >= array.length) {
            grow();
        }
        array[size] = element;
        size++;
        return true;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        return array[index];
    }

    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        T removed = array[index];
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        array[size - 1] = null; 
        size--;
        return removed;
    }

    public int size() {
        return size;
    }

   
    private void grow() {
        T[] newArray = (T[]) new Object[array.length * 2];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }
}
