package week1.path;

import week1.graph.Graph;

/**
 * Defines an API to work with paths in graphs.
 *
 * @author Renat Kaitmazov
 */

public abstract class AbstractGraphPath {

    /*--------------------------------------------------------*/
    /* Helper methods                                         */
    /*--------------------------------------------------------*/

    private final int vertexCount;
    protected final int sourceVertex;

    /**
     * Keeps track of what vertex is visited so that we won't process the same vertex more than one time.
     */
    protected final boolean[] visitedVertices;

    /**
     * Allows us to reestablish a path from the source vertex to any other given vertex which is connected
     * to the source vertex.
     */
    protected final int[] route;

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    public AbstractGraphPath(Graph graph, int sourceVertex) {
        vertexCount = graph.vertices();
        checkVertexRange(sourceVertex);
        this.sourceVertex = sourceVertex;
        visitedVertices = new boolean[vertexCount];
        route = new int[vertexCount];
        search(graph, sourceVertex);
    }

    /*--------------------------------------------------------*/
    /* API                                                    */
    /*--------------------------------------------------------*/

    public boolean hasPathTo(int vertex) {
        checkVertexRange(vertex);
        return visitedVertices[vertex];
    }

    public abstract Iterable<Integer> pathTo(int vertex);

    /*--------------------------------------------------------*/
    /* Abstract methods                                       */
    /*--------------------------------------------------------*/

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
