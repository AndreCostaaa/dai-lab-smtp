package dai.smtp;

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

    static public Message[] makeMessageList(ArrayList<String> messageText){
        Message[] messages = new Message[messageText.size()/2];

        for(int i = 0; i < messages.length; i++){
            messages[i] = new Message(messageText.get(i*2), messageText.get(i*2 + 1));
        }

        return messages;
    }
}
