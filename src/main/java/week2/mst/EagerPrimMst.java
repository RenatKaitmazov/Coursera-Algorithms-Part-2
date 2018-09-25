package week2.mst;

import week2.graph.Edge;
import week2.graph.WeightedUndirectedGraph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This is an eager version of the Prim's algorithm to find the minimum spanning tree in a weighted graph.
 *
 * @author Renat Kaitmazov
 */

public final class EagerPrimMst implements MinSpanningTree {

    /*--------------------------------------------------------*/
    /* Fields                                                 */
    /*--------------------------------------------------------*/

    private final Queue<Edge> mstEdges = new LinkedList<>();
    private double totalWeight;

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    public EagerPrimMst(WeightedUndirectedGraph graph) {
        // The graph is expected to be connected.

    }

    /*--------------------------------------------------------*/
    /* MinSpanningTree implementation                         */
    /*--------------------------------------------------------*/

    @Override
    public Iterable<Edge> edges() {
        return mstEdges;
    }

    @Override
    public double weight() {
        return totalWeight;
    }
}
