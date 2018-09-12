package week1.search;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
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
        final DirectedGraph graph = new DirectedGraph(13);
        graph.addEdge(0, 1);
        graph.addEdge(0, 5);
        graph.addEdge(0, 6);
        graph.addEdge(2, 0);
        graph.addEdge(2, 3);
        graph.addEdge(3, 5);
        graph.addEdge(5, 4);
        graph.addEdge(6, 4);
        graph.addEdge(6, 9);
        graph.addEdge(7, 6);
        graph.addEdge(8, 7);
        graph.addEdge(9, 10);
        graph.addEdge(9, 11);
        graph.addEdge(9, 12);
        graph.addEdge(11, 12);
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