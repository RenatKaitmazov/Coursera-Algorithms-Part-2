package week1.graph;

/**
 * Defines an API common to all types of graphs.
 *
 * @author Renat Kaitmazov
 */

public interface Graph {

    /**
     * Returns number of vertices in the graph.
     *
     * @return number of vertices.
     */
    int vertices();

    /**
     * Returns number of edges between vertices in the graph.
     *
     * @return number of edges.
     */
    int edges();

    /**
     * Adds an edge between two vertices.
     *
     * @param fromVertex the start point of the edge.
     * @param toVertex the end point of the edge.
     * @return <code>true</code> if the edge was successfully added, <code>false</code> otherwise.
     */
    boolean addEdge(int fromVertex, int toVertex);

    /**
     * Returns a collection of vertices adjacent to the given vertex.
     *
     * @param ofVertex a vertex whose adjacent vertices need to be returned.
     * @return a collection of vertices adjacent to the given vertex, <code>null</code> if the vertex does not
     * have any.
     */
    Iterable<Integer> adjacentVertices(int ofVertex);

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

    /**
     * Checks to see if there is an edge between the given two vertices.
     *
     * @param fromVertex the start of the edge.
     * @param toVertex the end of the edge.
     * @return <code>true</code> if there is an edge between the start and end vertices, <code>false</code> otherwise.
     */
    boolean hasEdge(int fromVertex, int toVertex);
}
