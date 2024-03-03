package com.valentinstamate;


import com.valentinstamate.solver.Member;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        var students = 4;
        var classes = 4;
        var mutationRate = 0.01;

        var preferences = generatePreferences(students, classes);
        printMatrix(preferences);
        System.out.println();

        var members = new ArrayList<Member>();
        for (var i = 0; i < 30; i++) {
            members.add(new Member(students, classes, mutationRate, preferences));
        }

        members.forEach(System.out::println);
    }

    public static int[][] generatePreferences(int students, int classes) {
        var preferences = new int[students][classes];

        for (var i = 0; i < students; i++) {
            var point = 0;

            for (var j = 0; j < classes; j++) {
//                var pointChosen = (short) (Math.random() * (1f * point + 0.999));
//                preferences[i][j] = pointChosen;
//
//                if (pointChosen == point) {
//                    point++;
//                }
                preferences[i][j] = point++;
            }

            shuffleArray(preferences[i]);
        }

        return preferences;
    }

    static void shuffleArray(int[] array) {
        var rand = new Random();

        for (int i = 0; i < array.length; i++) {
			int randomIndexToSwap = rand.nextInt(array.length);
			int temp = array[randomIndexToSwap];
			array[randomIndexToSwap] = array[i];
			array[i] = temp;
		}
    }

    public static void printMatrix(int matrix[][]) {
        for (var i = 0; i < matrix.length; i++) {
            var arr = new ArrayList<String>();
            for (var p : matrix[i]) {
                arr.add(String.valueOf(p));
            }

            System.out.println("[" + String.join(", ", arr) + "]");
        }
    }

}
