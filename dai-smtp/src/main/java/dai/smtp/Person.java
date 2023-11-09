package dai.smtp;

abstract public class Person {

    private final String emailAddress;

    public Person(String address) {
        if (!address.contains("@"))
            throw new RuntimeException("Not a valid email address");
        emailAddress = address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getDomain() {
        return emailAddress.split("@")[1];
    }
}