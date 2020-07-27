package model;

public class Login {
    private String username;
    private String password;

    public Login setUsername(String username) {
        this.username = username;
        return this;
    }

    public Login setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getJson() {
        return "{\n" +
                "    \"username\" : \"" + username + "\",\n " +
                "    \"password\" : \"" + password + "\"\n" +
                "}";
    }
}
