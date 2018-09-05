package week1;

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
    public boolean add(int fromVertex, int toVertex) {
        if (fromVertex == toVertex) {
            // Self loops are not allowed.
            return false;
        }
        final Set<Integer> sourceAdjacentVertices = adjacentInternal(fromVertex);
        if (sourceAdjacentVertices.contains(toVertex)) {
            // Already added. Parallel edges are not allowed, so we are done.
            return false;
        }
        final Set<Integer> destinationAdjacentVertices = adjacentInternal(toVertex);
        return sourceAdjacentVertices.add(toVertex)
                && destinationAdjacentVertices.add(fromVertex);
    }
}
