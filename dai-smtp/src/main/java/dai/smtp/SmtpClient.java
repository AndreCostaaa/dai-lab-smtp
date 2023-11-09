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
        // we ignore the server domain and just connect to our mock server and port
        socket = new Socket("127.0.0.1", 1025);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
    }

    private boolean isLastLine(String line) {
        return line.charAt(3) == ' ';
    }

    private void sendCommand(String command) throws IOException {
        writer.write(command);
        writer.flush();
    }

    private void endCommunication() throws IOException {
        sendCommand(endSend);
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

    public void sendEmail(Sender sender, ArrayList<Victim> recipients, Message message)
            throws IOException {

        emptyBuffer();
        greetOtherServer(sender.getDomain());
        emptyBuffer();
        sendSender(sender.getEmailAddress());
        emptyBuffer();
        for (var rcpt : recipients) {
            sendRcpt(rcpt.getEmailAddress());
            emptyBuffer();
        }
        String data = message.getSubject() + "\r\n" + message.getBody();
        sendData(data);
        emptyBuffer();
        endCommunication();
        emptyBuffer();
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
