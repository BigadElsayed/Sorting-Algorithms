package core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {

    public static int[] loadFromFile(String filePath) throws IOException {
        List<Integer> list = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] tokens = line.split("\\s*,\\s*");

                for (int i = 0; i < tokens.length; i++) {
                    tokens[i] = tokens[i].trim();
                    try {
                        list.add(Integer.parseInt(tokens[i]));
                    } catch (NumberFormatException e) {
                        throw new IOException();
                    }
                }

            }
        }
        if (list.isEmpty()) throw new IOException();
        return list.stream().mapToInt(Integer::intValue).toArray() ;
    }

    public static String getFileName(String filePath) {
        int index = Math.max(filePath.lastIndexOf('/') ,filePath.lastIndexOf('\\')) ;
        return filePath.substring(index + 1);
    }
}
