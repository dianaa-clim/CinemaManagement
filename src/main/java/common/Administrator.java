package common;

public class Administrator extends Account {

    public Administrator(String name, String username,
                         String phoneNumber, String password) {
        super(0, "Administrator", name, username, phoneNumber, password);
    }

    public Administrator(int id, String name, String username,
                         String phoneNumber, String password) {
        super(id, "Administrator", name, username, phoneNumber, password);
    }
}
