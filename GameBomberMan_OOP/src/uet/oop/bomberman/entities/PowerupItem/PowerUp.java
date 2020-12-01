package uet.oop.bomberman.entities.PowerupItem;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Char.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class PowerUp extends AnimatedEntity {

    private String type;
    private int durationDisappear = 0;
    public String getType() {
        return type;
    }

    public PowerUp(double xUnit, double yUnit, Image img, String type) {
        super(xUnit, yUnit, img);
        this.type = type;
        if(type!= "portal") allowToPassThru = true; else
            allowToPassThru = false;
    }

    public int getTileX(){
        return (int) (x/ Sprite.SCALED_SIZE);
    }

    public int getTileY() {
        return (int) (y/Sprite.SCALED_SIZE);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public void applyItemTo(Bomber player) {
        switch (type) {
            case "flame item":
                player.applyFlameItem();
                break;
            case "bomb item":
                player.applyBombItem();
                break;
            case "speed item":
                player.applySpeedItem();
                break;
        }
    }

    @Override
    public void update() {

        animate();

        if(BombermanGame.entities.size() == 1 && type == "portal") {
            setAllowToPassThru(true);
        }

        if(collide(BombermanGame.player1) && type == "portal" && BombermanGame.entities.size() == 1) {
            BombermanGame.goToNextLevel();
        }

        if(collide(BombermanGame.player1) && type!="portal" && durationDisappear == 0) {
            durationDisappear = 30;
            applyItemTo(BombermanGame.player1);
        }

        if(durationDisappear > 0) {
            durationDisappear--;

            switch (type) {
                case "flame item":
                    img =Sprite.movingSprite(Sprite.powerup_flames, Sprite.powerup_flamepass, animate ,50).getFxImage();
                    break;
                case "bomb item":
                    img = Sprite.movingSprite(Sprite.powerup_bombs, Sprite.powerup_bombpass, animate, 50).getFxImage();
                    break;
                case "speed item":
                    img = Sprite.movingSprite(Sprite.powerup_speed, Sprite.powerup_speed, animate, 50).getFxImage();
                    break;
            }

            if(durationDisappear == 1) {
                remove();
            }

        }

    }

    @Override
    public boolean collide(Entity e) {
        if( getTileX() == e.getTileX() && getTileY() == e.getTileY()) return true;
        return false;
    }
}
