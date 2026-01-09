package common;

public class Customer extends Account {

    public Customer(String name, String username,
                    String phoneNumber, String password) {
        super(0, "Customer", name, username, phoneNumber, password);
    }

    public Customer(int id, String name, String username,
                    String phoneNumber, String password) {
        super(id, "Customer", name, username, phoneNumber, password);
    }
}
