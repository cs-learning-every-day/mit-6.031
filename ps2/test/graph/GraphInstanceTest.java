/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {

    // Testing strategy
    // TODO

    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }

    // TODO other tests for instance methods of Graph

    @Test
    public void testAddVertex() {
        Graph<String> graph = emptyInstance();
        assertTrue(graph.add("1"));
        assertTrue(graph.add("2"));
        assertFalse(graph.add("1"));
        assertEquals(Set.of("1", "2"), graph.vertices());
    }

    @Test
    public void testSet() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.add("C");

        assertEquals(graph.set("A", "B", 1), 0);
        assertEquals(graph.set("A", "B", 2), 1);

        assertEquals(graph.set("B", "C", 0), 0);
        assertEquals(graph.set("A", "B", 0), 2);
        assertEquals(Collections.emptySet(), emptyInstance().vertices());
    }

    @Test
    public void testRemoveVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertTrue("expected true when removing existing vertex", graph.remove("A"));
        assertFalse("expected false when removing non-existent vertex", graph.remove("B"));
        assertEquals("expected graph to have no vertices",
                Collections.emptySet(), graph.vertices());
    }

    @Test
    public void testVertices() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);
        Set<String> vertices = graph.vertices();
        assertTrue("expected vertex A in graph", vertices.contains("A"));
        assertTrue("expected vertex B in graph", vertices.contains("B"));
        assertEquals("expected graph to have 2 vertices", 2, vertices.size());
    }

    @Test
    public void testSources() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);
        Map<String, Integer> sources = graph.sources("B");
        assertTrue("expected A to be a source of B", sources.containsKey("A"));
        assertEquals("expected edge weight of 5", 5, (int) sources.get("A"));
    }

    @Test
    public void testTargets() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);
        Map<String, Integer> targets = graph.targets("A");
        assertTrue("expected B to be a target of A", targets.containsKey("B"));
        assertEquals("expected edge weight of 5", 5, (int) targets.get("B"));
    }

}
