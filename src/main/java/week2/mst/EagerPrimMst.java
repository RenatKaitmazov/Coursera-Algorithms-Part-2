package week2.mst;

import week2.graph.Edge;
import week2.graph.WeightedUndirectedGraph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This is an eager version of the Prim's algorithm to find the minimum spanning tree in a weighted graph.
 * It works as follows:
 * • we start with some initial vertex S and put it on the priority queue.
 * • we then remove the closest vertex to the MST.
 * • we add all vertices adjacent to the removed vertex if they are not already on the queue.
 * However, if they are on the queue and the distance from the adjacent vertex to the MST is smaller
 * than it was before, we update the distance
 * • we repeat the same process until the queue is empty.
 *
 * @author Renat Kaitmazov
 */

public final class EagerPrimMst implements MinSpanningTree {

    /*--------------------------------------------------------*/
    /* Fields                                                 */
    /*--------------------------------------------------------*/

    private final Queue<Edge> mstEdges = new LinkedList<>();
    private double totalWeight;

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    public EagerPrimMst(WeightedUndirectedGraph graph) {
        // The graph is expected to be connected.
        // Perform set up.
        final int vertexCount = graph.vertexCount();
        // Stores all vertices on the MST.
        final boolean[] verticesOnMst = new boolean[vertexCount];
        // Stores distances from vertices not on the MST to the MST.
        final double[] distanceTo = new double[vertexCount];
        for (int i = 0; i < vertexCount; ++i) {
            distanceTo[i] = Double.POSITIVE_INFINITY;
        }
        // Stores edges from vertices not on the MST to the MST.
        final Edge[] edgeTo = new Edge[vertexCount];
        // Stores vertices not on the MST based on their distance to the MST.
        // The closest vertex is the first one to be removed.
        final IndexMinPriorityQueue<Double> queue = new IndexMinPriorityQueue<>(vertexCount);
        final int startVertex = 0;
        final double startDistance = 0.0;
        queue.insert(startVertex, startDistance);
        while (!queue.isEmpty()) {
            // Delete the closest vertex to the MST
            final int minVertex = queue.delete();
            if (minVertex != startVertex) {
                totalWeight += distanceTo[minVertex];
                mstEdges.add(edgeTo[minVertex]);
            }
            //  Explore other vertices adjacent to the closest vertex.
            explore(graph, verticesOnMst, distanceTo, edgeTo, queue, minVertex);
        }
    }

    /*--------------------------------------------------------*/
    /* MinSpanningTree implementation                         */
    /*--------------------------------------------------------*/

    @Override
    public Iterable<Edge> edges() {
        return mstEdges;
    }

    @Override
    public double weight() {
        return totalWeight;
    }

    /*--------------------------------------------------------*/
    /* Helper methods                                         */
    /*--------------------------------------------------------*/

    private void explore(WeightedUndirectedGraph graph,
                         boolean[] verticesOnMst,
                         double[] distanceTo,
                         Edge[] edgeTo,
                         IndexMinPriorityQueue<Double> queue,
                         int vertex) {
        // Mark the vertex as being on the MST
        verticesOnMst[vertex] = true;
        for (final Edge edge : graph.adjacentEdges(vertex)) {
            // Get a vertex on the other side of the edge.
            final int otherVertex = edge.otherVertex(vertex);
            // If the other vertex is already on the MST, examine the
            if (verticesOnMst[otherVertex]) continue;
            final double newDistanceToMst = edge.weight();
            final double oldDistanceToMst = distanceTo[otherVertex];
            // Found a better way to connect the other vertex to the MST
            if (newDistanceToMst < oldDistanceToMst) {
                // Update data.
                edgeTo[otherVertex] = edge;
                distanceTo[otherVertex] = newDistanceToMst;
                if (queue.contains(otherVertex)) {
                    // If the other vertex is already on the priority queue, then decrease its weight.
                    queue.decreaseKey(otherVertex, newDistanceToMst);
                } else {
                    // Otherwise, just add it to the queue.
                    queue.insert(otherVertex, newDistanceToMst);
                }
            }
        }
    }
}
