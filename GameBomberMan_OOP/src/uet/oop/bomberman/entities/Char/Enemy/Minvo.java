package uet.oop.bomberman.entities.Char.Enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Minvo extends Enemy {
    public Minvo(double xUnit, double yUnit, Image img) {
        super(xUnit, yUnit, img);
        velocity = 0.8;
    }

    @Override
    public void chooseSprite() {
        switch (direction) {
            case 0:
            case 1:
                img = Sprite.movingSprite(Sprite.minvo_right1, Sprite.minvo_right2, Sprite.minvo_right3, animate, 60).getFxImage();
                break;
            case 2:
            case 3:
                img = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2, Sprite.minvo_left3, animate, 60).getFxImage();
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
            img =Sprite.movingSprite(Sprite.minvo_dead,Sprite.minvo_dead, animate, 30).getFxImage();
            if(timeToDisappear == 1) {
                remove();
            }
        } else {
            chooseSprite();
            calculateMove();
        }
    }
}
