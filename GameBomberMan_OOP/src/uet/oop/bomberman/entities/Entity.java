package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {


    //Tọa độ X tính từ góc trái trên trong Canvas
    protected double x;
    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected double y;
    protected boolean removed = false;
    protected boolean allowToPassThru = true;
    protected Image img;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(double xUnit, double yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public boolean isAllowToPassThru() {
        return allowToPassThru;
    }

    public void setAllowToPassThru(boolean allowToPassThru) {
        this.allowToPassThru = allowToPassThru;
    }

    public abstract int getTileX();
    public abstract int getTileY();


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void remove() {
        removed = true;
    }

    public boolean isRemoved() {
        return removed;
    }

    public abstract void render(GraphicsContext gc);

    public abstract void update();

    public abstract boolean collide(Entity e);

}
