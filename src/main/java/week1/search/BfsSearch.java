package week1.search;

import week1.graph.Graph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Performs the breadth first search (hence the name BfsSearch) on the
 *
 * @author Renat Kaitmazov
 */

public final class BfsSearch extends AbstractGraphSearch {

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    public BfsSearch(Graph graph, int sourceVertex) {
        super(graph, sourceVertex);
    }

    /*--------------------------------------------------------*/
    /* AbstractGraphSearch implementation                     */
    /*--------------------------------------------------------*/

    @Override
    protected void search(Graph graph, int sourceVertex) {
        // To perform the BFS search we need to use a queue.
        final Queue<Integer> verticesQueue = new LinkedList<>();
        exploreNextVertex(verticesQueue, sourceVertex);
        while (!verticesQueue.isEmpty()) {
            final int visitedVertex = verticesQueue.remove();
            for (final int neighbour : graph.adjacentVertices(visitedVertex)) {
                if (!visitedVertices[neighbour]) {
                    exploreNextVertex(verticesQueue, neighbour);
                }
            }
        }
    }

    /*--------------------------------------------------------*/
    /* Helper methods                                         */
    /*--------------------------------------------------------*/

    private void exploreNextVertex(Queue<Integer> queue, int vertex) {
        queue.add(vertex);
        visitedVertices[vertex] = true;
        ++connectedVerticesCount;
    }
}
