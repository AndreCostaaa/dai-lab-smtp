package dai.smtp;

import java.io.*;

import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

public class SmtpClient implements java.io.Closeable {

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
        // we ignore the server domain and just connect to our mock server and port
        socket = new Socket("127.0.0.1", 1025);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
    }

    private boolean isLastLine(String line) {
        return line.charAt(3) == ' ';
    }

    public boolean sendEmail(Group group) {

        try {
            sendEmailToServer(group.getSender(), group.getRecipients(), group.getMessage());
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    private void sendCommand(String command) throws IOException {
        writer.write(command);
        writer.flush();
    }

    private void greetOtherServer(String domain) throws IOException {
        sendCommand(greetCommand + domain);
    }

    private void sendSender(String email) throws IOException {
        sendCommand(senderCommand + email);
    }

    private void sendRcpt(String email) throws IOException {
        sendCommand(rcptCommand + email);
    }

    private void sendData(String data) throws IOException {
        sendCommand(dataCommand);
        writer.write(data);
        writer.write(dataEnd);
        writer.flush();
    }

    private void emptyBuffer() throws IOException {
        while (!isLastLine(reader.readLine())) {
        }
    }

    private void sendEmailToServer(Person sender, Person[] recipients, Message message)
            throws IOException {
        emptyBuffer();
        greetOtherServer(sender.domain());
        emptyBuffer();
        sendSender(sender.email());
        emptyBuffer();
        for (var rcpt : recipients) {
            sendRcpt(rcpt.email());
            emptyBuffer();
        }
        String data = message.getSubject() + "\r\n" + message.getBody();
        sendData(data);
        emptyBuffer();

    }

    private HashSet<String> getServers(String[] recipients) {
        var ret = new HashSet<String>();

        for (var rec : recipients) {
            if (!rec.contains("@"))
                continue;
            ret.add(rec.split("@")[1]);
        }
        return ret;

    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
