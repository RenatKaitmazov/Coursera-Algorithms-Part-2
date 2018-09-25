package week2.mst;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import week2.WeightedGraphProvider;
import week2.graph.Edge;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public class EagerPrimMstTest {

    private final EagerPrimMst mst = new EagerPrimMst(WeightedGraphProvider.newGraphWith8VerticesAnd16Edges());

    @Test
    public void totalWeightTest() {
        assertEquals(1.81, mst.weight(), 0.0);
    }

    @Test
    public void mstEdgesTest() {
        assertEquals(expectedMst(), mst.edges());
    }

    private Iterable<Edge> expectedMst() {
        final Queue<Edge> mst = new LinkedList<>();
        mst.add(new Edge(0, 7, 0.16));
        mst.add(new Edge(1, 7, 0.19));
        mst.add(new Edge(0, 2, 0.26));
        mst.add(new Edge(2, 3, 0.17));
        mst.add(new Edge(5, 7, 0.28));
        mst.add(new Edge(4, 5, 0.35));
        mst.add(new Edge(6, 2, 0.40));
        return mst;
    }
}