package week1.search;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import week1.GraphProvider;
import week1.graph.DirectedGraph;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public class DirectedDfsSearchTest {

    private DirectedDfsSearch searchSingleSource;
    private DirectedDfsSearch searchMultipleSource;

    @Before
    public void setUp() {
        final DirectedGraph graph = GraphProvider.newDirectedWith13VerticesAndNoCycles();
        searchSingleSource = new DirectedDfsSearch(graph, 0);
        searchMultipleSource = new DirectedDfsSearch(graph, Arrays.asList(8, 1, 5));
    }

    @Test
    public void checkConnectedTest() {
        assertTrue(searchSingleSource.isConnected(12));
        assertTrue(searchSingleSource.isConnected(10));
        assertTrue(searchSingleSource.isConnected(4));

        assertTrue(searchMultipleSource.isConnected(6));
        assertTrue(searchMultipleSource.isConnected(4));
    }

    @Test
    public void checkNotConnectedTest() {
        assertFalse(searchSingleSource.isConnected(2));
        assertFalse(searchSingleSource.isConnected(3));
        assertFalse(searchSingleSource.isConnected(7));

        assertFalse(searchMultipleSource.isConnected(0));
        assertFalse(searchMultipleSource.isConnected(2));
    }
}