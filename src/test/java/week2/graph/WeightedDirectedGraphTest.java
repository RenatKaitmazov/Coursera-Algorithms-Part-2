package week2.graph;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import week2.WeightedGraphProvider;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public class WeightedDirectedGraphTest {

    private WeightedDirectedGraph graph;

    @Before
    public void setUp() {
        graph = WeightedGraphProvider.newDirectedGraphWith8VerticesAnd15Edges();
    }

    @Test
    public void vertexCountTest() {
        assertEquals(8, graph.vertexCount());
    }

    @Test
    public void edgeCountTest() {
        assertEquals(15, graph.edgeCount());
    }

    @Test
    public void addEdgeTest() {
        assertEquals(expectedStringRepresentation(), graph.toString());
    }

    @Test
    public void edgesTest() {
        assertEquals(expectedEdges(), graph.edges());
    }

    @Test
    public void inDegreeTest() {
        assertEquals(3, graph.inDegree(7));
        assertEquals(1, graph.inDegree(1));
    }

    @Test
    public void outDegreeTest() {
        assertEquals(2, graph.outDegree(7));
        assertEquals(3, graph.outDegree(6));
    }

    @Test
    public void doesNotAddSelfLoops() {
        assertFalse(graph.addEdge(new DirectedEdge(0, 0, 0.23)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void doesNotProcessNonexistentFileTest() {
        new WeightedDirectedGraph("file_that_does_not_exist.txt");
    }

    @Test
    public void processExistentFileTest() {
        final String filePath = "src/test/java/week2/tiny_directed_weighted.txt";
        final WeightedDirectedGraph graph = new WeightedDirectedGraph(filePath);
        assertEquals(this.graph.toString(), graph.toString());
        assertEquals(this.graph.vertexCount(), graph.vertexCount());
        assertEquals(this.graph.edgeCount(), graph.edgeCount());
        assertEquals(this.graph.edges(), graph.edges());
    }

    @Test
    public void emptyGraphRepresentationTest() {
        assertEquals("[]", new WeightedDirectedGraph(10).toString());
    }

    @Test
    public void onlyConnectedVerticesAreDisplayedTest() {
        final WeightedDirectedGraph simpleGraph = new WeightedDirectedGraph(50);
        assertTrue(simpleGraph.addEdge(new DirectedEdge(0, 1, 0.52)));
        assertEquals(
                "0 - [(0->1|0.520000)]",
                simpleGraph.toString()
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void doNotAllowToCreateGraphWithZeroAmountOfVerticesTest() {
        new WeightedDirectedGraph(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void doAllowToCreateGraphWithNegativeAmountOfVerticesTest() {
        new WeightedDirectedGraph(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void doNotAddVerticesThatDoesNotExistTest() {
        graph.addEdge(new DirectedEdge(99, 100, 100.0));
    }

    private String expectedStringRepresentation() {
        final StringBuilder builder = new StringBuilder();
        builder.append("0 - [(0->4|0.380000), (0->2|0.260000)]\n");
        builder.append("1 - [(1->3|0.290000)]\n");
        builder.append("2 - [(2->7|0.340000)]\n");
        builder.append("3 - [(3->6|0.520000)]\n");
        builder.append("4 - [(4->5|0.350000), (4->7|0.370000)]\n");
        builder.append("5 - [(5->4|0.350000), (5->7|0.280000), (5->1|0.320000)]\n");
        builder.append("6 - [(6->2|0.400000), (6->0|0.580000), (6->4|0.930000)]\n");
        builder.append("7 - [(7->5|0.280000), (7->3|0.370000)]");
        return builder.toString();
    }

    private Iterable<DirectedEdge> expectedEdges() {
        final List<DirectedEdge> edges = new LinkedList<>();
        edges.add(new DirectedEdge(4, 5, 0.35));
        edges.add(new DirectedEdge(5, 4, 0.35));
        edges.add(new DirectedEdge(4, 7, 0.37));
        edges.add(new DirectedEdge(5, 7, 0.28));
        edges.add(new DirectedEdge(7, 5, 0.28));
        edges.add(new DirectedEdge(5, 1, 0.32));
        edges.add(new DirectedEdge(0, 4, 0.38));
        edges.add(new DirectedEdge(0, 2, 0.26));
        edges.add(new DirectedEdge(7, 3, 0.37));
        edges.add(new DirectedEdge(1, 3, 0.29));
        edges.add(new DirectedEdge(2, 7, 0.34));
        edges.add(new DirectedEdge(6, 2, 0.40));
        edges.add(new DirectedEdge(3, 6, 0.52));
        edges.add(new DirectedEdge(6, 0, 0.58));
        edges.add(new DirectedEdge(6, 4, 0.93));
        return edges;
    }
}