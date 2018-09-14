package week1.cc;

import week1.graph.DirectedGraph;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Calculates the amount of components in a directed graph.
 * Uses the Kosaraju’s algorithm to do the job.
 * Here are steps:
 * • reverse the original graph G. Lets call it GR.
 * • compute the reversed post order of GR.
 * • perform a standard DFS search on G, where the source vertex is determined by the order
 * computed in the previous step instead of standard numerical order.
 *
 * @author Renat Kaitmazov
 */

public class StronglyConnectedComponents {

    /*--------------------------------------------------------*/
    /* Fields                                                 */
    /*--------------------------------------------------------*/

    private final int vertexCount;

    private int componentsCount;

    /**
     * Keeps track of what vertex is visited so that we won't process the same vertex more than one time.
     */
    private final boolean[] visitedVertices;

    /**
     * Stores IDs of vertices. Vertices from the same connected component have the same ID.
     */
    private final int[] ids;

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    public StronglyConnectedComponents(DirectedGraph graph) {
        vertexCount = graph.vertices();
        visitedVertices = new boolean[vertexCount];
        ids = new int[vertexCount];
        final DirectedGraph reversedGraph = graph.reverse();
        final ReversedPostOrder postOrder = new ReversedPostOrder(reversedGraph);
        for (final int vertex : postOrder.postOrder()) {
            if (!visitedVertices[vertex]) {
                countStrongComponents(graph, vertex);
                ++componentsCount;
            }
        }
    }

    /*--------------------------------------------------------*/
    /* API                                                    */
    /*--------------------------------------------------------*/

    /**
     * Checks to see if the given two vertices are connected.
     *
     * @param fromVertex the first vertex.
     * @param toVertex the second vertex.
     * @return <code>true</code> if they are connected, <code>false</code> otherwise.
     */
    public boolean isStronglyConnected(int fromVertex, int toVertex) {
        return componentId(fromVertex) == componentId(toVertex);
    }

    /**
     * Returns the amount of connected components in a graph.
     *
     * @return number of components.
     */
    public int count() {
        return componentsCount;
    }

    /**
     * Returns a unique id assigned to each vertex when processing a graph.
     * The id allows us to determine which vertex belongs to which component.
     *
     * @param vertex a vertex whose id we want to know.
     * @return the id of the given vertex.
     */
    public int componentId(int vertex) {
        checkVertexRange(vertex);
        return ids[vertex];
    }

    /*--------------------------------------------------------*/
    /* Helper methods                                         */
    /*--------------------------------------------------------*/

    private void checkVertexRange(int vertex) {
        if (vertex < 0 || vertex >= vertexCount) {
            throw new IllegalArgumentException("The graph does not have vertex: " + vertex);
        }
    }

    private void countStrongComponents(DirectedGraph graph, int sourceVertex) {
        visitedVertices[sourceVertex] = true;
        ids[sourceVertex] = componentsCount;
        for (final int neighbour : graph.adjacentVertices(sourceVertex)) {
            if (!visitedVertices[neighbour]) {
                countStrongComponents(graph, neighbour);
            }
        }
    }

    /*--------------------------------------------------------*/
    /* Nested classes                                         */
    /*--------------------------------------------------------*/

    private static final class ReversedPostOrder {

        /*--------------------------------------------------------*/
        /* Fields                                                 */
        /*--------------------------------------------------------*/

        private final Deque<Integer> postOrder;
        private final boolean[] visitedVertices;

        /*--------------------------------------------------------*/
        /* Constructors                                           */
        /*--------------------------------------------------------*/

        ReversedPostOrder(DirectedGraph graph) {
            final int size = graph.vertices();
            postOrder = new ArrayDeque<>(size);
            visitedVertices = new boolean[size];
            for (int vertex = 0; vertex < size; ++vertex) {
                if (!visitedVertices[vertex]) {
                    search(graph, vertex);
                }
            }
        }

        /*--------------------------------------------------------*/
        /* API                                                    */
        /*--------------------------------------------------------*/

        public Iterable<Integer> postOrder() {
            return postOrder;
        }

        /*--------------------------------------------------------*/
        /* Helper methods                                         */
        /*--------------------------------------------------------*/

        private void search(DirectedGraph graph, int sourceVertex) {
            visitedVertices[sourceVertex] = true;
            for (final int neighbour : graph.adjacentVertices(sourceVertex)) {
                if (!visitedVertices[neighbour]) {
                    search(graph, neighbour);
                }
            }
            postOrder.addFirst(sourceVertex);
        }
    }
}
