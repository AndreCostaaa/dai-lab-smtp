package dai.smtp;

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
}
