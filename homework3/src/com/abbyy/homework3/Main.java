package com.abbyy.homework3;

import java.io.IOException;
import java.util.Map;

public class Main {

    // #0 - src, #1 - dst
    public static void main(String[] args) {
	    if (args.length == 2) {
            try {
                Map<Character, Integer> sortedTable = Reading.ReadAndMakeTable(args[0]);
                Writing.WriteTable(args[1], sortedTable);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
	        System.out.println("Wrong number of arguments!");
        }
    }
}
