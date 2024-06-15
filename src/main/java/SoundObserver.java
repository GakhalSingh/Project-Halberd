import halberd.sound.audio;

public class SoundObserver implements ObserverListener {
    private audio soundPlayer;

    public SoundObserver() {
        this.soundPlayer = new audio();
    }

    @Override
    public void update(String username, boolean isAuthenticated) {

    }

    @Override
    public void update1(String message) {
        soundPlayer.playSound();
    }
}
