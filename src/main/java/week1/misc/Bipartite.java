package week1.misc;

import week1.graph.Graph;

/**
 * This class lets us determine if a graph can be colored using only two colors, so that any
 * two adjacent vertices are not the same color.
 *
 * @author Renat Kaitmazov
 */

public final class Bipartite {

    /*--------------------------------------------------------*/
    /* Fields                                                 */
    /*--------------------------------------------------------*/

    private boolean isBipartite = true;

    /**
     * Keeps track of what vertex is visited so that we won't process the same vertex more than one time.
     */
    private final boolean[] visitedVertices;

    /**
     * Keeps track of what vertex has what color.
     */
    private final boolean[] colors;

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    public Bipartite(Graph graph) {
        final int vertexCount = graph.vertices();
        visitedVertices = new boolean[vertexCount];
        colors = new boolean[vertexCount];
        for (int vertex = 0; vertex < vertexCount; ++vertex) {
            if (!visitedVertices[vertex]) {
                checkIfBipartite(graph, vertex);
            }
            if (!isBipartite) {
                break;
            }
        }
    }

    /*--------------------------------------------------------*/
    /* API                                                    */
    /*--------------------------------------------------------*/

    public boolean isBipartite() {
        return isBipartite;
    }

    /*--------------------------------------------------------*/
    /* Helper methods                                         */
    /*--------------------------------------------------------*/

    private void checkIfBipartite(Graph graph, int parentVertex) {
        visitedVertices[parentVertex] = true;
        for (final int neighbour : graph.adjacentVertices(parentVertex)) {
            if (!visitedVertices[neighbour]) {
                visitedVertices[parentVertex] = true;
                // Make the neighbour's color opposite to its parent color.
                colors[neighbour] = !colors[parentVertex];
            } else if (haveSameColor(neighbour, parentVertex)) {
                isBipartite = false;
                return;
            }
        }
    }

    private boolean haveSameColor(int vertex1, int vertex2) {
        return colors[vertex1] == colors[vertex2];
    }
}
