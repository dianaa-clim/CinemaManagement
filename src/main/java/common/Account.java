package common;

import java.io.Serializable;

public abstract class Account implements Serializable {
    protected int idAccount;
    protected String role;
    protected String name;
    protected String mail;
    protected String phoneNumber;
    protected String password;

    public Account(String role, String name, String mail, String phoneNumber, String password) {
        this.role = role;
        this.name = name;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
    public Account(int idAccount, String role, String name, String mail, String phoneNumber, String password) {
        this.idAccount = idAccount;
        this.role = role;
        this.name = name;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
    public int getIdAccount() {
        return idAccount;
    }
    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
