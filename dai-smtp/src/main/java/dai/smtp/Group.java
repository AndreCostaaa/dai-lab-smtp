package dai.smtp;

import java.util.ArrayList;

public class Group {

    private final Sender sender;
    private ArrayList<Victim> recipients;
    private Message message;

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
}
