package model;

public class Login {
    private String username;
    private String password;
    private String token;

    public Login setUsername(String username) {
        this.username = username;
        return this;
    }

    public Login setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
