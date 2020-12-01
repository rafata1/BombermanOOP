package uet.oop.bomberman.entities.Char.Enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Char.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Oneal extends Enemy {
    public Oneal(double xUnit, double yUnit, Image img) {
        super(xUnit, yUnit, img);
        velocity = 0.5;
        direction = 3;
    }

    @Override
    public void chooseSprite() {
        switch (direction) {
            case 0:
            case 1:
                img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animate, 60).getFxImage();
                break;
            case 2:
            case 3:
                img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animate, 60).getFxImage();
                break;
        }
    }

    @Override
    public void calculateMove() {
        int xa = 0;
        int ya = 0;

        if(direction == 0) ya--;
        if(direction == 2) ya++;
        if(direction == 3) xa--;
        if(direction == 1) xa++;

        if(canMove(xa, ya)) {
            move(xa * velocity, ya * velocity);
        } else {

            if(BombermanGame.player1.getTileX() == getTileX()) {
                if(BombermanGame.player1.getTileY() > getTileY()) direction = 2;
                else direction = 0;
            } else
            if(BombermanGame.player1.getTileY() == getTileY()) {
                if(BombermanGame.player1.getTileX() > getTileX()) direction = 1;
                else direction = 3;
            } else {
                direction = new Random().nextInt(4);
            }

        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    @Override
    public void update() {
        kill();
        if(timeToDisappear > 0) {
            timeToDisappear -- ;
            img =Sprite.movingSprite(Sprite.oneal_dead,Sprite.oneal_dead, animate, 30).getFxImage();
            if(timeToDisappear == 1) {
                remove();
            }
        } else {
            chooseSprite();
            calculateMove();
        }
    }

}
