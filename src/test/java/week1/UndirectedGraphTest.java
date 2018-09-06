package week1;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import week1.graph.Graph;
import week1.graph.UndirectedGraph;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public class UndirectedGraphTest {

    private Graph graph;

    @Before
    public void setUp() {
        graph = GraphProvider.newUndirectedWith10VerticesAnd3Components();
    }

    @Test
    public void correctlyAddsEdgesTest() {
        assertEquals(expectedGraphRepresentation(), graph.toString());
    }

    @Test
    public void correctlyCountsVerticesAndEdgesTest() {
        assertEquals(13, graph.vertices());
        assertEquals(13, graph.edges());
    }

    @Test(expected = IllegalArgumentException.class)
    public void doesNotProcessNonexistentFileTest() {
        new UndirectedGraph("file_that_does_not_exist.txt");
    }

    @Test
    public void processExistentFileTest() {
        final String filePath = "src/test/java/week1/tiny_graph.txt";
        final Graph graphFromFile = new UndirectedGraph(filePath);
        assertEquals(expectedGraphRepresentation(), graphFromFile.toString());
        assertEquals(13, graphFromFile.vertices());
        assertEquals(13, graphFromFile.edges());
    }

    @Test
    public void inDegreesTest() {
        assertEquals(4, graph.inDegree(0));
        assertEquals(2, graph.inDegree(12));
    }

    @Test
    public void outDegreesTest() {
        assertEquals(4, graph.outDegree(0));
        assertEquals(2, graph.outDegree(12));
    }

    @Test
    public void emptyGraphRepresentationTest() {
        assertEquals("[]", new UndirectedGraph(100).toString());
    }

    @Test
    public void onlyConnectedVerticesAreDisplayedTest() {
        final Graph simpleGraph = new UndirectedGraph(100);
        simpleGraph.addEdge(0, 1);
        simpleGraph.addEdge(0, 2);
        assertEquals("0 - [1, 2]\n1 - [0]\n2 - [0]", simpleGraph.toString());
    }

    @Test
    public void doNotAddSelfLoops() {
        assertFalse(graph.addEdge(0, 0));
    }

    @Test
    public void doAddParallelEdges() {
        assertFalse(graph.addEdge(0, 6));
    }

    @Test(expected = IllegalArgumentException.class)
    public void doNotAllowToCreateGraphWithZeroAmountOfVertices() {
        new UndirectedGraph(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void doAllowToCreateGraphWithNegativeAmountOfVertices() {
        new UndirectedGraph(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void doNotAddNegativeVertices() {
        graph.addEdge(0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void doNotAddVerticesThatDoesNotExist() {
        graph.addEdge(99, 100);
    }

    private String expectedGraphRepresentation() {
        final StringBuilder expected = new StringBuilder();
        final String newLine = "\n";
        expected.append("0 - [1, 2, 5, 6]").append(newLine);
        expected.append("1 - [0]").append(newLine);
        expected.append("2 - [0]").append(newLine);
        expected.append("3 - [5, 4]").append(newLine);
        expected.append("4 - [5, 3, 6]").append(newLine);
        expected.append("5 - [0, 3, 4]").append(newLine);
        expected.append("6 - [0, 4]").append(newLine);

        expected.append("7 - [8]").append(newLine);
        expected.append("8 - [7]").append(newLine);

        expected.append("9 - [10, 11, 12]").append(newLine);
        expected.append("10 - [9]").append(newLine);
        expected.append("11 - [9, 12]").append(newLine);
        expected.append("12 - [9, 11]");
        return expected.toString();
    }
}