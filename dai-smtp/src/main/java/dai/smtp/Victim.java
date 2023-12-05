package dai.smtp;

import java.io.File;
import java.util.ArrayList;

public class Victim extends Person {

    public Victim(String address) {
        super(address);
    }

    public static Victim[] fromFile(String filepath) {
        File file = new File(filepath);
        ArrayList<String> emails = FileReader.readLines(file);
        if (emails == null) {
            return null;
        }

        Victim[] victims = new Victim[emails.size()];
        for (int i = 0; i < emails.size(); ++i) {
            if(!emails.get(i).contains("@")){
                return null;
            }
            victims[i] = new Victim(emails.get(i));
        }
        return victims;
    }
}