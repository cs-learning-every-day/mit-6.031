/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {

    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();

    // Abstraction function:
    // TODO
    // Representation invariant:
    // TODO
    // Safety from rep exposure:
    // TODO

    // TODO constructor
    public ConcreteEdgesGraph() {
    }

    // TODO checkRep
    private void checkRep() {
        for (var edge : edges) {
            assert vertices.contains(edge.getSource());
            assert vertices.contains(edge.getTarget());
            assert edge.getWeight() > 0;
        }
    }

    @Override
    public boolean add(L vertex) {
        if (vertices.contains(vertex)) {
            return false;
        }
        vertices.add(vertex);
        checkRep();
        return true;
    }

    @Override
    public int set(L source, L target, int weight) {
        var newEdge = new Edge<L>(source, target, weight);
        Iterator<Edge<L>> iterator = edges.iterator();
        while (iterator.hasNext()) {
            var edge = iterator.next();
            if (newEdge.equals(edge)) {
                int res = edge.getWeight();
                if (weight == 0) {
                    iterator.remove();
                } else {
                    edges.set(edges.indexOf(edge), newEdge);
                }
                return res;
            }
        }
        edges.add(newEdge);
        return 0;
    }

    @Override
    public boolean remove(L vertex) {
        if (!vertices.contains(vertex)) {
            return false;
        }
        vertices.remove(vertex);

        edges.removeIf(e -> e.getSource().equals(vertex) || e.getTarget().equals(vertex));
        return true;
    }

    @Override
    public Set<L> vertices() {
        return new HashSet<>(vertices);
    }

    @Override
    public Map<L, Integer> sources(L target) {
        Map<L, Integer> res = new HashMap<>();
        edges.forEach(e -> res.put(e.getSource(), e.getWeight()));
        return res;
    }

    @Override
    public Map<L, Integer> targets(L source) {
        Map<L, Integer> res = new HashMap<>();
        edges.forEach(e -> res.put(e.getTarget(), e.getWeight()));
        return res;
    }

    // TODO toString()
    @Override
    public String toString() {
        return super.toString();
    }

}

/**
 * TODO specification
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>
 * PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge<L> {

    // TODO fields
    private final L source;
    private final L target;
    private final Integer weight;

    // Abstraction function:
    // TODO
    // Representation invariant:
    // TODO
    // Safety from rep exposure:
    // TODO

    // TODO constructor
    public Edge(L source, L target, Integer weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }

    // TODO checkRep
    private void checkRep() {
        assert source != null;
        assert target != null;
        assert weight != null && weight >= 0;
    }

    // TODO methods

    // TODO toString()
    @Override
    public String toString() {
        return "Edge(" + source.toString() + "," + target.toString() + "," + weight + ")";
    }

    public L getSource() {
        return source;
    }

    public L getTarget() {
        return target;
    }

    public Integer getWeight() {
        return weight;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((source == null) ? 0 : source.hashCode());
        result = prime * result + ((target == null) ? 0 : target.hashCode());
        result = prime * result + ((weight == null) ? 0 : weight.hashCode());
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
        var other = (Edge<L>) obj;
        return this.source.equals(other.getSource()) &&
                this.target.equals(other.getTarget());
    }
}
