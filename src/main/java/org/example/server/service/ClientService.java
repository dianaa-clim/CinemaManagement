package org.example.server.service;

import org.example.server.dao.ClientDAO;

public class ClientService {
    private final ClientDAO clientDAO;

    public ClientService(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public int getClientIdByAccountId(int idAccount) {
        Integer id = clientDAO.findClientIdByAccountId(idAccount);
        if (id == null) throw new IllegalStateException("Account-ul nu este client");
        return id;
    }
}

