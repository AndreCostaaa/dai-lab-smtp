package dai.smtp;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class FileReader {

    public ArrayList<String> readLines(File file) {
        ArrayList<String> lines = new ArrayList<>();

        try (var reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            while (reader.ready()) {
                lines.add(reader.readLine());
            }
            reader.close();
            return lines;

        } catch (IOException e) {
            return null;
        }
    }

    public String readText(File file) {
        StringBuilder text = new StringBuilder();
        try (var reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            while (reader.ready()) {
                text.append(reader.readLine());
            }
            return text.toString();

        } catch (IOException e) {
            return null;
        }
    }
}
