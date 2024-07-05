package com.valentinstamate;

import com.valentinstamate.graph.Graph;

public class Main {
    public static void main(String[] args) {
        int n = 5;
        int m = 5;

        var graph = Graph.generateRandomBipartiteComplete(n, m);

        graph.printAsByPartite(n);

        System.out.println("SOLUTION");

        var stableMatching = new StableMatching();
        var solution = stableMatching.galeShapelyAlgorithm(graph, n, m);

        solution.printAsByPartite(n);
    }

}
