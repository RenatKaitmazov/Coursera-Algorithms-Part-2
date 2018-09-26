package week2.graph;

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
        return String.format("(%d->%d|%.6f)", from, to, weight);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        final DirectedEdge that = (DirectedEdge) obj;
        return this.from == that.from
                && this.to == that.to
                && this.weight == that.weight;
    }

    @Override
    public int hashCode() {
        int result = 3;
        result = 31 * result + from;
        result = 31 * result + to;
        result = 31 * result + Double.hashCode(weight);
        return result;
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
