package com.valentinstamate.solver;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Member {

    private final int[][] preferences;
    private final double mutationRate;
    private final int students;
    private final int classes;
    private final int[] chromosome;
    private final int studentsPerClass;

    public Member(int students, int classes, double mutationRate, int[][] preferences) {
        this.preferences = preferences;
        this.mutationRate = mutationRate;
        this.students = students;
        this.classes = classes;
        this.studentsPerClass = (int) Math.ceil(1.0d * students / classes);
        this.chromosome = new int[students * classes];

        for (int i = 0; i < students; i++) {
            int randAssignment = getRandomAvailableClass();
            chromosome[i * classes + randAssignment] = 1;
        }
    }

    private Member(int students, int classes, double mutationRate, int[][] preferences, int[] chromosome) {
        this.preferences = preferences;
        this.mutationRate = mutationRate;
        this.students = students;
        this.classes = classes;
        this.studentsPerClass = (int) Math.ceil(1.0d * students / classes);
        this.chromosome = chromosome;
    }

    void mutate() {
        double rand = 0d + Math.random() * (100d - 0);

        if (rand > mutationRate) {
            int studentA = (int)(Math.random() * students);
            int studentB = (int)(Math.random() * students);

            int classA = getStudentAssignedClass(studentA);
            int classB = getStudentAssignedClass(studentB);

            assignStudentToClass(studentA, classB);
            assignStudentToClass(studentB, classA);
        }
    }

    public List<Member> crossover(Member m) {
        var chromosomeA = this.chromosome;
        var chromosomeB = m.chromosome;

        var offspringA = new int[classes * students];
        var offspringB = new int[classes * students];

        var randomPivot = (int) (Math.random() * students);

        for (var i = 0; i <= randomPivot; i++) {
            for (var j = 0; j < classes; j++) {
                offspringA[i * classes + j] = chromosomeA[i * classes + j];
                offspringB[i * classes + j] = chromosomeB[i * classes + j];
            }
        }

        for (var i = randomPivot + 1; i < students; i++) {
            for (var j = 0; j < classes; j++) {
                offspringA[i * classes + j] = chromosomeB[i * classes + j];
                offspringB[i * classes + j] = chromosomeA[i * classes + j];
            }
        }

        return List.of(
                new Member(students, classes, mutationRate, preferences, offspringA),
                new Member(students, classes, mutationRate, preferences, offspringB)
        );
    }

    public double fitness() {
        var maxScore = Math.exp(classes - 1) * students;
        var minScore = students;

        var score = score();
        score -= minScore;

        var mappedScore = score / (maxScore - minScore);

        return 1 - mappedScore + 0.00001;
    }

    public double score() {
        double score = 0;

        for (var i = 0; i < students; i++) {
            var choice = getStudentAssignedClass(i);
            var rawScore = classes - 1 - preferences[i][choice];

            score += Math.exp(rawScore);
        }

        return score;
    }

    private int getRandomAvailableClass() {
        var availableClasses = new boolean[classes];

        for (int i = 0; i < classes; i++) {
            int studentsAssigned = 0;

            for (int j = 0; j < students; j++) {
                studentsAssigned += chromosome[j * classes + i];
            }

            availableClasses[i] = studentsAssigned <=  studentsPerClass;
        }

        var availableIndexes = new ArrayList<Integer>();

        for (var i = 0; i < classes; i++) {
            if (availableClasses[i]) {
                availableIndexes.add(i);
            }
        }

        int index = (int)(Math.random() * availableIndexes.size());
        return availableIndexes.get(index);
    }

    private int getStudentAssignedClass(int student) {
        for (var i = 0; i < classes; i++) {
            if (chromosome[student * classes + i] == 1) {
                return i;
            }
        }

        throw new RuntimeException("The student is not assigned to any class");
    }

    private void assignStudentToClass(int student, int class_) {
        for (var i = 0; i < classes; i++) {
            chromosome[student * classes + i] = 0;
        }

        chromosome[student * classes + class_] = 1;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();

        sb.append(String.format("Member: %f %f\n", score(), fitness()));

        for (var i = 0; i < students; i++) {
            for (var j = 0; j < classes; j++) {
                sb.append(chromosome[i * classes + j]);
            }
            sb.append(" ");
        }

        return sb.toString();
    }
}
