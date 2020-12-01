package uet.oop.bomberman.entities.Char;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Sound.SoundEffect;
import uet.oop.bomberman.entities.Bomb.Bomb;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends Mob {

    private List<Bomb> bombs = new ArrayList<>();
    private int maxSizeOfBombs = 1;

    public int getBombRadius() {
        return bombRadius;
    }

    private int bombRadius = 1;
    private boolean SpacePressed = false;
    private int lives;
    private int MAX_LIVES = 3;
    private int initX;
    private int initY;

    public int getLives() {
        return lives;
    }

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        initX = x;
        initY = y;
        velocity = 0.3;
        direction = 1;
        lives = MAX_LIVES;
   }

    public int getTileX() {

        int tileX = (int) (x / Sprite.SCALED_SIZE);
        if((x / Sprite.SCALED_SIZE) - tileX > 0.65) {
            tileX++;
        }
        return tileX;

    }

    public int getTileY() {
        int tileY = (int) (y / Sprite.SCALED_SIZE);
        if((y / Sprite.SCALED_SIZE) - tileY > 0.42) {
            tileY++;
        }
        return tileY;
    }

    public void applyBombItem() {
        maxSizeOfBombs++;
    }

    public void applyFlameItem() {
        bombRadius++;
    }

    public void applySpeedItem() {
        velocity += 0.1;
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

    public void move(int xa, int ya) {
        if (xa > 0) direction = 1;
        if (xa < 0) direction = 3;
        if (ya > 0) direction = 2;
        if (ya < 0) direction = 0;
        if (canMove(xa, ya)) {
            x += xa;
            y += ya;
        }
    }

    public void calculateMove() {
        int xa = 0;
        int ya = 0;
        if (Keyboard.UP == true) {
            ya--;
        } else
        if (Keyboard.DOWN == true) {
            ya++;
        } else
        if (Keyboard.LEFT == true) {
            xa--;
        } else
        if (Keyboard.RIGHT == true) {
            xa++;
        }

        if (xa != 0 || ya != 0) {
            move(xa , ya );
            isMoving = true;
        } else {
            isMoving = false;
        }

    }


    public void updateBombs() {
        for (int i = 0; i < bombs.size(); i++) {
            bombs.get(i).update();
        }
    }

    public void clearBombs() {
        for (int i = 0; i < bombs.size(); i++) {
            if (bombs.get(i).isRemoved()) {
                bombs.remove(i);
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        renderBombs();
        gc.drawImage(img, x, y);
    }

    public int getMaxSizeOfBombs() {
        return maxSizeOfBombs;
    }

    @Override
    public void update() {

        if(timeToDisappear > 0) {
            timeToDisappear -- ;
            img = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, animate, 20).getFxImage();
            if(timeToDisappear == 1) {
                lives -- ;
                if(lives > 0) {
                    x = initX * Sprite.SCALED_SIZE;
                    y = initY * Sprite.SCALED_SIZE;
                    timeToDisappear = 0;
                } else
                {
                    BombermanGame.gameOver = true;
                    remove();
                }
            }
        } else
        {
            calculateMove();
            chooseSprite();
            detectPlaceBomb();
        }
        updateBombs();
        animate();
    }

    public void renderBombs() {
        for (int i = 0; i < bombs.size(); i++) {
            bombs.get(i).render(BombermanGame.gc);
        }
    }

    public void placeBomb(int x, int y) {
        Bomb b = new Bomb(x, y, Sprite.bomb.getFxImage());
        b.setBombRadius(bombRadius);
        bombs.add(b);
    }

    public void detectPlaceBomb() {
        clearBombs();
        if(Keyboard.SPACE && bombs.size() < maxSizeOfBombs) {
            SpacePressed = true;
        }

        if (SpacePressed == true && !Keyboard.SPACE && bombs.size() < maxSizeOfBombs) {
            SpacePressed = false;
            placeBomb((int) (x + 10) / Sprite.SCALED_SIZE, (int) (y + 20) / Sprite.SCALED_SIZE);
        }
    }


    public void chooseSprite() {
        switch (direction) {
            case 0:
                img = Sprite.player_up.getFxImage();
                if (isMoving) {
                    img = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, animate, 50).getFxImage();
                }
                break;
            case 1:
                img = Sprite.player_right.getFxImage();
                if (isMoving) {
                    img = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animate, 50).getFxImage();
                }
                break;
            case 2:
                img = Sprite.player_down.getFxImage();
                if (isMoving) {
                    img = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, animate, 50).getFxImage();
                }
                break;
            case 3:
                img = Sprite.player_left.getFxImage();
                if (isMoving) {
                    img = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, animate, 50).getFxImage();
                }
                break;
            default:
                img = Sprite.player_right.getFxImage();
                if (isMoving) {
                    img = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animate, 50).getFxImage();
                }
                break;
        }
    }

    @Override
    public void beKill() {
        if(timeToDisappear > 0 ) {
            return;
        }
        SoundEffect.bomber_die.play();
        timeToDisappear = 120;
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }

    public List<Bomb> getBombs() {
        return bombs;
    }
}
