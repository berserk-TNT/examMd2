package glocery.services;

import glocery.models.Account;

import java.util.List;

public interface IAccountService {
    List<Account> findAll();

    Account adminLogin(String username, String password);

    void add(Account newAccount);

    void update(Account newAccount);

    boolean existById(Long id);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByUsername(String username);

    Account findById(Long id);
}
