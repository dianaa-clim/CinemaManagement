package common;

import java.io.Serializable;

public class Administrator extends Account implements Serializable{
    public Administrator(String role, String name, String mail, String phoneNumber, String password) {
        super("Administrator", name, mail, phoneNumber, password);
    }
    public Administrator(int id, String role, String name, String mail, String phoneNumber, String password) {
        super(id, "Administrator", name, mail, phoneNumber, password);
    }
}
