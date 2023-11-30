package dai.smtp;

abstract public class Person {

    private final String emailAddress;
    private final String displayName;

    public Person(String displayName, String address) {
        if (!address.contains("@"))
            throw new RuntimeException("Not a valid email address");
        emailAddress = address;
        if (displayName == null) {
            this.displayName = address.split("@")[0];
        } else {
            this.displayName = displayName;
        }
    }

    public Person(String address) {
        this(null, address);
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getDomain() {
        return emailAddress.split("@")[1];
    }

    public String toString() {
        return emailAddress;
    }
}