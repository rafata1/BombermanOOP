package uet.oop.bomberman.entities.Tiles;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.PowerupItem.PowerUp;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Tile {

    public Brick(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        allowToPassThru = false;
    }
    private PowerUp behindItem = null;
    private int durationDisappear = 0;

    public PowerUp getBehindItem() {
        return behindItem;
    }

    public void setBehindItem(PowerUp behindItem) {
        this.behindItem = behindItem;
    }

    public void setDurationDisappear(int durationDisappear) {
        this.durationDisappear = durationDisappear;
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

    @Override
    public void update() {
        animate();
        if(durationDisappear > 0) {
            durationDisappear--;
            img = Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, animate, 50).getFxImage();
            if(durationDisappear == 1) {
                remove();
            }
        }
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }
}
