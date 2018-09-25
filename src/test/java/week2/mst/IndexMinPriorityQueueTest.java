package week2.mst;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public class IndexMinPriorityQueueTest {

    private final IndexMinPriorityQueue<String> emptyQueue = new IndexMinPriorityQueue<>(1);
    private IndexMinPriorityQueue<String> queue;

    @Before
    public void setUp() {
        final String[] fruits = fruits();
        final int size = fruits.length;
        queue = new IndexMinPriorityQueue<>(size);
        for (int i = 0; i < 7; ++i) {
            queue.insert(i, fruits[i]);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void nonPositiveCapacityThrowsTest() {
        new IndexMinPriorityQueue<String>(0);
    }

    @Test
    public void notEmptyAfterInsertionTest() {
        assertFalse(queue.isEmpty());
    }

    @Test
    public void insertTest() {
        assertEquals(expectedQueueRepresentation(), queue.toString());
    }

    @Test
    public void sizeAfterInsertionTest() {
        assertEquals(7, queue.size());
    }

    @Test
    public void minIndexTest() {
        assertEquals(6, queue.minIndex());
    }

    @Test
    public void minKeyTest() {
        assertEquals("Apple", queue.minKey());
    }

    @Test
    public void containsIndexTest() {
        assertTrue(queue.contains(0));
        assertTrue(queue.contains(1));
        assertTrue(queue.contains(2));
        assertTrue(queue.contains(3));
        assertTrue(queue.contains(4));
        assertTrue(queue.contains(5));
        assertTrue(queue.contains(6));
    }

    @Test
    public void absentIndexTest() {
        assertFalse(queue.contains(7));
        assertFalse(queue.contains(8));
        assertFalse(queue.contains(9));
    }

    @Test(expected = IllegalArgumentException.class)
    public void containsThrowsIfIndexIsOutOfBoundsTest() {
        queue.contains(100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void decreaseKeyThrowsIfIndexIsOutBoundsTest() {
        queue.decreaseKey(100, "New Key");
    }

    @Test
    public void doesNotDecreaseIfIndexIsAbsentOrIsNotLessThanOldKeyTest() {
        queue.decreaseKey(9, "Cherry");
        assertEquals(expectedQueueRepresentation(), queue.toString());
        queue.decreaseKey(6, "Cherry");
    }

    @Test
    public void decreaseKeyTest() {
        queue.decreaseKey(5, "Cherry");
        assertEquals(
                "[(6-Apple), (2-Pear), (4-Banana), (0-Watermelon), (3-Plum), (1-Pineapple), (5-Cherry)]",
                queue.toString()
        );
        queue.decreaseKey(5, "Avocado");
        assertEquals(
                "[(6-Apple), (2-Pear), (5-Avocado), (0-Watermelon), (3-Plum), (1-Pineapple), (4-Banana)]",
                queue.toString()
        );
    }

    @Test
    public void emptyQueueRepresentationTest() {
        assertEquals("[]", emptyQueue.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void minIndexOnEmptyQueueThrowsTest() {
        emptyQueue.minIndex();
    }

    @Test(expected = IllegalArgumentException.class)
    public void minKeyOnEmptyQueueThrowsTest() {
        emptyQueue.minKey();
    }

    @Test(expected = IllegalArgumentException.class)
    public void containsOnEmptyQueueThrowsTest() {
        emptyQueue.contains(10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void decreaseKeyOnEmptyQueueThrowsTest() {
        emptyQueue.decreaseKey(0, "Key");
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteOnEmptyQueueThrowsTest() {
        emptyQueue.delete();
    }

    @Test
    public void deleteMinTest() {
        assertEquals(6, queue.delete());
        assertEquals(4, queue.delete());
        assertEquals(5, queue.delete());
        assertEquals(2, queue.delete());
        assertEquals(1, queue.delete());
        assertEquals(3, queue.delete());
        assertEquals(0, queue.delete());
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    private String expectedQueueRepresentation() {
        return "[(6-Apple), (2-Pear), (4-Banana), (0-Watermelon), (3-Plum), (1-Pineapple), (5-Orange)]";
    }

    private String[] fruits() {
        final String[] fruits = new String[10];
        fruits[0] = "Watermelon";
        fruits[1] = "Pineapple";
        fruits[2] = "Pear";
        fruits[3] = "Plum";
        fruits[4] = "Banana";
        fruits[5] = "Orange";
        fruits[6] = "Apple";
        return fruits;
    }

}