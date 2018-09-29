package week2.path;

import week2.graph.DirectedEdge;
import week2.graph.WeightedDirectedGraph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Renat Kaitmazov
 */

public final class PreOrderSort {

    /*--------------------------------------------------------*/
    /* Fields                                                 */
    /*--------------------------------------------------------*/

    private final Queue<Integer> preOrder = new LinkedList<>();

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    public PreOrderSort(WeightedDirectedGraph graph, int sourceVertex) {
        final int vertexCount = graph.vertexCount();
        final boolean[] visitedVertices = new boolean[vertexCount];
        for (int vertex = sourceVertex; vertex < vertexCount; ++vertex) {
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