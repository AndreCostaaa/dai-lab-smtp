package dai.smtp;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class SmtpClientTest {

    // we use the mock child class to actually test

    /**
     * This test will only not pass if an IOException is thrown or an error is
     * received from the server.
     * To actually check if the email was sent correctly, the email actually
     * received at the mock server must be verified
     */
    @Test
    public void testSendEmail() {
        Sender sender = new Sender("andremig.serzedel@heig-vd.ch");

        ArrayList<Victim> victims = new ArrayList<>(Arrays.asList(new Victim("amir.mouti@heig-vd.ch"),
                new Victim("mouti.amir@heig-vd.ch"), new Victim("am.ir.mou.ti@heig-vd.ch")));

        Message message = new Message("Hola! cariño", "Hola cariño!");

        assertDoesNotThrow(() -> {
            SmtpClient client = new MockSmtpClient();
            client.sendEmail(sender, victims, victims, message);
            client.close();
        });

    }

}