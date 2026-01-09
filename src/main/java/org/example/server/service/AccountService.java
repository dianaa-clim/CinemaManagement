package org.example.server.service;

import common.Account;
import org.example.server.dao.AccountDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountDAO accountDAO;

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    /* ================= AUTH ================= */

    public Account login(String username, String password) {
        return accountDAO.findByUsernameAndPassword(username, password);
    }

    public void register(String username, String password) {
        accountDAO.insertClient(username, password);
    }

    /* ================= ADMIN ================= */

    public List<Account> findAll() {
        return accountDAO.findAll();
    }

    public void createEmployee(String username, String password) {
        accountDAO.insertEmployee(username, password);
    }

    public void deleteByUsername(String username) {
        accountDAO.deleteByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return accountDAO.existsByUsername(username);
    }
}
