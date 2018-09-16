package week1.homework;

/**
 * @author Renat Kaitmazov
 */

final class IntQueue {

    /*--------------------------------------------------------*/
    /* Fields                                                 */
    /*--------------------------------------------------------*/

    private Node head;
    private Node tail;
    private int size;

    /*--------------------------------------------------------*/
    /* API                                                    */
    /*--------------------------------------------------------*/

    int size() {
        return size;
    }

    boolean isEmpty() {
        return head == null && tail == null;
    }

    int peek() {
        checkNotEmpty();
        return head.value;
    }

    void enqueue(int value) {
        final Node newNode = new Node(value);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        ++size;
    }

    int dequeue() {
        checkNotEmpty();
        final int value = head.value;
        head = head.next;
        if (head == null) {
            // The only item in the queue
            tail = null;
        }
        --size;
        return value;
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
        Node currentNode = head;
        while (currentNode != null) {
            final int value = currentNode.value;
            builder.append(value).append(", ");
            currentNode = currentNode.next;
        }
        final int endIndex = builder.length();
        final int startIndex = endIndex - 2;
        return builder.replace(startIndex, endIndex, "]").toString();
    }

    /*--------------------------------------------------------*/
    /* Helper methods                                         */
    /*--------------------------------------------------------*/

    private void checkNotEmpty() {
        if (isEmpty()) {
            throw new IllegalArgumentException("The queue is emtpy");
        }
    }

    /*--------------------------------------------------------*/
    /* Nested classes                                         */
    /*--------------------------------------------------------*/

    private static final class Node {

        Node next;
        int value;

        Node(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }
}
