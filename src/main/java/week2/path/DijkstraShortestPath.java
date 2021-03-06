package week2.path;

import week2.graph.DirectedEdge;
import week2.graph.WeightedDirectedGraph;
import week2.mst.IndexMinPriorityQueue;

import java.util.LinkedList;

/**
 * Edgar Dijkstra's algorithm to find the shortest path.
 *
 * @author Renat Kaitmazov
 */

public final class DijkstraShortestPath implements WeightedPath {

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

    public DijkstraShortestPath(WeightedDirectedGraph graph, int sourceVertex) {
        // Perform set up.
        vertexCount = graph.vertexCount();
        checkVertexRange(sourceVertex);
        cost = new double[vertexCount];
        for (int vertex = 0; vertex < vertexCount; ++vertex) {
            cost[vertex] = Double.POSITIVE_INFINITY;
        }
        final double sourceDistance = 0.0;
        cost[sourceVertex] = sourceDistance;
        edgeTo = new DirectedEdge[vertexCount];
        final IndexMinPriorityQueue<Double> minQueue = new IndexMinPriorityQueue<>(vertexCount);
        minQueue.insert(sourceVertex, sourceDistance);
        while (!minQueue.isEmpty()) {
            // A visited vertex which is on the shortest path.
            final int visitedVertex = minQueue.delete();
            relax(graph, minQueue, visitedVertex);
        }
    }

    /*--------------------------------------------------------*/
    /* WeightedPath implementation                            */
    /*--------------------------------------------------------*/

    @Override
    public boolean hasPath(int toVertex) {
        return cost(toVertex) < Double.POSITIVE_INFINITY;
    }

    @Override
    public Iterable<DirectedEdge> path(int toVertex) {
        if (!hasPath(toVertex)) return null;
        final LinkedList<DirectedEdge> reversedEdges = new LinkedList<>();
        for (DirectedEdge edge = edgeTo[toVertex]; edge != null; edge = edgeTo[edge.from()]) {
            reversedEdges.addFirst(edge);
        }
        return reversedEdges;
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

    // The relaxation is basically the process of updating the cost and the edge connecting the source vertex
    // with its neighbour vertex if we found a better way to go from the source to the neighbour.
    private void relax(WeightedDirectedGraph graph,
                       IndexMinPriorityQueue<Double> minQueue,
                       int sourceVertex) {
        for (final DirectedEdge edge : graph.adjacentEdges(sourceVertex)) {
            // The start point of the edge.
            final int fromVertex = edge.from();
            // The endpoint of the edge.
            final int toVertex = edge.to();
            // The cost of reaching the endpoint from other path (if there is such a path).
            final double oldCost = cost[toVertex];
            // The cost of reaching the endpoint from the current path.
            final double newCost = cost[fromVertex] + edge.weight();
            if (newCost < oldCost) {
                // It is cheaper to get to the endpoint using the current path, so update the data.
                cost[toVertex] = newCost;
                edgeTo[toVertex] = edge;
                if (minQueue.contains(toVertex)) {
                    minQueue.decreaseKey(toVertex, newCost);
                } else {
                    // This means that this is the only path from source to the endpoint and there is no any other paths
                    // to get here.
                    minQueue.insert(toVertex, newCost);
                }
            }
        }
    }
}
