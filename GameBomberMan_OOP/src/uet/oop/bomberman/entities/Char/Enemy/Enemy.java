package uet.oop.bomberman.entities.Char.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Sound.SoundEffect;
import uet.oop.bomberman.entities.Char.Bomber;
import uet.oop.bomberman.entities.Char.Mob;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Enemy extends Mob {
    public Enemy(double xUnit, double yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public boolean canMove(int xa, int ya) {

        Entity a = BombermanGame.getEntityAt(getTileX() + xa , getTileY() + ya);

        if(direction == 0) {
            if(y + ya * velocity < a.getY() + Sprite.SCALED_SIZE ) {
                return a.isAllowToPassThru();
            }
        }

        if(direction == 2) {
            if(y + ya * velocity + Sprite.SCALED_SIZE > a.getY() - 1) {
                return a.isAllowToPassThru();
            }
        }

        if(direction == 3) {
            if(x + xa * velocity < a.getX() + Sprite.SCALED_SIZE) {
                if(a.isAllowToPassThru() == false) {
                }
                return a.isAllowToPassThru();
            }
        }

        if(direction == 1) {
            if(x + xa * velocity + Sprite.SCALED_SIZE > a.getX() - 1) {
                return a.isAllowToPassThru();
            }
        }

        return true;
    }

    public int pixelToTile(double pixel, double a) {
        int tile = (int)(pixel / Sprite.SCALED_SIZE);
        if((pixel / Sprite.SCALED_SIZE) - tile > a) {
            tile++;
        }
        return tile;
    }

    public int getTileX() {
        return pixelToTile(x,0.5);
    }

    public int getTileY() {
        return pixelToTile(y, 0.5);
    }

    public void move(double xa, double ya) {
        x += xa;
        y += ya;
    }

    public void kill(){
        if(collide(BombermanGame.player1)) {
            beKill();
            BombermanGame.player1.beKill();
        }
    }

    public void beKill() {
        timeToDisappear = 60;
        SoundEffect.enemyDie.play();
    }

    public boolean collide(Entity e) {
        if( getTileX() == e.getTileX() && getTileY() == e.getTileY()) {
            if(e instanceof Bomber) {
                ((Bomber) e).beKill();
            }
            return true;
        }
        return false;
    }

}
