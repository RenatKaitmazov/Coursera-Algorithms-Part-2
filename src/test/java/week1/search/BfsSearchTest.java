package week1.search;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import week1.GraphProvider;
import week1.graph.Graph;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public class BfsSearchTest {

    private Graph graph;
    private AbstractGraphSearch search;

    @Before
    public void setUp() {
        graph = GraphProvider.newUndirectedWith13VerticesAnd3Components();
        search = new BfsSearch(graph, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void doesNotAllowNegativeSourceVertexTest() {
        new DfsSearch(graph, -1);
    }

    @Test
    public void checkNotConnectedVerticesTest() {
        assertFalse(search.isConnectedTo(8));
        assertFalse(search.isConnectedTo(9));
    }

    @Test
    public void checkConnectedVerticesTest() {
        assertTrue(search.isConnectedTo(1));
        assertTrue(search.isConnectedTo(2));
        assertTrue(search.isConnectedTo(3));
        assertTrue(search.isConnectedTo(4));
        assertTrue(search.isConnectedTo(5));
        assertTrue(search.isConnectedTo(6));
    }

    @Test
    public void connectedVerticesCountTest() {
        assertEquals(7, search.count());
    }
}