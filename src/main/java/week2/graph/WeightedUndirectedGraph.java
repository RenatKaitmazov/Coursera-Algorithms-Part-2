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
 * This class represents an undirected graph where edges connecting vertices in the graph
 * carry additional information to represent some value (like distance).
 *
 * @author Renat Kaitmazov
 */

public final class WeightedUndirectedGraph implements WeightedGraph {

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
    private final Set<Edge>[] graph;
    /**
     * List of all edges in the graph.
     */
    private final List<Edge> edges = new LinkedList<>();

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    /**
     * Creates a graph with the give amount of vertices.
     *
     * @param vertexCount number of vertices the graph is going to have.
     */
    @SuppressWarnings("unchecked")
    public WeightedUndirectedGraph(int vertexCount) {
        checkVertexIsPositive(vertexCount);
        this.vertexCount = vertexCount;
        graph = (Set<Edge>[]) new Set[vertexCount];
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
    public WeightedUndirectedGraph(String pathToFile) {
        final File dataSet = new File(pathToFile);
        try (final BufferedReader input
                     = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(dataSet))))) {
            final int vertices = Integer.parseInt(input.readLine());
            checkVertexIsPositive(vertices);
            vertexCount = vertices;
            graph = (Set<Edge>[]) new Set[vertices];
            initGraph();
            final int edges = Integer.parseInt(input.readLine());
            for (int i = 0; i < edges; ++i) {
                final String line = input.readLine();
                final StringTokenizer tokens = new StringTokenizer(line, SPACE);
                final int fromVertex = Integer.parseInt(tokens.nextToken());
                final int toVertex = Integer.parseInt(tokens.nextToken());
                final double weight = Double.parseDouble(tokens.nextToken());
                final Edge edge = new Edge(fromVertex, toVertex, weight);
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
        if (edgeCount() == 0) {
            return "[]";
        }
        final String newLine = "\n";
        final String comma = ",";
        final StringBuilder builder = new StringBuilder();
        for (int vertex = 0; vertex < vertexCount; ++vertex) {
            final Set<Edge> adjacentEdges = graph[vertex];
            if (adjacentEdges.isEmpty()) {
                continue;
            }
            builder.append(vertex).append(" - [");
            for (final Edge edge : adjacentEdges) {
                final int otherVertex = edge.otherVertex(vertex);
                builder.append("(")
                        .append(otherVertex)
                        .append(comma)
                        .append(edge.weight())
                        .append("), ");
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

    @Override
    public int vertexCount() {
        return vertexCount;
    }

    @Override
    public int edgeCount() {
        return edges.size();
    }

    @Override
    public Iterable<Edge> edges() {
        return edges;
    }

    @Override
    public int outDegree(int ofVertex) {
        checkVertexRange(ofVertex);
        return graph[ofVertex].size();
    }

    @Override
    public int inDegree(int ofVertex) {
        // In an undirected graph the number of out degrees is equal to the number of in degrees.
        return outDegree(ofVertex);
    }

    @Override
    public Iterable<Edge> adjacentEdges(int ofVertex) {
        checkVertexRange(ofVertex);
        return graph[ofVertex];
    }

    @Override
    public boolean addEdge(Edge edge) {
        final int vertex1 = edge.eitherVertex();
        final int vertex2 = edge.otherVertex(vertex1);
        checkVertexRange(vertex1);
        checkVertexRange(vertex2);
        if (vertex1 == vertex2) {
            // Self-loops are not allowed.
            return false;
        }
        graph[vertex1].add(edge);
        graph[vertex2].add(edge);
        edges.add(edge);
        return true;
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
