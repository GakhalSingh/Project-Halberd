import java.util.Scanner;

public class Login extends Account {
    //hi
    private Scanner scanner;

    public Login(String userName, String wachtWoord, Scanner scanner) {
        super(userName, wachtWoord);
        this.scanner = scanner;
    }

    public Login() {
        super("", "");
        this.scanner = new Scanner(System.in);
    }

    public void loginScreen() {
        System.out.println("Gebruikersnaam?");
        String gebruikersnaam = scanner.nextLine();
        System.out.println("Wachtwoord?");
        String wachtwoord = scanner.nextLine();

        Account account = new Account(gebruikersnaam, wachtwoord);

        if (isValidUser(account)) {
            System.out.println("Welkom, " + account.getUserName() + "!");
        } else {
            System.out.println("Sorry, uw wachtwoord/gebruikersnaam is verkeerd.");
        }
    }

    private boolean isValidUser(Account account) {
        return account.getUserName().equals("brian") && account.getWachtWoord().equals("hot");
    }
    public void nieuwAccount(){
        System.out.println("maak een gebruikers naam");
        String nieuweNaam = scanner.nextLine();
        System.out.println("je nieuwe wachtwoord");
        String nieuwWachtwoord = scanner.nextLine();
        System.out.println("herhaal het wachtwoord");
        String nieuwWachtwoord2 = scanner.nextLine();
        if (nieuwWachtwoord.equals(nieuwWachtwoord2){
            break;
        }
        else{
            nieuwAccount();
        }
    }
}
