package dai.smtp;

public class Person{

    final String emailAddress;

    public Person(String address){
        emailAddress = address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}