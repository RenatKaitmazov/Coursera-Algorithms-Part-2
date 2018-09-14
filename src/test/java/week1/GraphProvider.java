package week1;

import week1.graph.DirectedGraph;
import week1.graph.Graph;
import week1.graph.UndirectedGraph;

/**
 * @author Renat Kaitmazov
 */

public final class GraphProvider {

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    private GraphProvider() {
    }

    /*--------------------------------------------------------*/
    /* API                                                    */
    /*--------------------------------------------------------*/

    public static Graph newUndirectedWith13VerticesAnd3Components() {
        final Graph undirected = new UndirectedGraph(13);
        undirected.addEdge(0, 1);
        undirected.addEdge(0, 2);
        undirected.addEdge(0, 5);
        undirected.addEdge(0, 6);
        undirected.addEdge(5, 3);
        undirected.addEdge(5, 4);
        undirected.addEdge(3, 4);
        undirected.addEdge(4, 6);

        undirected.addEdge(7, 8);

        undirected.addEdge(9, 10);
        undirected.addEdge(9, 11);
        undirected.addEdge(9, 12);
        undirected.addEdge(11, 12);

        return undirected;
    }

    public static DirectedGraph newDirectedWith13Vertices() {
        final DirectedGraph directed = new DirectedGraph(13);
        directed.addEdge(0, 1);
        directed.addEdge(0, 5);

        directed.addEdge(2, 0);
        directed.addEdge(2, 3);

        directed.addEdge(3, 2);
        directed.addEdge(3, 5);

        directed.addEdge(4, 2);
        directed.addEdge(4, 3);

        directed.addEdge(5, 4);

        directed.addEdge(6, 0);
        directed.addEdge(6, 4);
        directed.addEdge(6, 9);

        directed.addEdge(7, 6);
        directed.addEdge(7, 8);

        directed.addEdge(8, 7);
        directed.addEdge(8, 9);

        directed.addEdge(9, 10);
        directed.addEdge(9, 11);

        directed.addEdge(10, 12);

        directed.addEdge(11, 4);
        directed.addEdge(11, 12);

        directed.addEdge(12, 9);

        return directed;
    }

    public static DirectedGraph newDirectedWith13VerticesAndNoCycles() {
        final DirectedGraph graph = new DirectedGraph(13);
        graph.addEdge(0, 1);
        graph.addEdge(0, 5);
        graph.addEdge(0, 6);
        graph.addEdge(2, 0);
        graph.addEdge(2, 3);
        graph.addEdge(3, 5);
        graph.addEdge(5, 4);
        graph.addEdge(6, 4);
        graph.addEdge(6, 9);
        graph.addEdge(7, 6);
        graph.addEdge(8, 7);
        graph.addEdge(9, 10);
        graph.addEdge(9, 11);
        graph.addEdge(9, 12);
        graph.addEdge(11, 12);
        return graph;
    }
}
