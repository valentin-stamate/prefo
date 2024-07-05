package com.valentinstamate.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Graph {

    private final List<Node> nodes;

    public Graph(int size) {
        this.nodes = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            this.nodes.add(new Node(i));
        }
    }

    private Graph(List<Node> nodes) {
        this.nodes = nodes;
    }

    public Node get(int i) {
        return nodes.get(i);
    }

    public List<Node> getNeighbours(int i) {
        return this.nodes.get(i).getNeighbours();
    }

    public int getNeighboursSize(int i) {
        return this.nodes.get(i).getNeighbours().size();
    }

    public void connect(int i, int j) {
        this.nodes.get(i).connect(this.nodes.get(j));
    }

    public void connect(int i, int j, double cost) {
        this.nodes.get(i).connect(this.nodes.get(j), cost);
    }

    public void disconnect(int i, int j) {
        this.nodes.get(i).disconnect(this.nodes.get(j));
    }

    public double getCost(int i, int j) {
        return this.nodes.get(i).getCost(this.nodes.get(j));
    }

    public boolean isConnected(int i, int j) {
        return nodes.get(i).isConnectedWith(nodes.get(j));
    }

    public List<Node> getFirstN(int n) {
        var list = new ArrayList<Node>(n);

        for (int i = 0; i < n; i++) {
            list.add(this.nodes.get(i));
        }

        return list;
    }

    public static Graph generateRandomBipartite(int n, int m) {
        var nodes = new ArrayList<Node>(n + m);
        for (int i = 0; i < n + m; i++) {
            nodes.add(new Node(i));
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (Math.random() < 0.5) {
                    nodes.get(i).connect(nodes.get(n + j), Math.random());
                }
            }
        }

        for (int i = n; i < n + m; i++) {
            for (int j = 0; j < n; j++) {
                if (Math.random() < 0.2) {
                    nodes.get(i).connect(nodes.get(j), Math.random());
                }
            }
        }

        return new Graph(nodes);
    }

    public static Graph generateRandomBipartiteComplete(int n, int m) {
        var nodes = new ArrayList<Node>(n + m);
        for (int i = 0; i < n + m; i++) {
            nodes.add(new Node(i));
        }

        var random = new Random();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (random.nextDouble() < 0.5) {
                    nodes.get(i).connect(nodes.get(n + j), random.nextDouble());
                } else {
                    nodes.get(i).connect(nodes.get(n + j), -1);
                }
            }
        }

        for (int i = n; i < n + m; i++) {
            for (int j = 0; j < n; j++) {
                if (random.nextDouble() < 0.3) {
                    nodes.get(i).connect(nodes.get(j), random.nextDouble());
                } else {
                    nodes.get(i).connect(nodes.get(j), -1);
                }
            }
        }

        return new Graph(nodes);
    }

    /** n is the number of nodes in the first partition */
    public void printAsByPartite(int n) {
        for (int i = 0; i < n; i++) {
            System.out.println(this.nodes.get(i));
        }

        System.out.println();

        for (int i = n; i < this.nodes.size(); i++) {
            System.out.println(this.nodes.get(i));
        }
    }

}
