package glocery.models;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private Long accId;
    private String accUsername;
    private String accPassword;
    private String accName;
    private String accPhoneNumber;
    private String accEmail;
    private String accAddress;
    private Instant accCreatedTime;
    private Instant accUpdatedTime;
    private Role accRole;

    public Account() {
    }

    public Account(Long accId, String accUsername, String accPassword, String accName, String accPhoneNumber, String accEmail, String accAddress, Role accRole) {
        this.accId = accId;
        this.accUsername = accUsername;
        this.accPassword = accPassword;
        this.accName = accName;
        this.accPhoneNumber = accPhoneNumber;
        this.accEmail = accEmail;
        this.accAddress = accAddress;
        this.accRole = accRole;
    }

    public Long getAccId() {
        return accId;
    }
    public void setAccId(Long accId) {
        this.accId = accId;
    }
    public String getAccUsername() {
        return accUsername;
    }
    public void setAccUsername(String accUsername) {
        this.accUsername = accUsername;
    }
    public String getAccPassword() {
        return accPassword;
    }
    public void setAccPassword(String accPassword) {
        this.accPassword = accPassword;
    }
    public String getAccName() {
        return accName;
    }
    public void setAccName(String accName) {
        this.accName = accName;
    }
    public String getAccPhoneNumber() {
        return accPhoneNumber;
    }
    public void setAccPhoneNumber(String accPhoneNumber) {
        this.accPhoneNumber = accPhoneNumber;
    }
    public String getAccEmail() {
        return accEmail;
    }
    public void setAccEmail(String accEmail) {
        this.accEmail = accEmail;
    }
    public String getAccAddress() {
        return accAddress;
    }
    public void setAccAddress(String accAddress) {
        this.accAddress = accAddress;
    }
    public Instant getAccCreatedTime() {
        return accCreatedTime;
    }
    public void setAccCreatedTime(Instant accCreatedTime) {
        this.accCreatedTime = accCreatedTime;
    }
    public Instant getAccUpdatedTime() {
        return accUpdatedTime;
    }
    public void setAccUpdatedTime(Instant accUpdatedTime) {
        this.accUpdatedTime = accUpdatedTime;
    }
    public Role getAccRole() {
        return accRole;
    }
    public void setAccRole(Role accRole) {
        this.accRole = accRole;
    }

    public static Account parseAccount(String raw) {
        Account account = new Account();
        String[] fields = raw.split(", ");
        account.accId = Long.parseLong(fields[0]);
        account.accUsername = fields[1];
        account.accPassword = fields[2];
        account.accName = fields[3];
        account.accPhoneNumber = fields[4];
        account.accEmail = fields[5];
        account.accAddress = fields[6];
        account.accRole = Role.parseRole(fields[7]);
        account.accCreatedTime = Instant.parse(fields[8]);
        String temp = fields[9];
        if (temp != null && !temp.equals("null")) {
            account.accUpdatedTime = Instant.parse(temp);
        }
        return account;
    }

    @Override
    public String toString() {
        return String.format("%d, %s, %s, %s, %s, %s, %s, %s, %s, %s",
                accId, accUsername, accPassword, accName, accPhoneNumber, accEmail, accAddress, accRole, accCreatedTime, accUpdatedTime);
    }

    List<Order> orderList = new ArrayList<>();
}
