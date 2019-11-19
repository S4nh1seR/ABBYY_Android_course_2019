package com.abbyy.homework3;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class Writing {
    public static void WriteTable(String dst_filename, Map<Character, Integer> SortedTokenTable) throws IOException {
        FileOutputStream fs_dst = new FileOutputStream(dst_filename);
        BufferedOutputStream os = new BufferedOutputStream(fs_dst);

        for (Map.Entry<Character, Integer> entry : SortedTokenTable.entrySet()) {
            if (!Character.isWhitespace(entry.getKey())) {
                String curr_char_dsc = entry.getKey() + " " + entry.getValue().toString() + "\n";
                byte[] buffer = curr_char_dsc.getBytes();
                os.write(buffer, 0, buffer.length);
            }
        }

        os.close();
        fs_dst.close();
    }
}
