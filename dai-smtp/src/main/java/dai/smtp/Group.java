package dai.smtp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Group {

    private final Sender sender;
    private HashMap<String, ArrayList<Victim>> recipientsMap;
    private Message message;

    public Group(Sender sender, ArrayList<Victim> recipients, Message message) {
        this.sender = sender;
        this.recipientsMap = regroupVictimByDomain(recipients);
        this.message = message;
    }

    private HashMap<String, ArrayList<Victim>> regroupVictimByDomain(ArrayList<Victim> victims) {
        var result = new HashMap<String, ArrayList<Victim>>();
        for (var victim : victims) {
            String domain = victim.getDomain();
            if (!result.containsKey(domain)) {
                result.put(domain, new ArrayList<Victim>());
            }
            result.get(domain).add(victim);
        }
        return result;
    }

    public Sender getSender() {
        return sender;
    }

    public ArrayList<Victim> getVictims() {
        var victims = new ArrayList<Victim>();
        for (var v : recipientsMap.values()) {
            victims.addAll(v);
        }
        return victims;
    }

    public Message getMessage() {
        return message;
    }

    public boolean sendEmail() {

        for (String domain : recipientsMap.keySet()) {

            try (var smtpClient = new MockSmtpClient()) {
                smtpClient.sendEmail(sender, recipientsMap.get(domain), message);
            } catch (IOException e) {
                return false;
            }
        }
        return true;

    }
}
