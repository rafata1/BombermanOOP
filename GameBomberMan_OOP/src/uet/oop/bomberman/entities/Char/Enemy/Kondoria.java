package uet.oop.bomberman.entities.Char.Enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Kondoria extends Enemy {

    public Kondoria(double xUnit, double yUnit, Image img) {
        super(xUnit, yUnit, img);
        velocity = 0.4;
    }

    @Override
    public void chooseSprite() {
        switch (direction) {
            case 0:
            case 1:
                img = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, animate, 60).getFxImage();
                break;
            case 2:
            case 3:
                img = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, animate, 60).getFxImage();
                break;
        }
    }

    @Override
    public void calculateMove() {


        if(BombermanGame.player1.getTileX() == getTileX()) {
            if(BombermanGame.player1.getTileY() < getTileY()){
                direction = 0;
            }
            if(BombermanGame.player1.getTileY() > getTileY()) {
                direction = 2;
            }
        } else {
            if(BombermanGame.player1.getTileX() < getTileX()) {
                direction = 3;
            }

            if(BombermanGame.player1.getTileX() > getTileX()) {
                direction = 1;
            }
        }

        int xa = 0;
        int ya = 0;

        if(direction == 0) ya--;
        if(direction == 2) ya++;
        if(direction == 3) xa--;
        if(direction == 1) xa++;

        move(xa * velocity, ya * velocity);

    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    @Override
    public void update() {
        animate();
        if(timeToDisappear > 0) {
            timeToDisappear -- ;
            img =Sprite.movingSprite(Sprite.kondoria_dead,Sprite.kondoria_dead, animate, 30).getFxImage();
            if(timeToDisappear == 1) {
                remove();
            }
        } else {
            kill();
            chooseSprite();
            calculateMove();
        }
    }

}
