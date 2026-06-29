package com.example.demo.login;

public class Login {
    private String userName;
    private String phone;
    private String email;
    private String account;
    private String password;

    public Login(String userName,
     String phone,
     String email,
     String account,
     String password) {
        this.account = account;
        this.password = password;
        this.email = email;
        this.account = account;
        this.phone = phone;
        this.userName = userName;
    }
    public String getUserName() {return userName;}
    public void setUserName(String userName) {this.userName = userName;}

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
}
