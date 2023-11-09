package dai.smtp;

public class Person{

    final String emailAddress;

    public Person(String address){
        if(!address.contains("@")){
            throw new RuntimeException();
        }
        emailAddress = address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getDomain(){
        String[] email = emailAddress.split("@");
        return email[1];
    }
}