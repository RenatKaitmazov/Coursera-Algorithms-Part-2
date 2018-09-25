package week2.mst;

import week2.graph.Edge;
import week2.graph.WeightedUndirectedGraph;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * This is a lazy version of the Prim's algorithm to find the minimum spanning tree in a weighted graph.
 *
 * @author Renat Kaitmazov
 */

public final class LazyPrimMst implements MinSpanningTree {

    /*--------------------------------------------------------*/
    /* Fields                                                 */
    /*--------------------------------------------------------*/

    private final Queue<Edge> mstEdges = new LinkedList<>();
    private double totalWeight;

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    public LazyPrimMst(WeightedUndirectedGraph graph) {
        // It is expected that the graph is connected.
        final int vertexCount = graph.vertexCount();
        final boolean[] verticesOnMst = new boolean[vertexCount];
        final Queue<Edge> minEdges = new PriorityQueue<>(graph.edgeCount());
        final int mstSize = vertexCount - 1;
        // Start with initial vertex and then grow the MST from that vertex.
        addAdjacentEdges(graph, minEdges, verticesOnMst, 0);
        while (!minEdges.isEmpty() && mstEdges.size() < mstSize) {
            // An edge that could potentially be added into the MST
            final Edge minEdge = minEdges.remove();
            final int vertex1 = minEdge.eitherVertex();
            final int vertex2 = minEdge.otherVertex(vertex1);
            // Found an edge that is already in the MST.
            if (verticesOnMst[vertex1] && verticesOnMst[vertex2]) continue;
            mstEdges.add(minEdge);
            totalWeight += minEdge.weight();
            // Explore other edges of the graph.
            if (!verticesOnMst[vertex1]) {
                addAdjacentEdges(graph, minEdges, verticesOnMst, vertex1);
            }
            if (!verticesOnMst[vertex2]) {
                addAdjacentEdges(graph, minEdges, verticesOnMst, vertex2);
            }
        }
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

    /*--------------------------------------------------------*/
    /* Helper methods                                         */
    /*--------------------------------------------------------*/

    private void addAdjacentEdges(WeightedUndirectedGraph graph,
                                  Queue<Edge> minEdges,
                                  boolean[] verticesOnMst,
                                  int vertex) {
        verticesOnMst[vertex] = true;
        for (final Edge edge : graph.adjacentEdges(vertex)) {
            // Do not add the same edge twice.
            final int otherVertex = edge.otherVertex(vertex);
            if (verticesOnMst[otherVertex]) continue;
            minEdges.add(edge);
        }
    }
}
