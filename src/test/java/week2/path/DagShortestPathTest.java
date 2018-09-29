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
public class DagShortestPathTest {

    private final WeightedPath sp = new DagShortestPath(WeightedGraphProvider.newDagWith8verticesAnd13Edges(), 5);

    @Test
    public void hasPathTest() {
        assertTrue(sp.hasPath(0));
        assertTrue(sp.hasPath(1));
        assertTrue(sp.hasPath(2));
        assertTrue(sp.hasPath(3));
        assertTrue(sp.hasPath(4));
        assertTrue(sp.hasPath(5));
        assertTrue(sp.hasPath(6));
        assertTrue(sp.hasPath(7));
    }

    @Test
    public void costTest() {
        final double delta = 0.0001;
        assertEquals(0.73, sp.cost(0), delta);
        assertEquals(0.32, sp.cost(1), delta);
        assertEquals(0.62, sp.cost(2), delta);
        assertEquals(0.61, sp.cost(3), delta);
        assertEquals(0.35, sp.cost(4), delta);
        assertEquals(0.0, sp.cost(5), delta);
        assertEquals(1.13, sp.cost(6), delta);
        assertEquals(0.28, sp.cost(7), delta);
    }

    @Test
    public void pathTest() {
        assertEquals(
                createPath(new DirectedEdge(5, 1, 0.32)),
                sp.path(1)
        );
        assertEquals(
                createPath(new DirectedEdge(5, 4, 0.35)),
                sp.path(4)
        );
        assertEquals(
                createPath(new DirectedEdge(5, 7, 0.28)),
                sp.path(7)
        );
        assertEquals(
                createPath(new DirectedEdge(5, 4, 0.35), new DirectedEdge(4, 0, 0.38)),
                sp.path(0)
        );
        assertEquals(
                createPath(new DirectedEdge(5, 7, 0.28), new DirectedEdge(7, 2, 0.34)),
                sp.path(2)
        );
        assertEquals(
                createPath(new DirectedEdge(5, 1, 0.32), new DirectedEdge(1, 3, 0.29), new DirectedEdge(3, 6, 0.52)),
                sp.path(6)
        );
    }

    @SafeVarargs
    private final <T> Iterable<T> createPath(T... items) {
        final LinkedList<T> list = new LinkedList<>();
        Collections.addAll(list, items);
        return list;
    }
}