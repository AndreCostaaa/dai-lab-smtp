package dai.smtp;

import java.io.IOException;

public class MockSmtpClient extends SmtpClient {

    public MockSmtpClient() throws IOException {
        super("127.0.0.1", 1025);
    }
}
