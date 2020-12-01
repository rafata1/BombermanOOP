package uet.oop.bomberman.entities.Bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Sound.SoundEffect;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Char.Enemy.Balloom;
import uet.oop.bomberman.entities.Char.Enemy.Enemy;
import uet.oop.bomberman.entities.Char.Mob;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Tiles.Brick;
import uet.oop.bomberman.entities.Tiles.Grass;
import uet.oop.bomberman.entities.Tiles.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends AnimatedEntity {

    private int timeToExplode = 240; // 60Frames equal 2seconds
    private int explodingDuration = 60;
    private boolean isExploded = false;
    private static int bombRadius;

    private boolean isBlocked[] = {false, false, false, false};
    private int maxRadius[] = {bombRadius, bombRadius , bombRadius ,bombRadius};

    private List<AnimatedEntity> flames = new ArrayList<>();

    public Bomb(double xUnit, double yUnit, Image img) {
        super(xUnit, yUnit, img);
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

    public int getBombRadius() {
        return bombRadius;
    }

    public void setBombRadius(int bombRadius) {
        this.bombRadius = bombRadius;
    }

    public void updateFlames() {
        for (AnimatedEntity f : flames) {
            f.update();
        }
    }

    public void renderFlames() {
        for (AnimatedEntity f : flames) {
            f.render(BombermanGame.gc);
        }
    }

    public void setTimeToExplode(int timeToExplode) {
        this.timeToExplode = timeToExplode;
    }


    public void calculateImpactExplosionAt(int rad, int x, int y, int direction, boolean isTail) {

        if(isBlocked[direction] == true) return;
        Entity impactedEntity = BombermanGame.getEntityAt(x, y);

        if (impactedEntity instanceof Wall) {
            isBlocked[direction] = true;
        }

        if (impactedEntity instanceof Brick) {
            isBlocked[direction] = true;
            // cho no vien gach
            ((Brick)(impactedEntity)).setDurationDisappear(60);
        }

        if (impactedEntity instanceof Bomb) {
            isBlocked[direction] = true;
            ((Bomb) impactedEntity).setTimeToExplode(0);
        }

        if(isBlocked[direction] == true) {
            maxRadius[direction] = rad - 1;
        }

        if (isBlocked[direction] == false) {

            if (direction == 0 || direction == 2) {

                if(isTail == false) {
                    Flame flame = new Flame(x, y, direction, Sprite.explosion_vertical.getFxImage(),false);
                    flames.add(flame);
                } else {
                    if(direction == 0) {
                        Flame flame = new Flame(x, y, direction, Sprite.explosion_vertical_top_last.getFxImage(),true);
                        flames.add(flame);
                    }
                    if(direction == 2) {
                        Flame flame = new Flame(x, y, direction, Sprite.explosion_vertical_down_last.getFxImage(), true);
                        flames.add(flame);
                    }
                }
            } else {
                if(isTail == false) {
                    Flame flame = new Flame(x, y, direction, Sprite.explosion_horizontal.getFxImage(), false);
                    flames.add(flame);
                } else {
                    if(direction == 1) {
                        Flame flame = new Flame(x, y, direction, Sprite.explosion_horizontal_right_last.getFxImage(),true);
                        flames.add(flame);
                    }
                    if(direction == 3) {
                        Flame flame = new Flame(x, y, direction, Sprite.explosion_horizontal_left_last.getFxImage(),true);
                        flames.add(flame);
                    }
                }
            }
        }
    }

    public void killEntities(){
        for(Entity e : BombermanGame.entities) {
            if(e.getTileX() == getTileX() && e.getTileY() == getTileY()) {
                ((Mob) e).beKill();
            }
            else if(e.getTileX() == getTileX() || e.getTileY() == getTileY()) {
                if(e.getTileX() > getTileX()) {
                    if (e.getTileX() - getTileX() <= maxRadius[1]) ((Mob) e).beKill();
                }
                if(e.getTileX() < getTileX()) {
                    if (getTileX() - e.getTileX() <= maxRadius[3]) ((Mob) e).beKill();
                }
                if(e.getTileY() > getTileY()) {
                    if (e.getTileY() - getTileY() <= maxRadius[2]) ((Mob) e).beKill();
                }
                if(e.getTileY() < getTileY()) {
                    if (getTileY() - e.getTileY() <= maxRadius[0]) ((Mob) e).beKill();
                }
            }
        }
    }

    public void explode() {
        img = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, animate, 20).getFxImage();
        SoundEffect.exploision.play();
        if (flames.size() == 0) {
            int tmpX = getTileX();
            int tmpY = getTileY();
            for (int i = 1; i <= bombRadius; i++) {

                calculateImpactExplosionAt(i ,tmpX, tmpY - i, 0, i == bombRadius);
                calculateImpactExplosionAt(i ,tmpX + i, tmpY, 1, i == bombRadius);
                calculateImpactExplosionAt(i, tmpX, tmpY + i, 2, i == bombRadius);
                calculateImpactExplosionAt(i, tmpX - i, tmpY, 3, i == bombRadius);

            }
        } else {
            updateFlames();
            renderFlames();
            killEntities();
        }
    }

    @Override
    public void update() {
        animate();
        if (timeToExplode > 0) {
            timeToExplode--;
            if(timeToExplode == 210) setAllowToPassThru(false);
            img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 80).getFxImage();
        } else {

            if (explodingDuration > 0) {
                explode();
                explodingDuration--;
            } else {
                remove();
            }

        }
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }
}
