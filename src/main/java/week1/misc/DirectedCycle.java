package week1.misc;

import week1.graph.DirectedGraph;

import java.util.LinkedList;

/**
 * Determines a cycle in a directed graph using the DFS algorithm.
 *
 * @author Renat Kaitmazov
 */

public final class DirectedCycle {

    /*--------------------------------------------------------*/
    /* Fields                                                 */
    /*--------------------------------------------------------*/

    /**
     * Keeps track of what vertex is visited so that we won't process the same vertex more than one time.
     */
    private final boolean[] visitedVertices;

    /**
     * Stores the route from the source vertex to any other vertex (if there is such a route).
     */
    private final int[] route;

    /**
     * Keeps track of what vertex is currently on the stack (we use the DFS that uses a stack).
     */
    private final boolean[] onStack;

    /**
     * Stores vertices that form a cycle (if present).
     */
    private LinkedList<Integer> cycle;

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    public DirectedCycle(DirectedGraph graph) {
        final int size = graph.vertices();
        visitedVertices = new boolean[size];
        route = new int[size];
        onStack = new boolean[size];
        for (int vertex = 0; vertex < size; ++vertex) {
            if (!visitedVertices[vertex]) {
                searchCycle(graph, vertex);
            }
            if (hasCycle()) {
                break;
            }
        }
    }

    /*--------------------------------------------------------*/
    /* API                                                    */
    /*--------------------------------------------------------*/

    /**
     * Checks to see if there is a cycle in the given graph.
     *
     * @return <code>true</code> if there is a cycle, <code>false</code> otherwise.
     */
    public boolean hasCycle() {
        return cycle != null;
    }

    /**
     * Returns a cycle.
     *
     * @return an iterable collection of vertices starting from the source vertex, <code>null</code> if no cycle
     * is present in the graph.
     */
    public Iterable<Integer> cycle() {
        return cycle;
    }

    /*--------------------------------------------------------*/
    /* Helper methods                                         */
    /*--------------------------------------------------------*/

    private void searchCycle(DirectedGraph graph, int sourceVertex) {
        // The current vertex is now on the stack.
        onStack[sourceVertex] = true;
        visitedVertices[sourceVertex] = true;
        for (final int neighbour : graph.adjacentVertices(sourceVertex)) {
            if (hasCycle()) {
                return;
            }
            if (!visitedVertices[neighbour]) {
                // Remember from which vertex I got to the neighbour
                route[neighbour] = sourceVertex;
                searchCycle(graph, neighbour);
            } else if (onStack[neighbour]) {
                // If the visited vertex is on the stack, then it is the evidence that we have a cycle.
                cycle = new LinkedList<>();
                for (int vertex = sourceVertex; vertex != neighbour; vertex = route[vertex]) {
                    cycle.addFirst(vertex);
                }
                cycle.addFirst(neighbour);
                cycle.addFirst(sourceVertex);
            }
        }
        onStack[sourceVertex] = false;
    }
}
