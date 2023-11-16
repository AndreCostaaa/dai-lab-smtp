package dai.smtp;

import java.io.*;

import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class SmtpClient implements java.io.Closeable {

    public static final int DEFAULT_SMTP_SERVER_PORT = 587;
    private final String greetCommand = "EHLO ";
    private final String senderCommand = "MAIL FROM:";
    private final String rcptCommand = "RCPT TO:";
    private final String dataCommand = "DATA";
    private final String endCommand = "\r\n";
    private final String dataEnd = "\r\n.\r\n";
    private final String endSend = "QUIT";
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

    private void sendMessage(Sender sender, ArrayList<Victim> recipients, Message message) throws IOException {

        sendCommand(dataCommand);

        emptyBuffer();
        writer.write("Content-type: text/plain; charset=utf-8\r\n");

        writer.write(String.format("From: \"%s\"<%s>%s", sender.getDisplayName(), sender.getEmailAddress(),
                endCommand));
        String to = "To:";
        for (var rec : recipients) {
            to += String.format(" %s <%s>;", rec.getDisplayName(), rec.getEmailAddress());
        }
        writer.write(to + endCommand);
        writer.write(String.format("Subject: %s%s%s", message.getSubject(), endCommand, endCommand));
        writer.write(message.getBody());
        writer.write(dataEnd);
        writer.flush();
    }

    private void emptyBuffer() throws IOException {
        while (!isLastLine(reader.readLine())) {
        }
    }

    public void sendEmail(Sender sender, ArrayList<Victim> recipients, Message message)
            throws IOException {

        emptyBuffer();
        sayHello(sender.getDomain());
        emptyBuffer();
        sendSender(sender.getEmailAddress());
        emptyBuffer();
        for (var rcpt : recipients) {
            sendRcpt(rcpt.getEmailAddress());
            emptyBuffer();
        }
        // String data = message.getSubject() + "\r\n" + message.getBody();
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
