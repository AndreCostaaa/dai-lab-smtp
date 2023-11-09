package dai.smtp;

abstract public class Person {

    private final String emailAddress;

<<<<<<< HEAD
    public Person(String address) {
        if (!address.contains("@"))
            throw new RuntimeException("Not a valid email address");
=======
    public Person(String address){
>>>>>>> parent of c9b77a9 (start of FileReader and Person changes)
        emailAddress = address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
<<<<<<< HEAD

    public String getDomain() {
        return emailAddress.split("@")[1];
    }
=======
>>>>>>> parent of c9b77a9 (start of FileReader and Person changes)
}