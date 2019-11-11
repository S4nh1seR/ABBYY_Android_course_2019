package com.abbyy.homework2;

import java.util.Random;

public class Part1 {

    public static void Swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public static void PrintIntArr(int[][] arr) {
        for (int i = 0; i < arr.length; ++i) {
            for (int j = 0; j < arr[i].length; ++j) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void ChooseMax(int[][] arr) {
        for (int i = 0; i < arr.length; ++i) {
            int max_value_idx = 0;
            for (int j = 1; j < arr[i].length; ++j) {
                if (arr[i][j] > arr[i][max_value_idx]) {
                    max_value_idx = j;
                }
            }
            Swap(arr[i], 0, max_value_idx);
        }
    }

    public void Run() {
        Random rand = new Random();
        int[][] int_arr = new int[6][7];
        for (int i = 0; i < int_arr.length; ++i) {
            for (int j = 0; j < int_arr[i].length; ++j) {
                int_arr[i][j] = rand.nextInt(10);
            }
        }
        PrintIntArr(int_arr);

        System.out.println();

        ChooseMax(int_arr);
        PrintIntArr(int_arr);
    }
}
