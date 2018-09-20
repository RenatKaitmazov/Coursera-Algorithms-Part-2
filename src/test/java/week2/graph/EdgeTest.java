package week2.graph;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public class EdgeTest {

    @Test
    public void equalityTest() {
        final Edge edge1 = new Edge(1, 2, 2.0);
        final Edge edge2 = new Edge(1, 2, 2.0);
        assertEquals(0, edge1.compareTo(edge2));
        assertEquals(edge1, edge2);
        assertEquals(edge1.hashCode(), edge2.hashCode());
    }

    @Test
    public void inequalityTest() {
        final Edge edge1 = new Edge(10, 20, 30.0);
        final Edge edge2 = new Edge(10, 21, 100.0);
        assertNotEquals(0, edge1.compareTo(edge2));
        assertNotEquals(edge1, edge2);
        assertNotEquals(edge1.hashCode(), edge2.hashCode());
    }

    @Test
    public void compareToTest() {
        final Edge smaller = new Edge(1, 2, 5.0);
        final Edge greater = new Edge(1, 2, 10.0);
        assertEquals(-1, smaller.compareTo(greater));
        assertEquals(+1, greater.compareTo(smaller));
    }

    @Test
    public void vertexRetrievalTest() {
        final int from = 5;
        final int to = 100;
        final Edge edge = new Edge(from, to, 1.33);
        final int vertex1 = edge.eitherVertex();
        final int vertex2 = edge.otherVertex(vertex1);
        assertEquals(from, vertex1);
        assertEquals(to, vertex2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void inconsistentEdgeTest() {
        final Edge edge = new Edge(1, 2, 3.0);
        edge.otherVertex(100);
    }

    @Test
    public void toStringTest() {
        final Edge edge = new Edge(142, 8716, 1.223);
        assertEquals(
                edge.toString(),
                "{start=142,end=8716,weight=1.223000}"
        );
    }
}