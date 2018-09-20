package week2.mst;

import week2.graph.Edge;
import week2.graph.WeightedUndirectedGraph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * This is Kruskal's algorithm to find the minimum spanning tree in a graph.
 *
 * @author Renat Kaitmazov
 */

public class KruskalMst implements MinSpanningTree {

    /*--------------------------------------------------------*/
    /* Fields                                                 */
    /*--------------------------------------------------------*/

    private final Queue<Edge> edges = new LinkedList<>();
    private double totalWeight;

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    public KruskalMst(WeightedUndirectedGraph graph) {
        final int vertexCount = graph.vertexCount();
        final Queue<Edge> minWeightEdgeQueue = new PriorityQueue<>(graph.edgeCount());
        for (final Edge edge : graph.edges()) {
            // Sort edges in ascending order based on their weight using a priority queue.
            minWeightEdgeQueue.add(edge);
        }
        final UnionFind unionFind = new UnionFind();
        final int mstSize = vertexCount - 1;
        while (!minWeightEdgeQueue.isEmpty() && edges.size() < mstSize) {
            // Remove the current min edge.
            final Edge minEdge = minWeightEdgeQueue.remove();
            // Get its start and end points.
            final int start = minEdge.eitherVertex();
            final int end = minEdge.otherVertex(start);
            // If adding this edge to the current MST creates a cycle, then ignore the edge.
            if (unionFind.isConnected(start, end)) continue;
            unionFind.union(start, end);
            edges.add(minEdge);
            totalWeight += minEdge.weight();
        }
    }

    /*--------------------------------------------------------*/
    /* MinSpanningTree implementation                         */
    /*--------------------------------------------------------*/

    @Override
    public Iterable<Edge> edges() {
        return edges;
    }

    @Override
    public double weight() {
        return totalWeight;
    }

    /*--------------------------------------------------------*/
    /* Nested classes                                         */
    /*--------------------------------------------------------*/

    private static final class UnionFind {

        /*--------------------------------------------------------*/
        /* Fields                                                 */
        /*--------------------------------------------------------*/

        private final Map<Integer, Integer> roots = new HashMap<>();
        private final Map<Integer, Integer> weights = new HashMap<>();

        /*--------------------------------------------------------*/
        /* API                                                    */
        /*--------------------------------------------------------*/

        void union(int vertex1, int vertex2) {
            final int root1 = rootOf(vertex1);
            final int root2 = rootOf(vertex2);
            if (root1 == root2) return;
            final int weight1 = getWeight(root1);
            final int weight2 = getWeight(root2);
            final int totalWeight = weight1 + weight2;
            if (weight1 < weight2) {
                roots.put(root1, root2);
                weights.put(root2, totalWeight);
            } else {
                roots.put(root2, root1);
                weights.put(root1, totalWeight);
            }
        }

        boolean isConnected(int vertex1, int vertex2) {
            return rootOf(vertex1) == rootOf(vertex2);
        }

        /*--------------------------------------------------------*/
        /* Helper methods                                         */
        /*--------------------------------------------------------*/

        private int rootOf(int vertex) {
            int v = vertex;
            int parent = roots.getOrDefault(v, v);
            while (v != parent) {
                final int grandparent = roots.getOrDefault(parent, parent);
                roots.put(v, grandparent);
                v = parent;
                parent = grandparent;
            }
            return v;
        }

        private int getWeight(int vertex) {
            final Integer weight = weights.get(vertex);
            if (weight == null) {
                return 1;
            }
            return weight;
        }
    }
}
