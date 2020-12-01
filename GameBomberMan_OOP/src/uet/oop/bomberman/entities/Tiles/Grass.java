package uet.oop.bomberman.entities.Tiles;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Grass extends Tile {

    public Grass(int x, int y, Image img) {
        super(x, y, img);
        allowToPassThru = true;
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

    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }
}
