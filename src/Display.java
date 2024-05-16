import java.util.Scanner;

public class Display extends Login {
    private Scanner scanner = new Scanner(System.in);
    private CSVWriter csvWriter = new CSVWriter("accounts.csv");

    public void startScreen() {
        System.out.println("1. Login");
        System.out.println("2. Maak een account");
        int keuze = scanner.nextInt();

        if (keuze == 1) {
            loginScreen();
        } else if (keuze == 2) {
            nieuwAccount();
        } else {
            System.out.println("Ongeldige keuze. Probeer opnieuw.");
            startScreen();
        }
    }
}
