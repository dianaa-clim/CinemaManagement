package org.example.server.service;

import common.Account;
import org.example.server.dao.AccountDAO;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AccountDAO accountDAO;

    public AuthService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    /**
     * Login user după username + parolă
     */
    public Account login(String username, String password) {
        return accountDAO.findByUsernameAndPassword(username, password);
    }
}
