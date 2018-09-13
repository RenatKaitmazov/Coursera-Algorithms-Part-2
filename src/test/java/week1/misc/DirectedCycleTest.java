package week1.misc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import week1.GraphProvider;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public class DirectedCycleTest {

    private DirectedCycle cycle;
    private DirectedCycle noCycle;

    @Before
    public void setUp() {
        cycle = new DirectedCycle(GraphProvider.provideDirectedWith13Vertices());
        noCycle = new DirectedCycle(GraphProvider.provideDirectedWith13VerticesAndNoCycles());
    }

    @Test
    public void checkHasCycle() {
        assertTrue(cycle.hasCycle());
        final Iterable<Integer> expected = Arrays.asList(2, 0, 5, 4, 2);
        assertEquals(expected, cycle.cycle());
    }

    @Test
    public void checkDoesNotHaveCycle() {
        assertFalse(noCycle.hasCycle());
        assertNull(noCycle.cycle());
    }
}