package uet.oop.bomberman.entities.Char;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Entity;

public abstract class Mob extends AnimatedEntity {

    protected double velocity = 0;
    protected int direction = 0;
    protected int timeToDisappear = 0;

    public Mob(double xUnit, double yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public double getVelocity() {
        return velocity;
    }
    public abstract void chooseSprite();
    public abstract void beKill();
    public abstract void calculateMove();

}
