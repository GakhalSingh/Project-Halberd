package halberd.sound;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class audio {
    private final String filePath = "src/main/resources/mp3/msn-sound_1.mp3";

    public void playSound() {
        try (InputStream is = Files.newInputStream(Paths.get(filePath))) {
            Player player = new Player(is);
            player.play();
        } catch (FileNotFoundException e) {
            System.err.println("MP3 file not found: " + e.getMessage());
        } catch (JavaLayerException e) {
            System.err.println("Error playing MP3 file: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }
    }
}
