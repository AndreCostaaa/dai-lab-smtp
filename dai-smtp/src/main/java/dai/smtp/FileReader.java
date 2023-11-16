package dai.smtp;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

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


    public String readText(File file){
        StringBuilder text = new StringBuilder();
        try(var reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))){

            while(reader.ready()){
                text.append(reader.readLine());
            }
            reader.close();
            return text.toString();

        }catch(IOException e) {
            return null;
        }
    }

    public ArrayList<String> jsonParser(File file){

        ArrayList<String> messages = new ArrayList<>();
        try(JsonReader reader = new JsonReader(new InputStreamReader(
                new FileInputStream(file), StandardCharsets.UTF_8))){
            
            reader.beginArray();
            while(reader.hasNext()){
                reader.beginObject();
                reader.nextName();
                messages.add(reader.nextString());
                reader.nextName();
                messages.add(reader.nextString());
                reader.endObject();
            }
            reader.endArray();

        }
        catch(IOException e){
            System.out.println("Error reading Json file");
        }
        return messages;
    }
}
