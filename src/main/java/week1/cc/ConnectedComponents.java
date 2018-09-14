package week1.cc;

import week1.graph.UndirectedGraph;

/**
 * Finds connected components of a graph.
 *
 * @author Renat Kaitmazov
 */

public final class ConnectedComponents {

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

    public ConnectedComponents(UndirectedGraph graph) {
        vertexCount = graph.vertices();
        visitedVertices = new boolean[vertexCount];
        ids = new int[vertexCount];
        for (int vertex = 0; vertex < vertexCount; ++vertex) {
            if (!visitedVertices[vertex]) {
                countComponents(graph, vertex);
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
    public boolean isConnected(int fromVertex, int toVertex) {
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

    private void countComponents(UndirectedGraph graph, int sourceVertex) {
        visitedVertices[sourceVertex] = true;
        ids[sourceVertex] = componentsCount;
        for (final int neighbour : graph.adjacentVertices(sourceVertex)) {
            if (!visitedVertices[neighbour]) {
                countComponents(graph, neighbour);
            }
        }
    }
}
