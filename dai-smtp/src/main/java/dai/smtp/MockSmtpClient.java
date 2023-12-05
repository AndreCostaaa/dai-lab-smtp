package dai.smtp;

import java.io.IOException;

public class MockSmtpClient extends SmtpClient {

    static final private String SERVER_DOMAIN = "127.0.0.1";
    static final private int SERVER_PORT = 1025;

    public MockSmtpClient() throws IOException {
        super(SERVER_DOMAIN, SERVER_PORT);
    }
}
