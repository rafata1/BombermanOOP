package uet.oop.bomberman.entities.Char.Enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Doll extends Enemy {
    public Doll(double xUnit, double yUnit, Image img) {
        super(xUnit, yUnit, img);
        velocity = 0.7;
    }

    @Override
    public void chooseSprite() {
        switch (direction) {
            case 0:
            case 1:
                img = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, animate, 120).getFxImage();
                break;
            case 2:
            case 3:
                img = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, animate, 120).getFxImage();
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
            img =Sprite.movingSprite(Sprite.doll_dead,Sprite.doll_dead, animate, 30).getFxImage();
            if(timeToDisappear == 1) {
                remove();
            }
        } else {
            chooseSprite();
            calculateMove();
        }
    }

}
