package week2.graph;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public class WeightedUndirectedGraphTest {

    private WeightedUndirectedGraph graph;

    @Before
    public void setUp() {
        graph = new WeightedUndirectedGraph(8);
        graph.addEdge(new Edge(4, 5, 0.35));
        graph.addEdge(new Edge(4, 7, 0.37));
        graph.addEdge(new Edge(5, 7, 0.28));
        graph.addEdge(new Edge(0, 7, 0.16));
        graph.addEdge(new Edge(1, 5, 0.32));
        graph.addEdge(new Edge(0, 4, 0.38));
        graph.addEdge(new Edge(2, 3, 0.17));
        graph.addEdge(new Edge(1, 7, 0.19));
        graph.addEdge(new Edge(0, 2, 0.26));
        graph.addEdge(new Edge(1, 2, 0.36));
        graph.addEdge(new Edge(1, 3, 0.29));
        graph.addEdge(new Edge(2, 7, 0.34));
        graph.addEdge(new Edge(6, 2, 0.40));
        graph.addEdge(new Edge(3, 6, 0.52));
        graph.addEdge(new Edge(6, 0, 0.58));
        graph.addEdge(new Edge(6, 4, 0.93));
    }

    @Test
    public void correctlyAddsEdgesTest() {
        assertEquals(expectedGraphRepresentation(), graph.toString());
    }

    @Test
    public void correctlyCountsVerticesAndEdgesTest() {
        assertEquals(8, graph.vertexCount());
        assertEquals(16, graph.edgeCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void doesNotProcessNonexistentFileTest() {
        new WeightedUndirectedGraph("file_that_does_not_exist.txt");
    }

    @Test
    public void processExistentFileTest() {
        final String filePath = "src/test/java/week2/tiny_weighted.txt";
        final WeightedUndirectedGraph graphFromFile = new WeightedUndirectedGraph(filePath);
        assertEquals(graph.toString(), graphFromFile.toString());
        assertEquals(8, graphFromFile.vertexCount());
        assertEquals(16, graphFromFile.edgeCount());
    }

    @Test
    public void inDegreesTest() {
        assertEquals(4, graph.inDegree(0));
        assertEquals(5, graph.inDegree(2));
    }

    @Test
    public void outDegreesTest() {
        assertEquals(4, graph.outDegree(0));
        assertEquals(5, graph.outDegree(2));
    }

    @Test
    public void emptyGraphRepresentationTest() {
        assertEquals("[]", new WeightedUndirectedGraph(10).toString());
    }

    @Test
    public void onlyConnectedVerticesAreDisplayedTest() {
        final WeightedUndirectedGraph simpleGraph = new WeightedUndirectedGraph(50);
        simpleGraph.addEdge(new Edge(0, 1, 0.23));
        assertEquals(
                "0 - [(1,0.23)]\n1 - [(0,0.23)]",
                simpleGraph.toString()
        );
    }

    @Test
    public void doNotAddSelfLoopsTest() {
        assertFalse(graph.addEdge(new Edge(0, 0, 0.21)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void doNotAllowToCreateGraphWithZeroAmountOfVerticesTest() {
        new WeightedUndirectedGraph(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void doAllowToCreateGraphWithNegativeAmountOfVerticesTest() {
        new WeightedUndirectedGraph(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void doNotAddNegativeVerticesTest() {
        graph.addEdge(new Edge(0, -1, 0.98));
    }

    @Test(expected = IllegalArgumentException.class)
    public void doNotAddVerticesThatDoesNotExistTest() {
        graph.addEdge(new Edge(99, 100, 100.0));
    }

    @Test
    public void edgesTest() {
        assertEquals(expectedEdges(), graph.edges());
    }

    private String expectedGraphRepresentation() {
        final StringBuilder builder = new StringBuilder();
        builder.append("0 - [(7,0.16), (4,0.38), (2,0.26), (6,0.58)]\n");
        builder.append("1 - [(5,0.32), (7,0.19), (2,0.36), (3,0.29)]\n");
        builder.append("2 - [(3,0.17), (0,0.26), (1,0.36), (7,0.34), (6,0.4)]\n");
        builder.append("3 - [(2,0.17), (1,0.29), (6,0.52)]\n");
        builder.append("4 - [(5,0.35), (7,0.37), (0,0.38), (6,0.93)]\n");
        builder.append("5 - [(4,0.35), (7,0.28), (1,0.32)]\n");
        builder.append("6 - [(2,0.4), (3,0.52), (0,0.58), (4,0.93)]\n");
        builder.append("7 - [(4,0.37), (5,0.28), (0,0.16), (1,0.19), (2,0.34)]");
        return builder.toString();
    }
    
    private Iterable<Edge> expectedEdges() {
        final List<Edge> edges = new LinkedList<>();
        edges.add(new Edge(4, 5, 0.35));
        edges.add(new Edge(4, 7, 0.37));
        edges.add(new Edge(5, 7, 0.28));
        edges.add(new Edge(0, 7, 0.16));
        edges.add(new Edge(1, 5, 0.32));
        edges.add(new Edge(0, 4, 0.38));
        edges.add(new Edge(2, 3, 0.17));
        edges.add(new Edge(1, 7, 0.19));
        edges.add(new Edge(0, 2, 0.26));
        edges.add(new Edge(1, 2, 0.36));
        edges.add(new Edge(1, 3, 0.29));
        edges.add(new Edge(2, 7, 0.34));
        edges.add(new Edge(6, 2, 0.40));
        edges.add(new Edge(3, 6, 0.52));
        edges.add(new Edge(6, 0, 0.58));
        edges.add(new Edge(6, 4, 0.93));
        return edges;
    }
}