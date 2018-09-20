package week2.mst;

import week2.graph.Edge;

/**
 * @author Renat Kaitmazov
 */

public interface MinSpanningTree {

    /**
     * Edges of the minimum spanning tree.
     *
     * @return MST represented as an iterable collection of edges.
     */
    Iterable<Edge> edges();

    /**
     * Total weight of all edges in the MST calculated in {@link #edges()}.
     *
     * @return total weight.
     */
    double weight();
}
