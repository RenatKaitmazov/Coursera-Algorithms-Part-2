package week1.misc;

import week1.graph.UndirectedGraph;

/**
 * This class allows to determine if there is a cycle in an undirected graph or not.
 *
 * @author Renat Kaitmazov
 */

public final class UndirectedCycle {

    /*--------------------------------------------------------*/
    /* Fields                                                 */
    /*--------------------------------------------------------*/

    private boolean hasCycle;

    /**
     * Keeps track of what vertex is visited so that we won't process the same vertex more than one time.
     */
    private final boolean[] visitedVertices;

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    public UndirectedCycle(UndirectedGraph graph) {
        final int vertexCount = graph.vertices();
        visitedVertices = new boolean[vertexCount];
        for (int vertex = 0; vertex < vertexCount; ++vertex) {
            if (!visitedVertices[vertex]) {
                searchCycle(graph, vertex, vertex);
            }
            if (hasCycle) {
                break;
            }
        }
    }

    /*--------------------------------------------------------*/
    /* API                                                    */
    /*--------------------------------------------------------*/

    public boolean hasCycle() {
        return hasCycle;
    }

    /*--------------------------------------------------------*/
    /* Helper methods                                         */
    /*--------------------------------------------------------*/

    private void searchCycle(UndirectedGraph graph, int vertex, int parentVertex) {
        visitedVertices[vertex] = true;
        for (final int neighbour : graph.adjacentVertices(vertex)) {
            if (!visitedVertices[neighbour]) {
                searchCycle(graph, neighbour, vertex);
            } else if (neighbour != parentVertex) {
                // At this point we know that the neighbour vertex is already visited from somewhere else
                // which means there is a cycle.
                hasCycle = true;
                return;
            }
        }
    }

}
