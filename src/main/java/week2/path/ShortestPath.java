package week2.path;

import week2.graph.DirectedEdge;

/**
 * Defines a common API for algorithms that find the shortest path in a weighted directed graph
 * from some source vertex S to every other vertex in the graph.
 *
 * @author Renat Kaitmazov
 */

public interface ShortestPath {

    /**
     * Checks to see if there is a path from the source vertex to the given vertex.
     *
     * @param toVertex the endpoint of a path.
     * @return <code>true</code> if there is such a path, <code>false</code> otherwise.
     */
    boolean hasPath(int toVertex);

    /**
     * Returns a path from some source vertex to the given vertex represented as a collection of
     * directed edges.
     *
     * @param toVertex the end point a path.
     * @return an iterable collection of directed edges from the source to the given vertex, <code>null</code>
     * if no such path exists.
     */
    Iterable<DirectedEdge> path(int toVertex);

    /**
     * Returns the total cost of a path from some source vertex to the given vertex.
     *
     * @param toVertex the endpoint of a path.
     * @return total cost of the path, <code>0.0</code> if no such path exists.
     */
    double cost(int toVertex);
}
