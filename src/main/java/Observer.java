import java.util.ArrayList;
import java.util.List;

public class Observer {
    private List<ObserverListener> listeners = new ArrayList<>();
    private ObserverListener[] observers;

    public void addObserver(ObserverListener listener) {
        listeners.add(listener);
    }

    public void removeObserver(ObserverListener listener) {
        listeners.remove(listener);
    }

    public void notifyObservers(String username, boolean isAuthenticated) {
        for (ObserverListener listener : listeners) {
            listener.update(username, isAuthenticated);
        }
    }
    public void notify1Observers(String message) {
        for (ObserverListener observer : observers) {
            observer.update1(message);
        }
    }
}