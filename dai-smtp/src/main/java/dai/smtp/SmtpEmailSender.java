package dai.smtp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SmtpEmailSender {

    private final Group group;

    public SmtpEmailSender(Group group) {
        this.group = group;
    }

    private HashMap<String, ArrayList<Victim>> regroupRecipientsByDomain(ArrayList<Victim> recipients) {
        var result = new HashMap<String, ArrayList<Victim>>();
        for (var recipient : recipients) {
            String domain = recipient.getDomain();
            if (!result.containsKey(domain)) {
                result.put(domain, new ArrayList<Victim>());
            }
            result.get(domain).add(recipient);
        }
        return result;
    }

    public boolean send() {
        var recipientsPerDomain = regroupRecipientsByDomain(group.getVictims());

        for (var domain : recipientsPerDomain.keySet()) {
            try (SmtpClient smtpClient = new MockSmtpClient()) {
                smtpClient.sendEmail(group.getSender(), recipientsPerDomain.get(domain), group.getVictims(),
                        group.getMessage());
            } catch (IOException | RuntimeException e) {
                System.out.printf("Problem sending e-mail to %s > %s\n", domain, e);
                System.out.println("Aborting...");
                return false;
            }
        }
        return true;
    }
}
