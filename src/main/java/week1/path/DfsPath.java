package week1.path;

import week1.graph.Graph;

/**
 * Performs the depth first search (hence the name DfsPath) to find a path from
 * the source vertex passed to the constructor and any other vertex in a graph.
 * The path found by using the DFS algorithm is not necessarily the shortest one.
 *
 * @author Renat Kaitmazov
 */

public final class DfsPath extends AbstractGraphPath {

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    public DfsPath(Graph graph, int sourceVertex) {
        super(graph, sourceVertex);
    }

    /*--------------------------------------------------------*/
    /* AbstractGraphPath implementation                       */
    /*--------------------------------------------------------*/

    @Override
    protected void search(Graph graph, int sourceVertex) {
        visitedVertices[sourceVertex] = true;
        for (final int neighbour : graph.adjacentVertices(sourceVertex)) {
            if (!visitedVertices[neighbour]) {
                route[neighbour] = sourceVertex;
                search(graph, neighbour);
            }
        }
    }
}
