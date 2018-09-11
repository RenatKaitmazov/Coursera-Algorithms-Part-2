package week1.graph;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import week1.GraphProvider;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public class DirectedGraphTest {

    private DirectedGraph graph;

    @Before
    public void setUp() {
        graph = GraphProvider.provideDirectedWith13Vertices();
    }

    @Test
    public void correctlyAddsEdgesTest() {
        assertEquals(expectedGraphRepresentation(), graph.toString());
    }

    @Test
    public void correctlyCountsVerticesAndEdgesTest() {
        assertEquals(13, graph.vertices());
        assertEquals(22, graph.edges());
    }

    @Test
    public void processExistentFileTest() {
        final String filePath = "src/test/java/week1/tiny_directed_graph.txt";
        final DirectedGraph graphFromFile = new DirectedGraph(filePath);
        assertEquals(expectedGraphRepresentation(), graphFromFile.toString());
        assertEquals(13, graphFromFile.vertices());
        assertEquals(22, graphFromFile.edges());
    }

    @Test
    public void inDegreesTest() {
        assertEquals(2, graph.inDegree(0));
        assertEquals(2, graph.inDegree(12));
        assertEquals(3, graph.inDegree(4));
    }

    @Test
    public void outDegreesTest() {
        assertEquals(2, graph.outDegree(0));
        assertEquals(1, graph.outDegree(12));
        assertEquals(2, graph.outDegree(3));
    }

    @Test
    public void doNotAddSelfLoopsTest() {
        assertFalse(graph.addEdge(0, 0));
    }

    @Test
    public void doNotAddParallelEdgesTest() {
        assertFalse(graph.addEdge(0, 1));
    }

    @Test
    public void checkHasEdgeTest() {
        assertTrue(graph.hasEdge(0, 1));
    }

    @Test
    public void checkEdgeIsAbsentTest() {
        assertFalse(graph.hasEdge(0, 3));
    }

    @Test
    public void checkReverseTest() {
        final DirectedGraph directedGraph = new DirectedGraph(5);
        directedGraph.addEdge(0, 4);
        directedGraph.addEdge(1, 4);
        directedGraph.addEdge(2, 4);
        directedGraph.addEdge(3, 4);
        final DirectedGraph reversed = directedGraph.reverse();
        assertEquals("4 - [0, 1, 2, 3]", reversed.toString());
    }

    private String expectedGraphRepresentation() {
        final String newLine = "\n";
        final StringBuilder expected = new StringBuilder();
        expected.append("0 - [1, 5]").append(newLine);
        expected.append("2 - [0, 3]").append(newLine);
        expected.append("3 - [2, 5]").append(newLine);
        expected.append("4 - [2, 3]").append(newLine);
        expected.append("5 - [4]").append(newLine);
        expected.append("6 - [0, 4, 9]").append(newLine);
        expected.append("7 - [6, 8]").append(newLine);
        expected.append("8 - [7, 9]").append(newLine);
        expected.append("9 - [10, 11]").append(newLine);
        expected.append("10 - [12]").append(newLine);
        expected.append("11 - [4, 12]").append(newLine);
        expected.append("12 - [9]");
        return expected.toString();
    }
}