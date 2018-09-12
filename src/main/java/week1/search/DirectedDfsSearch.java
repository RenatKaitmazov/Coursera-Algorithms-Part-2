package week1.search;

import week1.graph.Graph;

/**
 * Performs the depth first search (hence the name DirectedDfsSearch) on the given graph.
 *
 * @author Renat Kaitmazov
 */

public class DirectedDfsSearch extends AbstractDirectedGraphSearch {

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    public DirectedDfsSearch(Graph graph, int sourceVertex) {
        super(graph, sourceVertex);
    }

    public DirectedDfsSearch(Graph graph, Iterable<Integer> sourceVertices) {
        super(graph, sourceVertices);
    }

    /*--------------------------------------------------------*/
    /* AbstractDirectedGraphSearch implementation             */
    /*--------------------------------------------------------*/

    @Override
    protected void search(Graph graph, int sourceVertex) {
        visitedVertices[sourceVertex] = true;
        for (final int neighbour : graph.adjacentVertices(sourceVertex)) {
            if (!visitedVertices[neighbour]) {
                search(graph, neighbour);
            }
        }
    }
}
