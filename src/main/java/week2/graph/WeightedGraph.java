package week2.graph;

/**
 * Defines a common API for a graph where its edges have weights.
 *
 * @author Renat Kaitmazov
 */

public interface WeightedGraph {

    /**
     * Returns number of vertices in the graph.
     *
     * @return number of vertices.
     */
    int vertexCount();

    /**
     * Returns number of edges between vertices in the graph.
     *
     * @return number of edges.
     */
    int edgeCount();

    /**
     * Adds an edge between two vertices.
     *
     * @param edge an edge to be added.
     * @return <code>true</code> if the edge was successfully added, <code>false</code> otherwise.
     */
    boolean addEdge(Edge edge);

    /**
     * Returns a collection of edges adjacent to the given vertex.
     *
     * @param ofVertex a vertex whose adjacent edges need to be returned.
     * @return a collection of edges adjacent to the given vertex, <code>null</code> if the vertex does not
     * have any.
     */
    Iterable<Edge> adjacentEdges(int ofVertex);

    /**
     * Returns a collection of all edges of the graph.
     *
     * @return an iterable collection of all edges in the graph.
     */
    Iterable<Edge> edges();

    /**
     * Returns the amount of edges pointing from other vertices to the given vertex.
     *
     * @param ofVertex a vertex whose incoming degree we want to know.
     * @return number of incoming edges.
     */
    int inDegree(int ofVertex);

    /**
     * Returns the amount of edges pointing from the given vertex to other vertices.
     *
     * @param ofVertex a vertex whose outgoing degree we want to know.
     * @return number of outgoing edges.
     */
    int outDegree(int ofVertex);
}
