package week2.graph;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Represents a graph whose edges have weights and are directed.
 *
 * @author Renat Kaitmazov
 */

public final class WeightedDirectedGraph {

    /*--------------------------------------------------------*/
    /* Constants                                              */
    /*--------------------------------------------------------*/

    private static final String SPACE = " ";

    /*--------------------------------------------------------*/
    /* Fields                                                 */
    /*--------------------------------------------------------*/

    /**
     * The amount of vertices in the graph.
     */
    private final int vertexCount;
    /**
     * The way this graph is represented in computer's memory.
     */
    private final Set<DirectedEdge>[] graph;
    /**
     * Stores the amount of in degrees.
     */
    private final int[] inDegrees;
    /**
     * List of all edges in the graph.
     */
    private final List<DirectedEdge> edges = new LinkedList<>();

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    /**
     * Creates a graph with the give amount of vertices.
     *
     * @param vertexCount number of vertices the graph is going to have.
     */
    @SuppressWarnings("unchecked")
    public WeightedDirectedGraph(int vertexCount) {
        checkVertexIsPositive(vertexCount);
        this.vertexCount = vertexCount;
        graph = (Set<DirectedEdge>[]) new Set[vertexCount];
        inDegrees = new int[vertexCount];
        initGraph();
    }

    /**
     * Creates a graph from the given file path.
     * The file must have the following structure:
     * • the first line contains a single positive number which is the amount of vertices (V)
     * • the second line contains a single number which is the amount of edges (E)
     * • there must be E number of lines with three digits (F, S, W) on each line separated by a single space character;
     * both F and S must be in the following range [0, V). W is a floating point number representing weight.
     * An exception is thrown if these conditions are not met.
     *
     * @param pathToFile an absolute path to the file with a data set.
     */
    @SuppressWarnings("unchecked")
    public WeightedDirectedGraph(String pathToFile) {
        final File dataSet = new File(pathToFile);
        try (final BufferedReader input
                     = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(dataSet))))) {
            final int vertices = Integer.parseInt(input.readLine());
            checkVertexIsPositive(vertices);
            vertexCount = vertices;
            graph = (Set<DirectedEdge>[]) new Set[vertices];
            inDegrees = new int[vertices];
            initGraph();
            final int edges = Integer.parseInt(input.readLine());
            for (int i = 0; i < edges; ++i) {
                final String line = input.readLine();
                final StringTokenizer tokens = new StringTokenizer(line, SPACE);
                final int fromVertex = Integer.parseInt(tokens.nextToken());
                final int toVertex = Integer.parseInt(tokens.nextToken());
                final double weight = Double.parseDouble(tokens.nextToken());
                final DirectedEdge edge = new DirectedEdge(fromVertex, toVertex, weight);
                addEdge(edge);
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
        if (edges.isEmpty()) {
            return "[]";
        }
        final String newLine = "\n";
        final StringBuilder builder = new StringBuilder();
        for (int vertex = 0; vertex < vertexCount; ++vertex) {
            final Set<DirectedEdge> adjacentEdges = graph[vertex];
            if (adjacentEdges.isEmpty()) continue;
            builder.append(vertex).append(" - [");
            for (final DirectedEdge edge : adjacentEdges) {
                builder.append(edge).append(", ");
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
    /* WeightedGraph implementation                           */
    /*--------------------------------------------------------*/

    public int vertexCount() {
        return vertexCount;
    }

    public int edgeCount() {
        return edges.size();
    }

    public boolean addEdge(DirectedEdge edge) {
        final int from = edge.from();
        final int to = edge.to();
        checkVertexRange(from);
        adjacentEdges(to);
        if (from == to) return false;
        graph[from].add(edge);
        edges.add(edge);
        inDegrees[to]++;
        return true;
    }

    public Iterable<DirectedEdge> adjacentEdges(int ofVertex) {
        checkVertexRange(ofVertex);
        return graph[ofVertex];
    }

    public Iterable<DirectedEdge> edges() {
        return edges;
    }

    public int inDegree(int ofVertex) {
        checkVertexRange(ofVertex);
        return inDegrees[ofVertex];
    }

    public int outDegree(int ofVertex) {
        checkVertexRange(ofVertex);
        return graph[ofVertex].size();
    }

    /*--------------------------------------------------------*/
    /* Helper methods                                         */
    /*--------------------------------------------------------*/

    private void checkVertexIsPositive(int vertex) {
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
}
