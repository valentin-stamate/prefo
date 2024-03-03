package com.valentinstamate.solver;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GeneticAlgorithm {

    private final int populationSize;
    private final int students;
    private final int classes;
    private final double mutationRate;
    private final int[][] preferences;
    private final int generations;

    private final List<Member> population;

    public GeneticAlgorithm(int populationSize, int students, int classes, double mutationRate, int[][] preferences, int generations) {
        this.populationSize = populationSize;
        this.students = students;
        this.classes = classes;
        this.mutationRate = mutationRate;
        this.preferences = preferences;
        this.generations = generations;

        population = new ArrayList<>();
        for (var i = 0; i < populationSize; i++) {
            population.add(new Member(students, classes, mutationRate, preferences));
        }
    }

    public void start() {
        for (var i = 0; i < generations; i++) {
            System.out.println("Generation " + i + 1);
        }
    }

    private void selection() {

    }

    private void mutatePopulation() {
        population.forEach(Member::mutate);
    }


}
