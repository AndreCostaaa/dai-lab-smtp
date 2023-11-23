package dai.smtp;

import com.google.gson.*;

import java.util.ArrayList;

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

    static public Message[] makeMessageList(String messageText) {
        Gson gson = new Gson();
        return gson.fromJson(messageText, Message[].class);
    }
}
