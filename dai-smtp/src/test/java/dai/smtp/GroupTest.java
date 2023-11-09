package dai.smtp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class GroupTest {
    private final Message message = new Message("my subject", "my body");
    private final Sender sender = new Sender("andremig.serzedel@heig-vd.ch");
    private final ArrayList<Victim> victims = new ArrayList<Victim>(
            Arrays.asList(new Victim("andremig.serzedel@heig-vd.ch"),
                    new Victim("amir.mouti@heig-vd.ch")));

    private final Group group = new Group(sender, victims, message);

    @Test
    public void senderTest() {
        assertEquals(group.getSender(), sender);
    }

    @Test
    public void messageTest() {
        assertEquals(group.getMessage(), message);
    }

    @Test
    public void victimTest() {
        assertEquals(group.getVictims(), victims);
    }
}