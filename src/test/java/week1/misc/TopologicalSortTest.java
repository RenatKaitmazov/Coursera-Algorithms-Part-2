package week1.misc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import week1.GraphProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public class TopologicalSortTest {

    private TopologicalSort topologicalSort;

    @Before
    public void setUp() {
        topologicalSort = new TopologicalSort(GraphProvider.newDirectedWith13VerticesAndNoCycles());
    }

    @Test(expected = IllegalArgumentException.class)
    public void doesNotProcessGraphWithCyclesTest() {
        new TopologicalSort(GraphProvider.newDirectedWith13Vertices());
    }

    @Test
    public void topologicalSortTest() {
        // Direct comparison does not work for an unknown reason.
        final List<Integer> expected = Arrays.asList(8, 7, 2, 3, 0, 6, 9, 11, 12, 10, 5, 4, 1);
        final List<Integer> actual = new ArrayList<>(expected.size());
        for (final int vertex : topologicalSort.sortedVertices()) {
            actual.add(vertex);
        }
        assertEquals(expected, actual);
    }
}