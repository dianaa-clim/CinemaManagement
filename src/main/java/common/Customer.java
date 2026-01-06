package common;

import java.io.Serializable;

public class Customer extends Account implements Serializable {
    public Customer(String role, String name, String mail, String phoneNumber, String password) {
        super("Customer", name, mail, phoneNumber, password);
    }
    public Customer(int id, String role, String name, String mail, String phoneNumber, String password) {
        super(id, "Customer", name, mail, phoneNumber, password);
    }
}
