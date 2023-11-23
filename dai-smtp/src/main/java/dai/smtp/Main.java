package dai.smtp;

public class Main {
    public static void main(String[] args) {

        if (args.length < 3) {
            System.out.println("Usage: <victims_file> <messages_file> <group_no>");
            return;
        }
        final String victimFilePath = args[0];
        final String messageFilePath = args[1];
        final int nbOfGroups = Integer.parseInt(args[2]);
        final Victim[] victims = Victim.fromFile(messageFilePath);
        final Message[] messages = Message.fromFile(messageFilePath);

        if (victims.length < nbOfGroups * 2) {
            throw new RuntimeException(String.format("Number of groups %d is too high for the number of emails (%d)",
                    victims.length, nbOfGroups));
        }
        if (messages.length < nbOfGroups) {
            System.out.printf(
                    "[INFO] Number of messages (%d) is smaller than the number of groups (%d).\n");
        }
    }
}