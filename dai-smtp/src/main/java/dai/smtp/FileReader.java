package dai.smtp;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import com.google.gson.*;

public class FileReader {

    public ArrayList<String> readLines(File file) {
        ArrayList<String> lines = new ArrayList<>();

        try(var reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))){

            while(reader.ready()){
                lines.add(reader.readLine());
            }
            reader.close();
            return lines;

        }catch(IOException e) {
            return null;
        }
    }


    public ArrayList<String> jsonParser(File file){

        Gson g = new Gson();


        return null;
    }
}
