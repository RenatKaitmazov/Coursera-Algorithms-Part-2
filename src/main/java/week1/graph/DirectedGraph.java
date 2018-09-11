package week1.graph;

import java.util.Set;

/**
 * @author Renat Kaitmazov
 */

public final class DirectedGraph extends AbstractGraph {

    /*--------------------------------------------------------*/
    /* Fields                                                 */
    /*--------------------------------------------------------*/

    private int[] inDegrees;

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    public DirectedGraph(int vertexCount) {
        super(vertexCount);
        inDegrees = new int[vertexCount];
    }

    public DirectedGraph(String pathToFile) {
        super(pathToFile);
    }

    // This method is called from the parent's constructor, so it is a part of initialization.
    @Override
    protected void onVertexCountExtractedFromFile(int vertexCount) {
        inDegrees = new int[vertexCount];
    }

    /*--------------------------------------------------------*/
    /* AbstractGraph implementation                           */
    /*--------------------------------------------------------*/

    @Override
    protected boolean add(int fromVertex, int toVertex) {
        final Set<Integer> fromVertexAdjacentVertices = adjacentInternal(fromVertex);
        final boolean success = fromVertexAdjacentVertices.add(toVertex);
        if (success) {
            inDegrees[toVertex]++;
        }
        return success;
    }

    @Override
    public int inDegree(int ofVertex) {
        checkVertexRange(ofVertex);
        return inDegrees[ofVertex];
    }

    /*--------------------------------------------------------*/
    /* API                                                    */
    /*--------------------------------------------------------*/

    public DirectedGraph reverse() {
        final int vertexCount = vertices();
        final DirectedGraph reversed = new DirectedGraph(vertexCount);
        for (int vertex = 0; vertex < vertexCount; ++vertex) {
            for (final int neighbour : adjacentVertices(vertex)) {
                reversed.addEdge(neighbour, vertex);
            }
        }
        return reversed;
    }
}
