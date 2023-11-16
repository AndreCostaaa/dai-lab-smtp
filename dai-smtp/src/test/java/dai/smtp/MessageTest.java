package dai.smtp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

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
}