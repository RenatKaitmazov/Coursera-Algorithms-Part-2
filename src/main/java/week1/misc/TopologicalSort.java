package week1.misc;

import week1.graph.DirectedGraph;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Performs a topological sort on a given graph.
 * Bear in mind that the graph must be a directed acyclic graph.
 * If the graph has cycles, it is impossible to sort such a graph.
 *
 * @author Renat Kaitmazov
 */

public final class TopologicalSort {

    /*--------------------------------------------------------*/
    /* Fields                                                 */
    /*--------------------------------------------------------*/

    /**
     * Keeps track of what vertex is visited so that we won't process the same vertex more than one time.
     */
    private final boolean[] visitedVertices;

    /**
     * Stores topologically sorted vertices of a graph.
     */
    private final Deque<Integer> reversedPostOrder;

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    public TopologicalSort(DirectedGraph graph) {
        final DirectedCycle cycleDetector = new DirectedCycle(graph);
        if (cycleDetector.hasCycle()) {
            throw new IllegalArgumentException("The graph has a cycle. Impossible to sort!");
        }
        reversedPostOrder = new ArrayDeque<>();
        final int vertexCount = graph.vertices();
        visitedVertices = new boolean[vertexCount];
        for (int vertex = 0; vertex < vertexCount; ++vertex) {
            if (!visitedVertices[vertex]) {
                sort(graph, vertex);
            }
        }
    }

    /*--------------------------------------------------------*/
    /* API                                                    */
    /*--------------------------------------------------------*/

    public Iterable<Integer> sortedVertices() {
        return reversedPostOrder;
    }

    /*--------------------------------------------------------*/
    /* Helper methods                                         */
    /*--------------------------------------------------------*/

    private void sort(DirectedGraph graph, int sourceVertex) {
        visitedVertices[sourceVertex] = true;
        for (final int neighbour : graph.adjacentVertices(sourceVertex)) {
            if (!visitedVertices[neighbour]) {
                sort(graph, neighbour);
            }
        }
        // At this point we know that the source vertex does not have any unvisited adjacent vertices
        // which means that the vertex has no more constraints to check and thus can be added to the stack.
        reversedPostOrder.push(sourceVertex);
    }
}
