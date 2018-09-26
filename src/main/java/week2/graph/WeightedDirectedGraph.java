package week2.graph;

/**
 * Represents a graph whose edges have weights and are directed.
 *
 * @author Renat Kaitmazov
 */

public final class WeightedDirectedGraph {

    /*--------------------------------------------------------*/
    /* Fields                                                 */
    /*--------------------------------------------------------*/

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    public WeightedDirectedGraph(int vertexCount) {

    }

    public WeightedDirectedGraph(String pathToFile) {

    }

    /*--------------------------------------------------------*/
    /* WeightedGraph implementation                           */
    /*--------------------------------------------------------*/

    public int vertexCount() {
        return 0;
    }

    public int edgeCount() {
        return 0;
    }

    public boolean addEdge(DirectedEdge edge) {
        return false;
    }

    public Iterable<Edge> adjacentEdges(int ofVertex) {
        return null;
    }

    public Iterable<DirectedEdge> edges() {
        return null;
    }

    public int inDegree(int ofVertex) {
        return 0;
    }

    public int outDegree(int ofVertex) {
        return 0;
    }

    /*--------------------------------------------------------*/
    /* Helper methods                                         */
    /*--------------------------------------------------------*/
}
