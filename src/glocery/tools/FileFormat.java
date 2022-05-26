package glocery.tools;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileFormat {
    public static <T> void write(String path, List<T> lines) {
        try {
            PrintWriter writer = new PrintWriter(path);
            for (Object line : lines) {
                writer.println(line.toString());
            }
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(path + " is not correct!");
        }
    }

    public static List<String> read(String path) {
        List<String> lines = new ArrayList<>();
        try {
            File file = new File(path);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null && !line.trim().isEmpty()) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(path + " is not correct!");
        }
        return lines;
    }
}
