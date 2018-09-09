package week1.path;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import week1.graph.Graph;
import week1.graph.UndirectedGraph;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public class BfsPathTest {

    private AbstractGraphPath path;

    @Before
    public void setUp() {
        final Graph graph = new UndirectedGraph(10);
        graph.addEdge(1, 2);
        graph.addEdge(1, 7);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(5, 6);
        graph.addEdge(7, 8);
        graph.addEdge(8, 6);
        path = new BfsPath(graph, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void doesNotAllowNegativeSourceVertexTest() {
        new DfsPath(new UndirectedGraph(1), -1);
    }

    @Test
    public void doesNotHavePathToDisconnectedVertexTest() {
        assertNull(path.pathTo(0));
    }

    @Test
    public void hasPathToConnectedVertexTest() {
        final LinkedList<Integer> expectedPath = new LinkedList<>();
        expectedPath.add(1);
        expectedPath.add(7);
        expectedPath.add(8);
        expectedPath.add(6);
        assertEquals(expectedPath, path.pathTo(6));
    }
}