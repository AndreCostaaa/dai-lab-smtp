package dai.smtp;

public class Main {
    public static void main(String[] args) {

        try (var smtpClient = new SmtpClient("127.0.0.1", 1025)) {
            smtpClient.sendEmail(new Group());
        } catch (Exception e) {

        }
    }
}