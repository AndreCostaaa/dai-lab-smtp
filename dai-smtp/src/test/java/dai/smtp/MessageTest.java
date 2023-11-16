package dai.smtp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

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
    public void messageListTest(){
        ArrayList<String> messageText = new ArrayList<>();
        messageText.add("subj 1");
        messageText.add("body 1");
        messageText.add("subj 2");
        messageText.add("body 2");

        System.out.println(messageText);

        Message[] messages = Message.makeMessageList(messageText);


        assertEquals(messages[0].getBody(), "body 1");
        assertEquals(messages[0].getSubject(), "subj 1");
        assertEquals(messages[1].getBody(), "body 2");
        assertEquals(messages[1].getSubject(), "subj 2");
    }


}