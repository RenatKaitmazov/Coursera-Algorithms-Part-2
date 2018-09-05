package week1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * The graph does not allow for anomalies like self-loops and parallel edges.
 *
 * @author Renat Kaitmazov
 */

public abstract class AbstractGraph implements Graph {

    /*--------------------------------------------------------*/
    /* Constants                                              */
    /*--------------------------------------------------------*/

    private static final String SPACE = " ";

    /*--------------------------------------------------------*/
    /* Fields                                                 */
    /*--------------------------------------------------------*/

    /**
     * We represent the graph in the computer's memory as an array of sets of integers
     * where an index of array corresponds to a vertex and the entry at the index is a collection
     * of the vertices adjacent to the vertex.
     */
    protected final Set<Integer>[] graph;

    /**
     * Number of vertices in the graph.
     */
    protected final int vertexCount;

    /**
     * Number of edges in the graph.
     */
    protected int edgeCount;

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    /**
     * Creates a graph with the give amount of vertices.
     *
     * @param vertexCount number of vertices the graph is going to have.
     */
    @SuppressWarnings("unchecked")
    public AbstractGraph(int vertexCount) {
        checkVertexNotNegative(vertexCount);
        this.vertexCount = vertexCount;
        graph = (Set<Integer>[]) new LinkedHashSet[vertexCount];
        initGraph();
    }

    /**
     * Creates a graph from the given file path.
     * The file must have the following structure:
     * • the first line contains a single positive number which is the amount of vertices (V)
     * • the second line contains a single number which is the amount of edges (E)
     * • there must be E number of lines with two digits (F, S) on each line separated by a single space character;
     * both F and S must be in the following range [0, V).
     * An exception is thrown if these conditions are not met.
     *
     * @param pathToFile an absolute path to the file with a data set.
     */
    @SuppressWarnings("unchecked")
    public AbstractGraph(String pathToFile) {
        final File dataSet = new File(pathToFile);
        try (final BufferedReader input
                     = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(dataSet))))) {
            final int vertices = Integer.parseInt(input.readLine());
            checkVertexNotNegative(vertices);
            vertexCount = vertices;
            graph = (Set<Integer>[]) new LinkedHashSet[vertexCount];
            initGraph();
            final int edges = Integer.parseInt(input.readLine());
            for (int i = 0; i < edges; ++i) {
                final String line = input.readLine();
                final StringTokenizer tokens = new StringTokenizer(line, SPACE);
                final int fromVertex = Integer.parseInt(tokens.nextToken());
                final int toVertex = Integer.parseInt(tokens.nextToken());
                addEdge(fromVertex, toVertex);
            }
        } catch (FileNotFoundException error) {
            throw new IllegalArgumentException("File at " + pathToFile + " does not exist!");
        } catch (IOException error) {
            throw new IllegalStateException("An error occurred while reading " + pathToFile + ". Cannot proceed further.");
        }
    }

    /*--------------------------------------------------------*/
    /* Overridden methods                                     */
    /*--------------------------------------------------------*/

    @Override
    public String toString() {
        if (edgeCount == 0) {
            return "[]";
        }
        final String newLine = "\n";
        final StringBuilder builder = new StringBuilder();
        for (int vertex = 0; vertex < vertexCount; ++vertex) {
            final Set<Integer> adjacentVertices = graph[vertex];
            if (adjacentVertices.isEmpty()) {
                continue;
            }
            builder.append(vertex).append("-").append("[");
            for (final Integer adjacentVertex : adjacentVertices) {
                builder.append(adjacentVertex).append(", ");
            }
            final int end = builder.length();
            final int start = end - 2;
            builder.replace(start, end, "]").append(newLine);
        }
        final int end = builder.length();
        final int start = end - 1;
        return builder.replace(start, end, "").toString();
    }

    /*--------------------------------------------------------*/
    /* Graph implementation                                   */
    /*--------------------------------------------------------*/

    @Override
    public int vertices() {
        return vertexCount;
    }

    @Override
    public int edges() {
        return edgeCount;
    }

    @Override
    public int outDegree(int ofVertex) {
        checkVertexRange(ofVertex);
        final Set<Integer> adjacentVertices = graph[ofVertex];
        return adjacentVertices.size();
    }

    @Override
    public Iterable<Integer> adjacentVertices(int ofVertex) {
        checkVertexRange(ofVertex);
        return graph[ofVertex];
    }

    @Override
    public boolean addEdge(int fromVertex, int toVertex) {
        checkVertexRange(fromVertex);
        checkVertexRange(toVertex);
        final boolean success = add(fromVertex, toVertex);
        if (success) {
            ++edgeCount;
        }
        return success;
    }

    /*--------------------------------------------------------*/
    /* Abstract methods                                       */
    /*--------------------------------------------------------*/

    /**
     * Implementation left to a subclass.
     * The subclass must just add a vertex and nothing more!
     *
     * @param fromVertex start point of an edge.
     * @param toVertex end point of the edge.
     * @return <code>true</code> if the edge was successfully added, <code>false</code> otherwise.
     */
    protected abstract boolean add(int fromVertex, int toVertex);

    /*--------------------------------------------------------*/
    /* Helper methods                                         */
    /*--------------------------------------------------------*/

    private void checkVertexNotNegative(int vertex) {
        if (vertex < 1) {
            throw new IllegalArgumentException(vertex + " is not positive");
        }
    }

    private void checkVertexRange(int vertex) {
        if (vertex < 0 || vertex >= vertexCount) {
            throw new IllegalArgumentException("The graph does not have vertex: " + vertex);
        }
    }

    private void initGraph() {
        for (int vertex = 0; vertex < vertexCount; ++vertex) {
            graph[vertex] = new LinkedHashSet<>();
        }
    }

    protected Set<Integer> adjacentInternal(int vertex) {
        return graph[vertex];
    }
}
