package week2.graph;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public class DirectedEdgeTest {


    @Test
    public void toStringTest() {
        final DirectedEdge edge = new DirectedEdge(1, 5, 0.2627);
        assertEquals("(1->5|0.262700)", edge.toString());
    }

    @Test
    public void gettersTest() {
        final DirectedEdge edge = new DirectedEdge(6, 3, 0.23194);
        assertEquals(6, edge.from());
        assertEquals(3, edge.to());
        assertEquals(0.23194, edge.weight(), 0.0);
    }
}