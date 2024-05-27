public class Account {
    private String userName;
    private String email;
    private String wachtwoord;

    public Account(String userName,String email, String wachtwoord) {
        this.userName = userName;
        this.email = email;
        this.wachtwoord = wachtwoord;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }
}
