package week1;

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

    public static Graph newUndirectedWith10VerticesAnd3Components() {
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
}
