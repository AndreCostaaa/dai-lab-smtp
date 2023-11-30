package dai.smtp;

import java.util.ArrayList;

public class Group {

    private final Sender sender;
    private final ArrayList<Victim> recipients;
    private final Message message;

    public Group(Sender sender, ArrayList<Victim> recipients, Message message) {
        this.sender = sender;
        this.recipients = recipients;
        this.message = message;
    }

    public Sender getSender() {
        return sender;
    }

    public ArrayList<Victim> getVictims() {
        return recipients;
    }

    public Message getMessage() {
        return message;
    }

    public String toString() {
        return String.format("Sender: %s, Recipients: %s, Subject: %s", sender, recipients, message.getSubject());
    }

}
