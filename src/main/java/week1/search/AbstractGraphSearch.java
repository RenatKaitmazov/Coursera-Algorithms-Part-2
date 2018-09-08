package week1.search;

import week1.graph.Graph;

/**
 * Defines an API to process a graph using a search algorithms
 * which is implemented by subclassing this class.
 *
 * @author Renat Kaitmazov
 */

public abstract class AbstractGraphSearch {

    /*--------------------------------------------------------*/
    /* Fields                                                 */
    /*--------------------------------------------------------*/

    private final int vertexCount;

    /**
     * Keeps track of what vertex is visited so that we won't process the same vertex more than one time.
     */
    protected final boolean[] visitedVertices;

    /**
     * Keeps track of how many vertices are connected to the source vertex.
     */
    protected int connectedVerticesCount;

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    public AbstractGraphSearch(Graph graph, int sourceVertex) {
        vertexCount = graph.vertices();
        checkVertexRange(sourceVertex);
        visitedVertices = new boolean[vertexCount];
        search(graph, sourceVertex);
    }

    /*--------------------------------------------------------*/
    /* API                                                    */
    /*--------------------------------------------------------*/

    /**
     * Checks to see if the given vertex is connected to the source vertex.
     *
     * @param vertex some vertex from the graph passed to the constructor.
     * @return <code>true</code> if the given vertex is connected to the source vertex, <code>false</code>
     * otherwise.
     */
    public boolean isConnectedTo(int vertex) {
        checkVertexRange(vertex);
        return visitedVertices[vertex];
    }

    /**
     * Returns the amount of vertices connected to the source vertex in the given graph.
     *
     * @return the amount of vertices connected to the source vertex including the source vertex.
     */
    public int count() {
        return connectedVerticesCount;
    }

    /*--------------------------------------------------------*/
    /* Abstract methods                                       */
    /*--------------------------------------------------------*/

    // Perform the actual search.
    protected abstract void search(Graph graph, int sourceVertex);

    /*--------------------------------------------------------*/
    /* Helper methods                                         */
    /*--------------------------------------------------------*/

    private void checkVertexRange(int vertex) {
        if (vertex < 0 || vertex >= vertexCount) {
            throw new IllegalArgumentException("The graph does not have vertex: " + vertex);
        }
    }
}
