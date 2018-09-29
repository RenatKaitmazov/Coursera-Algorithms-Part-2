package week2.path;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import week2.WeightedGraphProvider;
import week2.graph.DirectedEdge;

import java.util.Collections;
import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public class DagLongestPathTest {

    private final WeightedPath lp = new DagLongestPath(
            WeightedGraphProvider.newDagWith8verticesAnd13Edges(),
            5
    );

    @Test
    public void hasPathTest() {
        assertTrue(lp.hasPath(0));
        assertTrue(lp.hasPath(1));
        assertTrue(lp.hasPath(2));
        assertTrue(lp.hasPath(3));
        assertTrue(lp.hasPath(4));
        assertTrue(lp.hasPath(5));
        assertTrue(lp.hasPath(6));
        assertTrue(lp.hasPath(7));
    }

    @Test
    public void costTest() {
        final double delta = 0.00001;
        assertEquals(0.00, lp.cost(5), delta);
        assertEquals(0.32, lp.cost(1), delta);
        assertEquals(0.61, lp.cost(3), delta);
        assertEquals(1.13, lp.cost(6), delta);
        assertEquals(2.06, lp.cost(4), delta);
        assertEquals(2.44, lp.cost(0), delta);
        assertEquals(2.43, lp.cost(7), delta);
        assertEquals(2.77, lp.cost(2), delta);
    }

    @Test
    public void pathTest() {
        assertEquals(
                listOf(new DirectedEdge(5, 1, 0.32)),
                lp.path(1)
        );
        assertEquals(
                listOf(new DirectedEdge(5, 1, 0.32), new DirectedEdge(1, 3, 0.29)),
                lp.path(3)
        );
        assertEquals(
                listOf(new DirectedEdge(5, 1, 0.32), new DirectedEdge(1, 3, 0.29), new DirectedEdge(3, 6, 0.52)),
                lp.path(6)
        );
        assertEquals(
                listOf(
                        new DirectedEdge(5, 1, 0.32),
                        new DirectedEdge(1, 3, 0.29),
                        new DirectedEdge(3, 6, 0.52),
                        new DirectedEdge(6, 4, 0.93)
                ),
                lp.path(4)
        );
        assertEquals(
                listOf(
                        new DirectedEdge(5, 1, 0.32),
                        new DirectedEdge(1, 3, 0.29),
                        new DirectedEdge(3, 6, 0.52),
                        new DirectedEdge(6, 4, 0.93),
                        new DirectedEdge(4, 0, 0.38)
                ),
                lp.path(0)
        );
        assertEquals(
                listOf(
                        new DirectedEdge(5, 1, 0.32),
                        new DirectedEdge(1, 3, 0.29),
                        new DirectedEdge(3, 6, 0.52),
                        new DirectedEdge(6, 4, 0.93),
                        new DirectedEdge(4, 7, 0.37)
                ),
                lp.path(7)
        );
        assertEquals(
                listOf(
                        new DirectedEdge(5, 1, 0.32),
                        new DirectedEdge(1, 3, 0.29),
                        new DirectedEdge(3, 6, 0.52),
                        new DirectedEdge(6, 4, 0.93),
                        new DirectedEdge(4, 7, 0.37),
                        new DirectedEdge(7, 2, 0.34)
                ),
                lp.path(2)
        );
    }

    @SafeVarargs
    private final <T> Iterable<T> listOf(T... items) {
        final LinkedList<T> list = new LinkedList<>();
        Collections.addAll(list, items);
        return list;
    }
}