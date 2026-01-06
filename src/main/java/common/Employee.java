package common;

import java.io.Serializable;

public class Employee extends Account implements Serializable {
    public Employee(String role, String name, String mail, String phoneNumber, String password) {
        super("Employee", name, mail, phoneNumber, password);
    }
    public Employee(int id, String role, String name, String mail, String phoneNumber, String password) {
        super(id, "Employee", name, mail, phoneNumber, password);
    }
}
