package week1.misc;

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
public class BipartiteTest {

    private Bipartite nonBipartite;
    private Bipartite bipartite;

    @Before
    public void setUp() {

        final Graph graph = new UndirectedGraph(4);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(2, 3);
        bipartite = new Bipartite(graph);

        nonBipartite = new Bipartite(GraphProvider.newUndirectedWith13VerticesAnd3Components());
    }

    @Test
    public void checkBipartiteGraphTest() {
        assertTrue(bipartite.isBipartite());
    }

    @Test
    public void checkNotBipartiteGraphTest() {
        assertFalse(nonBipartite.isBipartite());
    }
}