package week2.path;

import week2.graph.DirectedEdge;
import week2.graph.WeightedDirectedGraph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Computes the shortest path in a weighted directed acyclic graph.
 * This algorithm is based on topological sort and is faster than {@link DijkstraShortestPath}.
 *
 * @author Renat Kaitmazov
 */

public final class DagShortestPath implements ShortestPath {

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

    public DagShortestPath(WeightedDirectedGraph graph, int sourceVertex) {
        vertexCount = graph.vertexCount();
        checkVertexRange(sourceVertex);
        cost = new double[vertexCount];
        for (int vertex = 0; vertex < vertexCount; ++vertex) {
            cost[vertex] = Double.POSITIVE_INFINITY;
        }
        cost[sourceVertex] = 0.0;
        edgeTo = new DirectedEdge[vertexCount];
        final Topological topological = new Topological(graph, sourceVertex);
        for (final int vertex : topological.preOrder()) {
            relax(graph, vertex);
        }
    }

    /*--------------------------------------------------------*/
    /* ShortestPath implementation                            */
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

    private void relax(WeightedDirectedGraph graph, int vertex) {
        for (final DirectedEdge edge : graph.adjacentEdges(vertex)) {
            final int toVertex = edge.to();
            final double oldCost = cost[toVertex];
            final double newCost = cost[vertex] + edge.weight();
            if (newCost < oldCost) {
                cost[toVertex] = newCost;
                edgeTo[toVertex] = edge;
            }
        }
    }

    /*--------------------------------------------------------*/
    /* Nested classes                                         */
    /*--------------------------------------------------------*/

    private static final class Topological {

        /*--------------------------------------------------------*/
        /* Fields                                                 */
        /*--------------------------------------------------------*/

        private final Queue<Integer> preOrder = new LinkedList<>();

        /*--------------------------------------------------------*/
        /* Constructors                                           */
        /*--------------------------------------------------------*/

        Topological(WeightedDirectedGraph graph, int source) {
            final int vertexCount = graph.vertexCount();
            final boolean[] visitedVertices = new boolean[vertexCount];
            for (int vertex = source; vertex < vertexCount; ++vertex) {
                if (!visitedVertices[vertex]) {
                    sort(graph, vertex, visitedVertices);
                }
            }
        }

        /*--------------------------------------------------------*/
        /* API                                                    */
        /*--------------------------------------------------------*/

        public Iterable<Integer> preOrder() {
            return preOrder;
        }

        /*--------------------------------------------------------*/
        /* Helper methods                                         */
        /*--------------------------------------------------------*/

        private void sort(WeightedDirectedGraph graph, int vertex, boolean[] visitedVertices) {
            visitedVertices[vertex] = true;
            preOrder.add(vertex);
            for (final DirectedEdge edge : graph.adjacentEdges(vertex)) {
                final int toVertex = edge.to();
                if (!visitedVertices[toVertex]) {
                    sort(graph, toVertex, visitedVertices);
                }
            }
        }
    }
}
