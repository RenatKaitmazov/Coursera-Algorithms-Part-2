package week2.path;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import week2.WeightedGraphProvider;
import week2.graph.DirectedEdge;

import java.util.Collections;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public class DijkstraShortestPathTest {

    private ShortestPath sp;

    @Before
    public void setUp() {
        sp = new DijkstraShortestPath(WeightedGraphProvider.newDirectedGraphWith8VerticesAnd15Edges(), 0);
    }

    @Test
    public void hasPathTest() {
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
        final double delta = 0.001;
        assertEquals(1.05, sp.cost(1), delta);
        assertEquals(0.26, sp.cost(2), delta);
        assertEquals(0.97, sp.cost(3), delta);
        assertEquals(0.38, sp.cost(4), delta);
        assertEquals(0.73, sp.cost(5), delta);
        assertEquals(1.49, sp.cost(6), delta);
        assertEquals(0.60, sp.cost(7), delta);
    }

    @Test
    public void pathTest() {
        assertEquals(
                linkedListOf(new DirectedEdge(0, 4, 0.38), new DirectedEdge(4, 5, 0.35), new DirectedEdge(5, 1, 0.32)),
                sp.path(1)
        );
        assertEquals(
                linkedListOf(new DirectedEdge(0, 2, 0.26)),
                sp.path(2)
        );
        assertEquals(
                linkedListOf(new DirectedEdge(0, 2, 0.26), new DirectedEdge(2, 7, 0.34), new DirectedEdge(7, 3, 0.37)),
                sp.path(3)
        );
        assertEquals(
                linkedListOf(new DirectedEdge(0, 4, 0.38)),
                sp.path(4)
        );
        assertEquals(
                linkedListOf(new DirectedEdge(0, 4, 0.38), new DirectedEdge(4, 5, 0.35)),
                sp.path(5)
        );
        assertEquals(
                linkedListOf(new DirectedEdge(0, 2, 0.26), new DirectedEdge(2, 7, 0.34), new DirectedEdge(7, 3, 0.37), new DirectedEdge(3, 6, 0.52)),
                sp.path(6)
        );
        assertEquals(
                linkedListOf(new DirectedEdge(0, 2, 0.26), new DirectedEdge(2, 7, 0.34)),
                sp.path(7)
        );
    }

    @SafeVarargs
    private final <T> Iterable<T> linkedListOf(T... values) {
        final LinkedList<T> items = new LinkedList<>();
        Collections.addAll(items, values);
        return items;
    }
}