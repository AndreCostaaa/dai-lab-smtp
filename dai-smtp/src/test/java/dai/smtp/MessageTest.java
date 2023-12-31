package dai.smtp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.io.File;

public class MessageTest {
    private final Message message = new Message("my subject", "my body");

    @Test
    public void bodyTest() {
        assertEquals(message.getBody(), "my body");
    }

    @Test
    public void subjectTest() {
        assertEquals(message.getSubject(), "my subject");
    }

    @Test
    public void messageListTest() {
        String messageText = "[{\"subject\": \"subject1\", \"body\" : \"message1\"}, {\"subject\": \"subject2\", \"body\" : \"message2\"}]";

        Message[] messages = Message.fromFile("messages.json");

        assertEquals(messages[0].getBody(), "Hola cariño");
        assertEquals(messages[0].getSubject(), "subject1");
        assertEquals(messages[1].getBody(), "Como estás?");
        assertEquals(messages[1].getSubject(), "assunto2");
    }
}