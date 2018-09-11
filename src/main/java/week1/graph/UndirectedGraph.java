package week1.graph;

import java.util.Set;

/**
 * @author Renat Kaitmazov
 */

public final class UndirectedGraph extends AbstractGraph {

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    public UndirectedGraph(int vertexCount) {
        super(vertexCount);
    }

    public UndirectedGraph(String pathToFile) {
        super(pathToFile);
    }

    /*--------------------------------------------------------*/
    /* AbstractGraph implementation                           */
    /*--------------------------------------------------------*/

    @Override
    public int inDegree(int ofVertex) {
        // In an undirected graph the number of incoming degrees is the same as the number of outgoing degrees.
        return outDegree(ofVertex);
    }

    @Override
    protected boolean add(int fromVertex, int toVertex) {
        final Set<Integer> sourceAdjacentVertices = adjacentInternal(fromVertex);
        final Set<Integer> destinationAdjacentVertices = adjacentInternal(toVertex);
        return sourceAdjacentVertices.add(toVertex)
                && destinationAdjacentVertices.add(fromVertex);
    }
}
