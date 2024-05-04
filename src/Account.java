import java.util.Scanner;

public class Account {
    protected String wachtWoord;
    protected String userName;

    public Account(String userName, String wachtWoord) {
        this.userName = userName;
        this.wachtWoord = wachtWoord;
    }

    public String getWachtWoord() {
        return wachtWoord;
    }

    public void setWachtWoord(String wachtWoord) {
        this.wachtWoord = wachtWoord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
