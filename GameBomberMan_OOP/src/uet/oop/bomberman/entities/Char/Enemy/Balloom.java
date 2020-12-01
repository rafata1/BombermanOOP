package uet.oop.bomberman.entities.Char.Enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomb.Bomb;
import uet.oop.bomberman.entities.Char.Mob;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Tiles.Brick;
import uet.oop.bomberman.entities.Tiles.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;

import java.util.Random;

public class Balloom extends Enemy {

    public Balloom(double xUnit, double yUnit, Image img) {
        super(xUnit, yUnit, img);
        velocity = 0.2;
    }

    @Override
    public void chooseSprite() {
        switch (direction) {
            case 0:
            case 1:
                img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, animate, 60).getFxImage();
                break;
            case 2:
            case 3:
                img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, animate, 60).getFxImage();
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
            direction = new Random().nextInt(4);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    @Override
    public void update() {
        animate();
        kill();
        if(timeToDisappear > 0) {
            timeToDisappear -- ;
            img =Sprite.movingSprite(Sprite.balloom_dead,Sprite.balloom_dead, animate, 30).getFxImage();
            if(timeToDisappear == 1) {
                remove();
            }
        } else {
            chooseSprite();
            calculateMove();
        }
    }

}
