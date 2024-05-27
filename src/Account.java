public class Account {
    private String userName;
    private String wachtwoord;

    public Account(String userName, String wachtwoord) {
        this.userName = userName;
        this.wachtwoord = wachtwoord;
    }

    public String getUserName() {
        return userName;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }
}
