package week1.cc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import week1.GraphProvider;
import week1.graph.Graph;
import week1.graph.UndirectedGraph;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public class ConnectedComponentsTest {

    private ConnectedComponents components;

    @Before
    public void setUp() {
        final Graph graph = GraphProvider.newUndirectedWith10VerticesAnd3Components();
        components = new ConnectedComponents((UndirectedGraph) graph);
    }

    @Test
    public void checkNotConnectedTest() {
        assertFalse(components.isConnected(0, 7));
        assertFalse(components.isConnected(7, 9));
        assertFalse(components.isConnected(0, 9));
    }

    @Test
    public void checkConnectedTest() {
        assertTrue(components.isConnected(0, 1));
        assertTrue(components.isConnected(7, 8));
        assertTrue(components.isConnected(9, 12));
    }

    @Test
    public void checkComponentsCountTest() {
        assertEquals(3, components.count());
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkNegativeVerticesAreNotAllowed() {
        components.isConnected(-1, -10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkNonexistentVerticesAreNotAllowed() {
        components.isConnected(0, 100);
    }

    @Test
    public void checkVerticesIdsTest() {
        assertEquals(0, components.vertexId(0));
        assertEquals(0, components.vertexId(1));
        assertEquals(0, components.vertexId(2));
        assertEquals(0, components.vertexId(3));
        assertEquals(0, components.vertexId(4));
        assertEquals(0, components.vertexId(5));
        assertEquals(0, components.vertexId(6));
        assertEquals(1, components.vertexId(7));
        assertEquals(1, components.vertexId(8));
        assertEquals(2, components.vertexId(9));
        assertEquals(2, components.vertexId(10));
        assertEquals(2, components.vertexId(11));
        assertEquals(2, components.vertexId(12));
    }
}