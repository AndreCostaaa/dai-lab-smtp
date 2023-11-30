package dai.smtp;

import java.util.ArrayList;

public class Main {

    private static final int MAX_PEOPLE_PER_GROUP = 5;
    private static final int MAX_RETRIES = 5;

    private static boolean sendEmailRetry(SmtpEmailSender sender) {
        for (int j = 1; j <= MAX_RETRIES; ++j) {
            if (sender.send()) {
                return true;
            }
            System.out.printf(" Retrying %d/%d...\n", j, MAX_RETRIES);
        }
        return false;

    }

    public static void main(String[] args) {

        if (args.length < 3) {
            System.out.println("Usage: <victims_file> <messages_file> <group_no>");
            return;
        }
        final String victimFilePath = args[0];
        final String messageFilePath = args[1];
        final int nbOfGroups = Integer.parseInt(args[2]);
        final Victim[] victims = Victim.fromFile(victimFilePath);
        final Message[] messages = Message.fromFile(messageFilePath);

        if (victims.length < nbOfGroups * 2) {
            throw new RuntimeException(String.format("Number of groups %d is too high for the number of emails (%d)",
                    victims.length, nbOfGroups));
        }
        if (messages.length < nbOfGroups) {
            System.out.printf(
                    "[INFO] Number of messages (%d) is smaller than the number of groups (%d).\n", messages.length,
                    nbOfGroups);
        }
        int victimsPerGroup = Math.min(MAX_PEOPLE_PER_GROUP - 1, (victims.length / nbOfGroups) - 1);

        System.out.printf("Nb Of Victims per group: %d\n", victimsPerGroup);
        for (int i = 0; i < nbOfGroups; ++i) {
            ArrayList<Victim> groupVictims = new ArrayList<>();

            for (int j = 0; j < victimsPerGroup; ++j) {
                groupVictims.add(victims[i * victimsPerGroup + i + 1 + j]);
            }

            Group group = new Group(
                    new Sender(victims[i * victimsPerGroup].getEmailAddress()),
                    groupVictims,
                    messages[i % messages.length]);

            SmtpEmailSender sender = new SmtpEmailSender(group);
            System.out.printf("Group %d: %s\n", i, group);

            if (sendEmailRetry(sender)) {
                System.out.println("Email Sent Successfully");
            } else {
                System.out.println("Problem While Sending E-mail ");
            }

        }

    }
}