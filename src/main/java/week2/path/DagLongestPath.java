package week2.path;

import week2.graph.DirectedEdge;
import week2.graph.WeightedDirectedGraph;

import java.util.LinkedList;

/**
 * This algorithm computes the longest path in a DAG.
 *
 * @author Renat Kaitmazov
 */

public final class DagLongestPath implements WeightedPath {

    /*--------------------------------------------------------*/
    /* Fields                                                 */
    /*--------------------------------------------------------*/

    private final int vertexCount;
    /**
     * Stores the cost of paths from the source vertex to each other vertex reachable from the source.
     */
    private final double[] cost;
    /**
     * Stores edges so that we can find a path from an arbitrary vertex in the graph to the source vertex
     * if there is a path between them.
     */
    private final DirectedEdge[] edgeTo;

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    public DagLongestPath(WeightedDirectedGraph graph, int sourceVertex) {
        vertexCount = graph.vertexCount();
        checkVertexRange(sourceVertex);
        cost = new double[vertexCount];
        for (int vertex = 0; vertex < vertexCount; ++vertex) {
            cost[vertex] = Double.NEGATIVE_INFINITY;
        }
        cost[sourceVertex] = 0.0;
        edgeTo = new DirectedEdge[vertexCount];
        final ReversedPostOrderSort sort = new ReversedPostOrderSort(graph, sourceVertex);
        for (final int vertex : sort.preOrder()) {
            relax(graph, vertex);
        }
    }

    /*--------------------------------------------------------*/
    /* WeightedPath implementation                            */
    /*--------------------------------------------------------*/

    @Override
    public boolean hasPath(int toVertex) {
        return cost(toVertex) > Double.NEGATIVE_INFINITY;
    }

    @Override
    public Iterable<DirectedEdge> path(int toVertex) {
        if (!hasPath(toVertex)) return null;
        final LinkedList<DirectedEdge> path = new LinkedList<>();
        for (DirectedEdge edge = edgeTo[toVertex]; edge != null; edge = edgeTo[edge.from()]) {
            path.addFirst(edge);
        }
        return path;
    }

    @Override
    public double cost(int toVertex) {
        checkVertexRange(toVertex);
        return cost[toVertex];
    }

    /*--------------------------------------------------------*/
    /* Helper methods                                         */
    /*--------------------------------------------------------*/

    private void checkVertexRange(int vertex) {
        if (vertex < 0 || vertex >= vertexCount) {
            throw new IllegalArgumentException("The graph does not have vertex: " + vertex);
        }
    }

    private void relax(WeightedDirectedGraph graph, int vertex) {
        for (final DirectedEdge edge : graph.adjacentEdges(vertex)) {
            final int fromVertex = edge.from();
            final int toVertex = edge.to();
            final double oldCost = cost[toVertex];
            final double newCost = cost[fromVertex] + edge.weight();
            if (newCost > oldCost) {
                cost[toVertex] = newCost;
                edgeTo[toVertex] = edge;
            }
        }
    }
}
