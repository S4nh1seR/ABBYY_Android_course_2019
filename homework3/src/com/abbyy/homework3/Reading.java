package com.abbyy.homework3;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

public class Reading {
    public static Map<Character, Integer> ReadAndMakeTable(String src_filename) throws IOException {
        FileInputStream fs_src = new FileInputStream(src_filename);
        BufferedInputStream is = new BufferedInputStream(fs_src);

        Map<Character, Integer> CountTokenTable = new HashMap<Character, Integer>();
        int c;
        while ((c = is.read()) != -1) {
            char curr_char = (char) c;
            int n_entries = CountTokenTable.getOrDefault(curr_char, 0);
            CountTokenTable.put(curr_char, n_entries + 1);
        }

        is.close();
        fs_src.close();

        return new TreeMap<Character, Integer>(CountTokenTable);
    }
}
