public interface List<T> {

    void add(int index, T element) throws IndexOutOfBoundsException;
    boolean add(T element);
    T get(int index) throws IndexOutOfBoundsException;
    T remove(int index) throws IndexOutOfBoundsException;
    int size();
}
