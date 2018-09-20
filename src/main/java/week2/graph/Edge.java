package week2.graph;

/**
 * This class represents an edge between two vertices in a graph.
 *
 * @author Renat Kaitmazov
 */

public final class Edge implements Comparable<Edge> {

    /*--------------------------------------------------------*/
    /* Fields                                                 */
    /*--------------------------------------------------------*/

    /**
     * The starts point of the edge.
     */
    private final int fromVertex;
    /**
     * The end point of the edge.
     */
    private final int toVertex;
    /**
     * The weight associated with the edge.
     */
    private final double weight;

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    public Edge(int fromVertex, int toVertex, double weight) {
        this.fromVertex = fromVertex;
        this.toVertex = toVertex;
        this.weight = weight;
    }

    /*--------------------------------------------------------*/
    /* Overridden methods                                     */
    /*--------------------------------------------------------*/

    @Override
    public String toString() {
        return String.format("{start=%d,end=%d,weight=%.6f}", fromVertex, toVertex, weight);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        final Edge that = (Edge) obj;
        return (this.fromVertex == that.fromVertex && this.toVertex == that.toVertex && this.weight == that.weight)
                || (this.fromVertex == that.toVertex && this.toVertex == that.fromVertex && this.weight == that.weight);
    }

    @Override
    public int hashCode() {
        int result = 3;
        result = 31 * result + fromVertex;
        result = 31 * result + toVertex;
        result = 31 * result + Double.hashCode(weight);
        return result;
    }

    /*--------------------------------------------------------*/
    /* Comparable implementation                              */
    /*--------------------------------------------------------*/

    // Some algorithms (like Kruskal's algorithm) require that edges be comparable
    // in order to sort them.
    @Override
    public int compareTo(Edge that) {
        if (this.weight < that.weight) return -1;
        if (this.weight > that.weight) return +1;
        return 0;
    }

    /*--------------------------------------------------------*/
    /* API                                                    */
    /*--------------------------------------------------------*/

    /**
     * Returns one of the vertices which is usually the start point of the edge.
     *
     * @return one of the two vertices on the edge.
     */
    public int eitherVertex() {
        return fromVertex;
    }

    /**
     * Returns the other vertex related to the edge.
     * The method should be used in conjunction with {@link #eitherVertex()}.
     * The value returned from {@link #eitherVertex()} is the argument to this method.
     * <pre>
     *     <code>
     *         final Edge edge = new Edge(10, 11, 0.12);
     *         final int vertex1 = edge.eitherVertex();
     *         final int vertex2 = edge.otherVertex(vertex1);
     *     </code>
     * </pre>
     *
     * @param firstVertex one the vertices of the edge obtained via {@link #eitherVertex()}.
     * @return the other vertex of this edge opposite to {@code firstVertex}.
     */
    public int otherVertex(int firstVertex) {
        if (firstVertex == fromVertex) return toVertex;
        if (firstVertex == toVertex) return fromVertex;
        throw new IllegalArgumentException("Inconsistent edge");
    }

    /**
     * Returns the weight associated with the edge.
     *
     * @return the edge's weight.
     */
    public double weight() {
        return weight;
    }
}
