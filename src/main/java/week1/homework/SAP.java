package week1.homework;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This class allows to find a common ancestor of two vertices along with the distance to the common ancestor.
 *
 * @author Renat Kaitmazov
 */

public final class SAP {

    /*--------------------------------------------------------*/
    /* Constants                                              */
    /*--------------------------------------------------------*/

    /**
     * An identifier of the first group of vertices.
     */
    private static final byte ID_GROUP_1 = 1;

    /**
     * An identifier of the second group of vertices.
     */
    private static final byte ID_GROUP_2 = 2;

    /*--------------------------------------------------------*/
    /* Fields                                                 */
    /*--------------------------------------------------------*/

    private final Digraph digraph;
    private final int vertexCount;

    /**
     * Keeps track of already visited vertices so that we can efficiently explore the graph without processing the same
     * vertex again.
     * The method of exploration is the breadth first search because we are interested in finding the shortest common
     * ancestral path from one group of vertices to another group of vertices.
     */
    private final boolean[] visited;

    /**
     * Keeps track of the distance from the first group of vertices to the given vertex.
     */
    private final int[] distanceGroup1;

    /**
     * Keeps track of the distance from the second group of vertices to the given vertex.
     */
    private final int[] distanceGroup2;

    /**
     * Keeps track of what vertex belongs to what group.
     */
    private final byte[] groupIds;

    /**
     * This queue keeps track of what vertices have been changed during the computation so that we can reset the results
     * of computation. This allows us to reuse the same resources instead of creating new ones every time we do a
     * computation.
     */
    private final IntQueue indicesOfChangedVertices = new IntQueue();

    /**
     * Caches computations.
     */
    private final CacheEntity[] cache;

    private final List<Integer> from = new LinkedList<>();
    private final List<Integer> to = new LinkedList<>();

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    public SAP(Digraph digraph) {
        Utils.checkNotNull(digraph);
        this.digraph = digraph;
        vertexCount = digraph.V();
        visited = new boolean[vertexCount];
        distanceGroup1 = new int[vertexCount];
        distanceGroup2 = new int[vertexCount];
        for (int i = 0; i < vertexCount; ++i) {
            distanceGroup1[i] = -1;
            distanceGroup2[i] = -1;
        }
        groupIds = new byte[vertexCount];
        cache = new CacheEntity[vertexCount];
        from.add(-1);
        to.add(-1);
    }

    /*--------------------------------------------------------*/
    /* API                                                    */
    /*--------------------------------------------------------*/

    public int length(int fromVertex, int toVertex) {
        checkVertex(fromVertex);
        checkVertex(toVertex);
        if (fromVertex == toVertex) return 0;
        final SapTuple cachedData = getFromCache(fromVertex, toVertex);
        if (cachedData != null) {
            return cachedData.length;
        }
        setVertices(fromVertex, toVertex);
        final SapTuple result = findLengthAndAncestor(from, to, false);
        resetComputationResults();
        putIntoCache(fromVertex, toVertex, result);
        return result.length;
    }

    public int ancestor(int fromVertex, int toVertex) {
        checkVertex(fromVertex);
        checkVertex(toVertex);
        if (fromVertex == toVertex) return fromVertex;
        final SapTuple cachedData = getFromCache(fromVertex, toVertex);
        if (cachedData != null) {
            return cachedData.ancestor;
        }
        setVertices(fromVertex, toVertex);
        final SapTuple result = findLengthAndAncestor(from, to, false);
        resetComputationResults();
        putIntoCache(fromVertex, toVertex, result);
        return result.ancestor;
    }

    public int length(Iterable<Integer> fromVertices, Iterable<Integer> toVertices) {
        final SapTuple result = findLengthAndAncestor(fromVertices, toVertices, true);
        resetComputationResults();
        return result.length;
    }

    public int ancestor(Iterable<Integer> fromVertices, Iterable<Integer> toVertices) {
        final SapTuple result = findLengthAndAncestor(fromVertices, toVertices,true);
        resetComputationResults();
        return result.ancestor;
    }

    /*--------------------------------------------------------*/
    /* Helper methods                                         */
    /*--------------------------------------------------------*/

    private void checkVerticesValidity(Iterable<Integer> vertices) {
        Utils.checkNotNull(vertices);
        for (final Integer vertex : vertices) {
            Utils.checkNotNull(vertex);
            checkVertex(vertex);
        }
    }

    private void checkVertex(int vertex) {
        if (vertex < 0 || vertex >= vertexCount) {
            throw new IllegalArgumentException("Vertex is out of bounds");
        }
    }

    private void resetComputationResults() {
        while (!indicesOfChangedVertices.isEmpty()) {
            final int index = indicesOfChangedVertices.dequeue();
            visited[index] = false;
            distanceGroup1[index] = -1;
            distanceGroup2[index] = -1;
            groupIds[index] = 0;
        }
    }

    private SapTuple getFromCache(int fromVertex, int toVertex) {
        final CacheEntity entity = cache[fromVertex];
        return entity == null ? null : entity.get(toVertex);
    }

    private void putIntoCache(int fromVertex, int toVertex, SapTuple tuple) {
        put(fromVertex, toVertex, tuple);
        put(toVertex, fromVertex, tuple);
    }

    private void put(int fromVertex, int toVertex, SapTuple tuple) {
        if (cache[fromVertex] == null) {
            cache[fromVertex] = new CacheEntity();
        }
        final CacheEntity entity = cache[fromVertex];
        entity.put(toVertex, tuple);
    }

    private SapTuple findLengthAndAncestor(Iterable<Integer> fromVertices,
                                           Iterable<Integer> toVertices,
                                           boolean hasMultipleSources) {
        checkVerticesValidity(fromVertices);
        checkVerticesValidity(toVertices);
        final IntQueue verticesQueue = new IntQueue();
        // Add everything to the queue from both iterables.
        for (final int vertex : fromVertices) {
            // Avoid duplicates
            if (!visited[vertex]) {
                groupIds[vertex] = ID_GROUP_1;
                exploreNextVertex(vertex, vertex, verticesQueue);
            }
        }
        for (final int vertex : toVertices) {
            // Avoid duplicates
            if (!visited[vertex]) {
                groupIds[vertex] = ID_GROUP_2;
                exploreNextVertex(vertex, vertex, verticesQueue);
            } else {
                // If we encountered a vertex which is visited and whose group id is different, then we are asked
                // to perform computation for two identical vertices.
                if (groupIds[vertex] == ID_GROUP_1) {
                    return new SapTuple(0, vertex);
                }
            }
        }
        // Initial assumption is that the best distance is undefined, and there is no a common ancestor.
        int bestDistanceSoFar = Integer.MAX_VALUE;
        final SapTuple result = new SapTuple(-1, -1);
        queueLoop:
        while (!verticesQueue.isEmpty()) {
            final int visitedVertex = verticesQueue.dequeue();
            for (final int adjacentVertex : digraph.adj(visitedVertex)) {
                if (!visited[adjacentVertex]) {
                    exploreNextVertex(adjacentVertex, visitedVertex, verticesQueue);
                } else if (isReachableFromBothVertices(adjacentVertex, visitedVertex)) {
                    // At this point we found a common ancestor
                    // but we are not sure if it is the shortest one.
                    // We also need to update distance.
                    // See the comments for the method for more information.
                    updateDistance(adjacentVertex, visitedVertex);
                    final int distanceFromGroup1 = distanceGroup1[adjacentVertex];
                    final int distanceFromGroup2 = distanceGroup2[adjacentVertex];
                    // There is not point in exploring the digraph further because the distance to the current vertex
                    // is far away from the best distance computed so far.
                    if (distanceFromGroup1 > bestDistanceSoFar && distanceFromGroup2 > bestDistanceSoFar) {
                        break queueLoop;
                    }
                    final int totalDistance = distanceFromGroup1 + distanceFromGroup2;
                    if (totalDistance < bestDistanceSoFar) {
                        // The path to newly found ancestor is shorted than the previous path. Update results.
                        bestDistanceSoFar = totalDistance;
                        result.length = totalDistance;
                        result.ancestor = adjacentVertex;
                        if (hasMultipleSources) {
                            verticesQueue.enqueue(adjacentVertex);
                        }
                    }
                }
            }
        }
        return result;
    }

    private boolean isReachableFromBothVertices(int vertex, int descendant) {
        final int descendantGroupId = groupIds[vertex];
        final int[] distanceFromOtherSource = descendantGroupId == ID_GROUP_1 ? distanceGroup2 : distanceGroup1;
        // One way to determine if the vertex is reachable from both groups is obviously to compare their corresponding
        // group ids. This works great for acyclic digraphs but it does not work if there is a cycle in a digraph.
        // In fact this approach is used to detect only the first common ancestor. The next time reachability is determined
        // using the distance array. If a vertex is reachable from both groups it means that we must know the distance
        // to the vertex from the first group and the second group. If the vertex belongs to the first group and the
        // distance to the same vertex from the second group is positive (and vice versa), then the vertex is definitely reachable from
        // both sources. It is vital to update the distance to the vertex from both sources when we detected for the
        // the first time that a vertex can be reached from both groups.

        /* DO NOT FORGET TO UPDATE THE DISTANCE IN THE EXPLORATION ROUTINE!!! */
        return (groupIds[vertex] != descendantGroupId) || distanceFromOtherSource[descendant] > -1;
    }


    /*  Not only does this method updates the distance to the vertex from both sources, but it is namely this method
     *  that helps our graph exploration routine to determine if a particular vertex is reachable from both groups
     *  of vertices. If you forget to call this method from the routine (after we detected an ancestor for the first time),
     *  then the method isReachableFromBothVertices is going to return false even if the vertex is actually reachable.
     */
    private void updateDistance(int vertex, int descendant) {
        final int vertexGroupId = groupIds[vertex];
        final int descendantGroupId = groupIds[descendant];
        final int[] distanceFromOtherSourceToVertex = vertexGroupId == ID_GROUP_1 ? distanceGroup2 : distanceGroup1;
        if (vertexGroupId == descendantGroupId) {
            distanceFromOtherSourceToVertex[vertex] = distanceFromOtherSourceToVertex[descendant] + 1;
        } else {
            final int[] distanceToDescendant = descendantGroupId == ID_GROUP_1 ? distanceGroup1 : distanceGroup2;
            distanceFromOtherSourceToVertex[vertex] = distanceToDescendant[descendant] + 1;
        }
    }

    private void exploreNextVertex(int vertex, int descendant, IntQueue bfsQueue) {
        final byte groupId = groupIds[descendant];
        groupIds[vertex] = groupId;
        visited[vertex] = true;
        if (groupId == ID_GROUP_1) {
            distanceGroup1[vertex] = distanceGroup1[descendant] + 1;
            final int distanceFromOtherSource = distanceGroup2[descendant];
            if (distanceFromOtherSource > -1) {
                // This allows us to know if the vertex is reachable from both group of vertices.
                distanceGroup2[vertex] = distanceFromOtherSource + 1;
            }
        } else if (groupId == ID_GROUP_2) {
            distanceGroup2[vertex] = distanceGroup2[descendant] + 1;
            final int distanceFromOtherSource = distanceGroup1[descendant];
            if (distanceFromOtherSource > -1) {
                distanceGroup1[vertex] = distanceFromOtherSource + 1;
            }
        }
        bfsQueue.enqueue(vertex);
        indicesOfChangedVertices.enqueue(vertex);
    }

    private void setVertices(int fromVertex, int toVertex) {
        from.set(0, fromVertex);
        to.set(0, toVertex);
    }

    /*--------------------------------------------------------*/
    /* Nested classes                                         */
    /*--------------------------------------------------------*/

    private static final class CacheEntity {

        private final Map<Integer, SapTuple> data = new HashMap<>();

        SapTuple get(int index) {
            return data.get(index);
        }

        void put(int index, SapTuple tuple) {
            data.putIfAbsent(index, tuple);
        }
    }

    private static final class SapTuple {

        int length;
        int ancestor;

        public SapTuple(int length, int ancestor) {
            this.length = length;
            this.ancestor = ancestor;
        }

        @Override
        public String toString() {
            return String.format("(length=%d;ancestor=%d)", length, ancestor);
        }
    }

    public static void main(String[] args) {
        final In in = new In(args[0]);
        final int vertexCount = in.readInt();
        final Digraph digraph = new Digraph(vertexCount);
        final int edgeCount = in.readInt();
        for (int i = 0; i < edgeCount; ++i) {
            final int from = in.readInt();
            final int to = in.readInt();
            digraph.addEdge(from, to);
        }
        final SAP sap = new SAP(digraph);
//        final Scanner scanner = new Scanner(System.in);
//        final int fromVertex = scanner.nextInt();
//        final int toVertex = scanner.nextInt();
//        System.out.println(sap.length(fromVertex, toVertex));
//        System.out.println(sap.ancestor(fromVertex, toVertex));
        System.out.println(
                sap.ancestor(
                        Arrays.asList(6701, 35238),
                        Collections.singleton(45113)
                )
        );
    }
}