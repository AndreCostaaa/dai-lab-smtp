package dai.smtp;

import java.util.ArrayList;

public class Group {

    private final Sender sender;
    private ArrayList<Victim> recipients;

    public Group(Sender sender, ArrayList<Victim> recipients) {
        this.sender = sender;
        this.recipients = recipients;
    }

    public Sender getSender() {
        return sender;
    }

    public ArrayList<Victim> getVictims() {
        return recipients;
    }

}
