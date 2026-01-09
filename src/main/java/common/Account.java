package common;

import java.io.Serializable;

public class Account implements Serializable {

    private int idAccount;
    private String role;
    private String name;
    private String username;
    private String phoneNumber;
    private String password;

    public Account(int idAccount, String role, String name,
                   String username, String phoneNumber, String password) {
        this.idAccount = idAccount;
        this.role = role;
        this.name = name;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }
}
