package glocery.services;

import glocery.models.Account;
import glocery.models.Role;
import glocery.tools.FileFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class AccountService implements IAccountService {
    public final static String path = "data/accounts.csv";

    private static AccountService instance;

    private AccountService() {
    }

    public static AccountService getInstance() {
        if (instance == null) {
            instance = new AccountService();
        }
        return instance;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accountList = new ArrayList<>();
        List<String> records = FileFormat.read(path);
        for (String record : records) {
            accountList.add(Account.parseAccount(record));
        }
        return accountList;
    }

    @Override
    public Account adminLogin(String username, String password) {
        List<Account> accountList = findAll();
        for (Account account : accountList) {
            if (account.getAccUsername().equals(username) && account.getAccPassword().equals(password)
                    && account.getAccRole().equals(Role.ADMIN)) {
                return account;
            }
        }
        return null;
    }

    @Override
    public void add(Account newAccount) {
        newAccount.setAccCreatedTime(Instant.now());
        List<Account> accountList = findAll();
        accountList.add(newAccount);
        FileFormat.write(path, accountList);
    }

    @Override
    public void update(Account newAccount) {
        List<Account> accountList = findAll();
        for (Account account : accountList) {
            if (newAccount.getAccId().equals(account.getAccId())) {
                String accName = newAccount.getAccName();
                if (accName != null && !accName.isEmpty()) {
                    account.setAccName(accName);
                }
                String phoneNumber = newAccount.getAccPhoneNumber();
                if (phoneNumber != null && !phoneNumber.isEmpty()) {
                    account.setAccPhoneNumber(phoneNumber);
                }
                String accAddress = newAccount.getAccAddress();
                if (accAddress != null && !accAddress.isEmpty()) {
                    account.setAccAddress(accAddress);
                }
                account.setAccUpdatedTime(Instant.now());
                FileFormat.write(path, accountList);
                break;
            }
        }
    }

    @Override
    public boolean existById(Long id) {
        return findById(id) != null;
    }

    @Override
    public boolean existsByEmail(String email) {
        List<Account> accountList = findAll();
        for (Account account : accountList) {
            if (email.equals(account.getAccEmail())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        List<Account> accountList = findAll();
        for (Account account : accountList) {
            if (phoneNumber.equals(account.getAccPhoneNumber())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existsByUsername(String username) {
        List<Account> accountList = findAll();
        for (Account account : accountList) {
            if (username.equals(account.getAccUsername())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Account findById(Long id) {
        List<Account> accountList = findAll();
        for (Account account : accountList) {
            if (id.equals(account.getAccId())) {
                return account;
            }
        }
        return null;
    }
}
