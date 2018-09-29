package week2.path;

import week2.graph.DirectedEdge;

/**
 * This algorithm computes the longest path in a DAG.
 *
 * @author Renat Kaitmazov
 */

public final class DagLongestPath implements WeightedPath {

    /*--------------------------------------------------------*/
    /* WeightedPath implementation                            */
    /*--------------------------------------------------------*/

    @Override
    public boolean hasPath(int toVertex) {
        return false;
    }

    @Override
    public Iterable<DirectedEdge> path(int toVertex) {
        return null;
    }

    @Override
    public double cost(int toVertex) {
        return 0;
    }
}
