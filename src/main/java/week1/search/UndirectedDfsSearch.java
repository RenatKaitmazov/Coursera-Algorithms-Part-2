package week1.search;

import week1.graph.Graph;

/**
 * Performs the depth first search (hence the name UndirectedDfsSearch) on the given graph.
 *
 * @author Renat Kaitmazov
 */

public final class UndirectedDfsSearch extends AbstractUndirectedGraphSearch {

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    public UndirectedDfsSearch(Graph graph, int sourceVertex) {
        super(graph, sourceVertex);
    }

    /*--------------------------------------------------------*/
    /* AbstractUndirectedGraphSearch implementation           */
    /*--------------------------------------------------------*/

    @Override
    protected void search(Graph graph, int sourceVertex) {
        // Mark as visited
        visitedVertices[sourceVertex] = true;
        // Increment the amount of vertices connected to the source
        ++connectedVerticesCount;
        // Repeat the same for the rest of unvisited vertices using the system stack.
        for (final int neighbour : graph.adjacentVertices(sourceVertex)) {
            if (!visitedVertices[neighbour]) {
                // Explore the next vertex only if it has not been visited so far.
                search(graph, neighbour);
            }
        }
    }
}
