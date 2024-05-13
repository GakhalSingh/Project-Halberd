import java.util.Scanner;
public class Display extends Login{
    Scanner scanner = new Scanner;
    //hi

    public void startScreen(){
        System.out.println("1.login");
        System.out.println("2.maak een account");
        int keuzeStartScherm = scanner.nextInt();

        if (keuzeStartScherm == 1){
            Login.loginScreen();
        }
        else if (keuzeStartScherm == 2){
            nieuwAccount();
        }
        else{
            startScreen();
        }

    }
}
