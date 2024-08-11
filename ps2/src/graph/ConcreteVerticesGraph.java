/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {

    private final List<Vertex<L>> vertices = new ArrayList<>();

    public ConcreteVerticesGraph() {

    }

    // Abstraction function:
    // TODO
    // Representation invariant:
    // TODO
    // Safety from rep exposure:
    // TODO
    private void checkRep() {
        Set<L> names = new HashSet<>();
        for (var v : vertices) {
            assert v != null;
            assert names.add(v.getName());
        }
    }

    // TODO constructor

    // TODO checkRep

    @Override
    public boolean add(L vertex) {
        var v = new Vertex<L>(vertex);
        if (vertices.contains(v)) {
            return false;
        }
        vertices.add(v);
        checkRep();
        return true;
    }

    @Override
    public int set(L source, L target, int weight) {
        Vertex<L> src = null, tgt = null;
        for (var v : vertices) {
            if (v.getName().equals(target)) {
                tgt = v;
            }
            if (v.getName().equals(source)) {
                src = v;
            }
        }

        if (src == null) {
            src = new Vertex<L>(source);
            vertices.add(src);
        }
        if (tgt == null) {
            tgt = new Vertex<L>(source);
            vertices.add(tgt);
        }

        int res = src.getEdges().getOrDefault(target, 0);
        if (src.getEdges().containsKey(target)) {
            if (weight == 0) {
                src.getEdges().remove(target);
            } else {
                src.getEdges().put(target, weight);
            }
        } else {
            src.getEdges().put(target, weight);
        }
        return res;
    }

    @Override
    public boolean remove(L vertex) {
        Vertex<L> toRemove = null;
        for (Vertex<L> v : vertices) {
            if (v.getName().equals(vertex)) {
                toRemove = v;
            } else {
                v.getEdges().remove(vertex);
            }
        }
        if (toRemove != null) {
            vertices.remove(toRemove);
            checkRep();
            return true;
        }
        return false;
    }

    @Override
    public Set<L> vertices() {
        Set<L> names = new HashSet<>();
        for (Vertex<L> v : vertices) {
            names.add(v.getName());
        }
        return names;
    }

    @Override
    public Map<L, Integer> sources(L target) {
        Map<L, Integer> sources = new HashMap<>();
        for (var v : vertices) {
            Map<L, Integer> edges = v.getEdges();
            if (edges.containsKey(target)) {
                sources.put(v.getName(), edges.get(target));
            }
        }
        return sources;
    }

    @Override
    public Map<L, Integer> targets(L source) {
        for (Vertex<L> v : vertices) {
            if (v.getName().equals(source)) {
                return v.getEdges();
            }
        }
        return new HashMap<>();
    }

    // TODO toString()

}

/**
 * TODO specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>
 * PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex<L> {

    // TODO fields
    private Map<L, Integer> edges = new HashMap<>();
    private L name;

    public Vertex(L name) {
        this.name = name;
    }

    public Map<L, Integer> getEdges() {
        return edges;
    }

    public L getName() {
        return name;
    }

    // Abstraction function:
    // TODO
    // Representation invariant:
    // TODO
    // Safety from rep exposure:
    // TODO

    // TODO constructor

    // TODO checkRep

    // TODO methods

    // TODO toString()
    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((edges == null) ? 0 : edges.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vertex<L> other = (Vertex<L>) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}
