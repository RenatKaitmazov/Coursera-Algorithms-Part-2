package week2.mst;
/**
 * A priority queue that allows to associate an item with an integer key.
 * Keys must in the range of [0, N).
 */
public final class IndexMinPriorityQueue<K extends Comparable<K>> {

    /*--------------------------------------------------------*/
    /* Helper methods                                         */
    /*--------------------------------------------------------*/

    /**
     * Represents a classic binary heap. It stores indices associated with keys.
     * Given a key's position we can quickly determine the key's index and the key itself.
     * <pre>
     * {@code
     * final int keyIndex = binaryHeap[keyPosition];
     * final K key = keys[keyIndex];
     * }
     * </pre>
     */
    private final int[] binaryHeap;
    /**
     * Stores indices associated with keys.
     * Given an index associated with a key we can quickly determine the key's position in {@link #binaryHeap}
     * via <code>keyIndices[keyIndex]</code>
     */
    private final int[] keyIndices;
    /**
     * Stores keys associated with their corresponding indices.
     */
    private final K[] keys;
    /**
     * Keeps track of the amount of keys in the queue.
     */
    private int size;

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    @SuppressWarnings("unchecked")
    public IndexMinPriorityQueue(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be positive: " + capacity);
        }
        // The capacity should be the same as the amount of vertices in a graph.
        binaryHeap = new int[capacity];
        keyIndices = new int[capacity];
        keys = (K[]) new Comparable[capacity];
        for (int i = 0; i < capacity; ++i) {
            binaryHeap[i] = -1;
        }
    }

    /*--------------------------------------------------------*/
    /* Overridden methods                                     */
    /*--------------------------------------------------------*/

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        final StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            final int valuePosition = binaryHeap[i];
            builder.append("(")
                    .append(valuePosition)
                    .append("-")
                    .append(keys[valuePosition])
                    .append("), ");
        }
        final int end = builder.length();
        final int start = end - 2;
        return builder.replace(start, end, "]").toString();
    }

    /*--------------------------------------------------------*/
    /* API                                                    */
    /*--------------------------------------------------------*/

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(int index) {
        checkIndexInRange(index);
        return keys[index] != null;
    }

    public int size() {
        return size;
    }

    public void insert(int index, K key) {
        checkIndexInRange(index);
        final int lastItemPosition = size++;
        binaryHeap[lastItemPosition] = index;
        keyIndices[index] = lastItemPosition;
        keys[index] = key;
        climb(lastItemPosition);
    }

    public void decreaseKey(int index, K newKey) {
        checkNotEmpty();
        checkIndexInRange(index);
        // If the queue does not contain the key or the new key is not less than the old one, then there is no
        // work to do.
        if (!contains(index) || keys[index].compareTo(newKey) <= 0) return;
        keys[index] = newKey;
        climb(keyIndices[index]);
    }

    public int delete() {
        checkNotEmpty();
        final int deletedIndex = binaryHeap[0];
        swap(0, --size);
        descend(0);
        binaryHeap[size] = -1;
        keys[deletedIndex] = null; // Avoid loitering.
        return deletedIndex;
    }

    public int minIndex() {
        checkNotEmpty();
        return binaryHeap[0];
    }

    public K minKey() {
        return keys[minIndex()];
    }

    /*--------------------------------------------------------*/
    /* Helper methods                                         */
    /*--------------------------------------------------------*/

    private void checkIndexInRange(int index) {
        final int capacity = binaryHeap.length;
        if (index < 0 || index >= capacity) {
            final String msg = "Invalid index. Must be in [0, " + capacity + ")";
            throw new IllegalArgumentException(msg);
        }
    }

    private void checkNotEmpty() {
        if (isEmpty()) {
            throw new IllegalArgumentException("The queue is empty");
        }
    }

    // Returns the final position of a key whose initial position is passed as an argument.
    private void climb(int position) {
        int index = position;
        // Stop when we reach the top of the heap.
        while (index > 0) {
            final int parentIndex = (index - 1) >> 1;
            // If the current key is not less than the parent key, we need to stop immediately.
            if (isNotLess(index, parentIndex)) break;
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    private void descend(int position) {
        int index = position;
        int childIndex = (index << 1) + 1;
        while (childIndex < size) {
            // If there is the right child and the right child is smaller than the left child, change the index
            // so that it will point to the smallest of the two children
            if (childIndex + 1 < size && isLess(childIndex + 1, childIndex)) ++childIndex;
            if (isNotLess(childIndex, index)) break;
            swap(childIndex, index);
            index = childIndex;
            childIndex = (index << 1) + 1;
        }
    }

    private boolean isNotLess(int i, int j) {
        return !isLess(i, j);
    }

    private boolean isLess(int i, int j) {
        final int iValue = binaryHeap[i];
        final int jValue = binaryHeap[j];
        return keys[iValue].compareTo(keys[jValue]) < 0;
    }

    private void swap(int i, int j) {
        // Knowing positions of items in the heap, we can retrieve the items' associated indices.
        final int iKeyIndex = binaryHeap[i];
        final int jKeyIndex = binaryHeap[j];
        // Swap indices in the heap.
        binaryHeap[i] = jKeyIndex;
        binaryHeap[j] = iKeyIndex;
        // Swap positions as well to reflect the changes in the heap.
        keyIndices[iKeyIndex] = j;
        keyIndices[jKeyIndex] = i;
    }
}