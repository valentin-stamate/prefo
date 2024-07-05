package com.valentinstamate.graph;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Node {

    private final Integer name;
    private final List<Node> neighbours;
    private final Map<Node, Double> costs;

    protected Node(int name) {
        this.name = name;
        this.neighbours = new ArrayList<>();
        this.costs = new HashMap<>();
    }

    protected void connect(Node neighbour) {
        this.neighbours.add(neighbour);
        this.costs.put(neighbour, 0D);
    }

    protected void disconnect(Node neighbour) {
        this.neighbours.remove(neighbour);
        this.costs.remove(neighbour);
    }

    protected void connect(Node neighbour, double cost) {
        this.neighbours.add(neighbour);
        this.costs.put(neighbour, cost);
    }

    protected double getCost(Node neighbour) {
        if (!this.costs.containsKey(neighbour)) {
            return -1;
        }

        return this.costs.get(neighbour);
    }

    protected boolean isConnectedWith(Node node) {
        return this.costs.containsKey(node);
    }

    @Override
    public String toString() {
        return String.format("%d -> [%s]", name, String.join(", ", this.neighbours.stream().map(n -> {
            return String.format("%s(%.2f)", n.getName(), this.getCost(n));
        }).toList()));
    }

    public boolean equals(Node n) {
        return this.name.equals(n.name);
    }
}
