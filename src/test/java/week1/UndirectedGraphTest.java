package week1;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

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