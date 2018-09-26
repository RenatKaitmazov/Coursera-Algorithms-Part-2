package week2.path;

/**
 * Represents an edge in a directed weighted graph.
 *
 * @author Renat Kaitmazov
 */

public final class DirectedEdge {

    /*--------------------------------------------------------*/
    /* Fields                                                 */
    /*--------------------------------------------------------*/

    private final int from;
    private final int to;
    private final double weight;

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    public DirectedEdge(int from, int to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    /*--------------------------------------------------------*/
    /* Overridden methods                                     */
    /*--------------------------------------------------------*/

    @Override
    public String toString() {
        return String.format("(%d->%d|%.4f)", from, to, weight);
    }

    /*--------------------------------------------------------*/
    /* Getters                                                */
    /*--------------------------------------------------------*/

    public int from() {
        return from;
    }

    public int to() {
        return to;
    }

    public double weight() {
        return weight;
    }
}
