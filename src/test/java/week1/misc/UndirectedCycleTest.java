package week1.misc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import week1.GraphProvider;
import week1.graph.UndirectedGraph;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public class UndirectedCycleTest {

    private UndirectedCycle cycle1;
    private UndirectedCycle cycle2;

    @Before
    public void setUp() {
        final UndirectedGraph graph1 = (UndirectedGraph) GraphProvider.newUndirectedWith13VerticesAnd3Components();
        cycle1 = new UndirectedCycle(graph1);

        final UndirectedGraph graph2 = new UndirectedGraph(5);
        graph2.addEdge(0, 4);
        graph2.addEdge(1, 4);
        graph2.addEdge(2, 4);
        graph2.addEdge(3, 4);
        cycle2 = new UndirectedCycle(graph2);
    }

    @Test
    public void checkHasCycleTest() {
        assertTrue(cycle1.hasCycle());
    }

    @Test
    public void checkNoCycleTest() {
        assertFalse(cycle2.hasCycle());
    }
}