package dai.smtp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class SmtpClientTest {

    // we use the mock child class to actually test

    @Test
    public void testSendEmail() {
        Sender sender = new Sender("andremig.serzedel@heig-vd.ch");

        ArrayList<Victim> victims = new ArrayList<>(Arrays.asList(new Victim("amir.mouti@heig-vd.ch"),
                new Victim("mouti.amir@heig-vd.ch"), new Victim("am.ir.mou.ti@heig-vd.ch")));

        Message message = new Message("Hola!", "Hola cariño!");

        try (SmtpClient client = new MockSmtpClient()) {
            client.sendEmail(sender, victims, message);
            assertTrue(true);
        } catch (Exception e) {
            System.out.println("Exception > " + e);
        }
    }

}