package week1.path;

import week1.graph.Graph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Performs the breadth first search (hence the name BfsPath) to find a path from
 * the source vertex passed to the constructor and any other vertex in a graph.
 * The path found by using the BFS algorithm yields the shortest one.
 *
 * @author Renat Kaitmazov
 */

public final class BfsPath extends AbstractGraphPath {

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    public BfsPath(Graph graph, int sourceVertex) {
        super(graph, sourceVertex);
    }

    /*--------------------------------------------------------*/
    /* AbstractGraphSearch implementation                     */
    /*--------------------------------------------------------*/

    @Override
    protected void search(Graph graph, int sourceVertex) {
        final Queue<Integer> verticesQueue = new LinkedList<>();
        exploreNextVertex(verticesQueue, sourceVertex, sourceVertex);
        while (!verticesQueue.isEmpty()) {
            final int visitedVertex = verticesQueue.remove();
            for (final int neighbour : graph.adjacentVertices(visitedVertex)) {
                if (!visitedVertices[neighbour]) {
                    exploreNextVertex(verticesQueue, neighbour, visitedVertex);
                }
            }
        }
    }

    /*--------------------------------------------------------*/
    /* Helper methods                                         */
    /*--------------------------------------------------------*/

    private void exploreNextVertex(Queue<Integer> queue, int vertex, int parentVertex) {
        visitedVertices[vertex] = true;
        route[vertex] = parentVertex;
        queue.add(vertex);
    }
}
