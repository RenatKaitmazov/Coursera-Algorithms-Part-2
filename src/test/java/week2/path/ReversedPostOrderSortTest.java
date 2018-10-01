package week2.path;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import week2.WeightedGraphProvider;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public class ReversedPostOrderSortTest {

    private final ReversedPostOrderSort sort = new ReversedPostOrderSort(WeightedGraphProvider.newDagWith8verticesAnd13Edges(), 5);

    @Test
    public void preOrderTest() {
        assertEquals(queueOf(5, 1, 3, 6, 4, 0, 7, 2), sort.preOrder());
    }

    @SafeVarargs
    private final <T> Queue<T> queueOf(T... items) {
        final Queue<T> queue = new LinkedList<>();
        Collections.addAll(queue, items);
        return queue;
    }
}