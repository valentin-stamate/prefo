package com.valentinstamate;

import com.valentinstamate.graph.Graph;

import java.util.LinkedList;

public class StableMatching {

    public Graph galeShapelyAlgorithm(Graph graph, int n, int m) {
        var solution = new Graph(n + m);

        var mSet = new LinkedList<>(graph.getFirstN(n));

        while (!mSet.isEmpty()) {
            var currentMName = mSet.pop().getName();
            var neighbours = graph.getNeighbours(currentMName);

            for (int j = 0; j < neighbours.size(); j++) {
                var currentWName = neighbours.get(j).getName();

                if (solution.getNeighboursSize(currentWName) == 0) {
                    solution.connect(currentMName, currentWName, graph.getCost(currentMName, currentWName));
                    solution.connect(currentWName, currentMName, graph.getCost(currentWName, currentMName));
                    break;
                } else {
                    var mPreviousChosenName = solution.getNeighbours(currentWName).stream().findFirst().get().getName();

                    if (solution.getCost(currentWName, mPreviousChosenName) < graph.getCost(currentWName, currentMName)) {
                        solution.disconnect(currentWName, mPreviousChosenName);
                        solution.disconnect(mPreviousChosenName, currentWName);

                        solution.connect(currentMName, currentWName, graph.getCost(currentMName, currentWName));
                        solution.connect(currentWName, currentMName, graph.getCost(currentWName, currentMName));

                        mSet.add(graph.get(mPreviousChosenName));
                        break;
                    }

                }
            }

        }

        return solution;
    }

}
