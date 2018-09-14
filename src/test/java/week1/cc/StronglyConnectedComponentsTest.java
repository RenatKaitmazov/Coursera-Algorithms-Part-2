package week1.cc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import week1.GraphProvider;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public class StronglyConnectedComponentsTest {

    private StronglyConnectedComponents components;

    @Before
    public void setUp() {
        components = new StronglyConnectedComponents(GraphProvider.newDirectedWith13Vertices());
    }

    @Test
    public void strongComponentsCountTest() {
        assertEquals(5, components.count());
    }

    @Test
    public void stronglyConnectedTest() {
        assertTrue(components.isStronglyConnected(0, 2));
        assertTrue(components.isStronglyConnected(0, 3));
        assertTrue(components.isStronglyConnected(0, 5));
        assertTrue(components.isStronglyConnected(0, 4));

        assertTrue(components.isStronglyConnected(1, 1));

        assertTrue(components.isStronglyConnected(9, 10));
        assertTrue(components.isStronglyConnected(9, 11));
        assertTrue(components.isStronglyConnected(9, 12));

        assertTrue(components.isStronglyConnected(7, 8));

        assertTrue(components.isStronglyConnected(6, 6));
    }

    @Test
    public void notConnectedTest() {
        assertFalse(components.isStronglyConnected(1, 0));
        assertFalse(components.isStronglyConnected(4, 1));
        assertFalse(components.isStronglyConnected(6, 9));
        assertFalse(components.isStronglyConnected(8, 12));
    }

    @Test
    public void componentIdTest() {
        assertEquals(0, components.componentId(1));
        assertEquals(1, components.componentId(0));
        assertEquals(1, components.componentId(2));
        assertEquals(1, components.componentId(3));
        assertEquals(1, components.componentId(4));
        assertEquals(1, components.componentId(5));
        assertEquals(2, components.componentId(9));
        assertEquals(2, components.componentId(10));
        assertEquals(2, components.componentId(11));
        assertEquals(2, components.componentId(12));
        assertEquals(3, components.componentId(6));
        assertEquals(4, components.componentId(7));
        assertEquals(4, components.componentId(8));
    }
}