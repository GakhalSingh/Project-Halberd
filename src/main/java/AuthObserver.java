public class AuthObserver implements ObserverListener {
    @Override
    public void update(String username, boolean isAuthenticated) {
        if (isAuthenticated) {
            System.out.println(username + " is successfully authenticated.");
        } else {
            System.out.println(username + " failed authentication.");
        }
    }

    @Override
    public void update1(String message) {

    }
}
