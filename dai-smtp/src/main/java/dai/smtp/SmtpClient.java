package dai.smtp;

import java.io.*;

import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

public class SmtpClient implements java.io.Closeable {

    public static final int DEFAULT_SMTP_SERVER_PORT = 587;
    private static final String greetCommand = "EHLO ";
    private static final String senderCommand = "MAIL FROM:";
    private static final String rcptCommand = "RCPT TO:";
    private static final String dataCommand = "DATA";
    private static final String endCommand = "\r\n";
    private static final String dataEnd = "\r\n.\r\n";
    private static final String endSend = "QUIT";

    private final Socket socket;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    public SmtpClient(String serverDomain, int serverPort) throws IOException {
        socket = new Socket(serverDomain, serverPort);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
    }

    private boolean isLastLine(String line) {
        return line.charAt(3) == ' ';
    }

    private void sendCommand(String command) throws IOException {
        writer.write(command + endCommand);
        writer.flush();
    }

    private void endCommunication() throws IOException {
        sendCommand(endSend);
    }

    private void sayHello(String domain) throws IOException {
        sendCommand(String.format("%s %s", greetCommand, domain));
    }

    private void sendSender(String email) throws IOException {
        sendCommand(String.format("%s <%s>", senderCommand, email));
    }

    private void sendRcpt(String email) throws IOException {
        sendCommand(String.format("%s <%s>", rcptCommand, email));
    }

    private String encodeSubjectBase64(String subject) {
        Base64.Encoder encoder = Base64.getEncoder();

        return String.format("=?utf-8?B?%s?=", encoder.encodeToString(subject.getBytes()));
    }

    private String fromHeader(Sender sender) {
        return String.format("From: \"%s\"<%s>",
                sender.getDisplayName(),
                sender.getEmailAddress());
    }

    private String toHeader(ArrayList<Victim> recipients) {
        String to = "To:";
        for (var rec : recipients) {
            to += String.format(" %s <%s>;", rec.getDisplayName(), rec.getEmailAddress());
        }
        return to;

    }

    private String subjectHeader(Message message) {
        return String.format("Subject: %s", encodeSubjectBase64(message.getSubject()));
    }

    private void sendMessage(Sender sender, ArrayList<Victim> recipients, Message message) throws IOException {

        sendCommand(dataCommand);

        emptyBuffer();
        // Set content type to utf-8
        writer.write("Content-type: text/plain; charset=utf-8\r\n");

        // Headers
        writer.write(fromHeader(sender) + endCommand);
        writer.write(toHeader(recipients) + endCommand);
        writer.write(subjectHeader(message) + endCommand.repeat(2));
        // Data
        writer.write(message.getBody() + dataEnd);
        // Flush
        writer.flush();
    }

    private void emptyBuffer() throws IOException, RuntimeException {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.charAt(0) > '3') {
                throw new RuntimeException("Received error > " + line);
            }
            if (isLastLine(line))
                break;
        }
    }

    /**
     * 
     * @param sender     the person who sends the email
     * @param recipients the recipients of the email
     * @param message    the message
     * @throws IOException
     * @throws RuntimeException if the server throws an error message
     */
    public void sendEmail(Sender sender, ArrayList<Victim> recipients, Message message)
            throws IOException, RuntimeException {

        emptyBuffer();
        sayHello(sender.getDomain());
        emptyBuffer();
        sendSender(sender.getEmailAddress());
        emptyBuffer();
        for (var rcpt : recipients) {
            sendRcpt(rcpt.getEmailAddress());
            emptyBuffer();
        }
        sendMessage(sender, recipients, message);
        emptyBuffer();
        endCommunication();
        emptyBuffer();
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
