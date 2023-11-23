package dai.smtp;

import com.google.gson.*;

import java.io.File;

public class Message {

    private String subject;
    private String body;

    public Message(String subject, String body) {
        this.subject = subject;
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public String getSubject() {
        return subject;
    }

    static public Message[] messagesFromFile(String filepath) {
        File file = new File(filepath);
        Gson gson = new Gson();
        return gson.fromJson(FileReader.readText(file), Message[].class);
    }
}
