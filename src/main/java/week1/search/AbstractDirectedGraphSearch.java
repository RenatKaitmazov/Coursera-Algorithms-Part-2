package week1.search;

import week1.graph.Graph;

/**
 * @author Renat Kaitmazov
 */

public abstract class AbstractDirectedGraphSearch {

    /*--------------------------------------------------------*/
    /* Fields                                                 */
    /*--------------------------------------------------------*/

    private final int vertexCount;

    /**
     * Keeps track of what vertex is visited so that we won't process the same vertex more than one time.
     */
    protected final boolean[] visitedVertices;

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    public AbstractDirectedGraphSearch(Graph graph, int sourceVertex) {
        vertexCount = graph.vertices();
        checkVertexRange(sourceVertex);
        visitedVertices = new boolean[vertexCount];
        search(graph, sourceVertex);
    }

    public AbstractDirectedGraphSearch(Graph graph, Iterable<Integer> sourceVertices) {
        vertexCount = graph.vertices();
        visitedVertices = new boolean[vertexCount];
        for (final int vertex : sourceVertices) {
            checkVertexRange(vertex);
            if (!visitedVertices[vertex]) {
                search(graph, vertex);
            }
        }
    }

    /*--------------------------------------------------------*/
    /* Abstract methods                                       */
    /*--------------------------------------------------------*/

    /**
     * Checks to see if there is a connection from the source vertex or any of the vertices
     * to the given vertex.
     *
     * @param vertex the end point of connection.
     * @return <code>true</code> if there is a connection, <code>false</code> otherwise.
     */
    public boolean isConnected(int vertex) {
        checkVertexRange(vertex);
        return visitedVertices[vertex];
    }

    /*--------------------------------------------------------*/
    /* Helper methods                                         */
    /*--------------------------------------------------------*/

    private void checkVertexRange(int vertex) {
        if (vertex < 0 || vertex >= vertexCount) {
            throw new IllegalArgumentException("The graph does not have vertex: " + vertex);
        }
    }

    protected abstract void search(Graph graph, int sourceVertex);
}
