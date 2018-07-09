package com.kakaobank.profile.producer.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    public static List<String> readActualContent(String filePath) throws IOException {
        BufferedReader in = new BufferedReader(new java.io.FileReader(filePath));
        List<String> actual = new ArrayList<String>();
        String line = "";

        while ((line = in.readLine()) != null)
            actual.add(line);

        in.close();
        return actual;
    }

}
