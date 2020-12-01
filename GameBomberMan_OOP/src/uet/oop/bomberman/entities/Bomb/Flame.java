package uet.oop.bomberman.entities.Bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Flame extends AnimatedEntity {

    private int direction = 0;
    private boolean isTail;
    public Flame(double xUnit, double yUnit, int direction, Image img, boolean isTail) {
        super(xUnit, yUnit, img);
        this.direction = direction;
        this.isTail = isTail;
    }

    public int getTileX(){
        return (int) (x/ Sprite.SCALED_SIZE);
    }

    public int getTileY() {
        return (int) (y/Sprite.SCALED_SIZE);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img,x ,y);
    }

    @Override
    public void update() {
        animate();

        if(isTail == true) {
            switch(direction) {
                case 0:
                    img = Sprite.movingSprite(Sprite.explosion_vertical_top_last, Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last2, animate, 20).getFxImage();
                    break;
                case 1:
                    img = Sprite.movingSprite(Sprite.explosion_horizontal_right_last, Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last2, animate, 20).getFxImage();
                    break;
                case 2:
                    img = Sprite.movingSprite(Sprite.explosion_vertical_down_last, Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last2, animate, 20).getFxImage();
                    break;
                case 3:
                    img = Sprite.movingSprite(Sprite.explosion_horizontal_left_last, Sprite.explosion_horizontal_left_last1, Sprite.explosion_horizontal_left_last2, animate, 20).getFxImage();
                    break;
            }
        } else {

            switch(direction) {
                case 0:
                case 2:
                    img = Sprite.movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2, animate, 20).getFxImage();
                    break;
                case 1:
                case 3:
                    img = Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, animate, 20).getFxImage();
                    break;
            }

        }
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }
}
