package uet.oop.bomberman.Sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import java.nio.file.Paths;


public class SoundEffect {

    private MediaPlayer mediaPlayer;
    private Media media;
    private boolean loop = false;
    String path;

    public static SoundEffect backgroundMusic = new SoundEffect("res/sound/music_game.wav", true);
    public static SoundEffect exploision = new SoundEffect("res/sound/Explosion.wav", false);
    public static SoundEffect bomber_die = new SoundEffect("res/sound/bomber_die.wav", false);
    public static SoundEffect enemyDie = new SoundEffect("res/sound/linh_die.wav", false);

    public SoundEffect(String path, boolean loop) {
        this.loop = loop;
        this.path = path;
        media = new Media(Paths.get(path).toUri().toString());
        mediaPlayer = new MediaPlayer(media);
    }

    public void play() {
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.dispose();
            mediaPlayer = new MediaPlayer(new Media(Paths.get(path).toUri().toString()));
        });
        mediaPlayer.play();
    }


}
