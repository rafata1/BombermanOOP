package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import uet.oop.bomberman.Sound.SoundEffect;
import uet.oop.bomberman.entities.Char.Bomber;
import uet.oop.bomberman.entities.Char.Enemy.*;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Item.Portal;
import uet.oop.bomberman.entities.PowerupItem.PowerUp;
import uet.oop.bomberman.entities.Tiles.Brick;
import uet.oop.bomberman.entities.Tiles.Grass;
import uet.oop.bomberman.entities.Tiles.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.gui.InfomationBar;
import uet.oop.bomberman.input.Keyboard;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BombermanGame extends Application {

    public static int WIDTH = 31;
    public static int HEIGHT = 13;
    public static boolean gameOver = false;
    public static int colOfMap;
    public static int rowOfMap;

    private static boolean nextLevel = true;
    private static int curLevel = 0;
    private static int timeDurationToRenderLevel = 0;

    public static GraphicsContext gc;
    private Canvas canvas;
    private static InfomationBar infomationBar = new InfomationBar();
    public static Bomber player1;
    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    public static List<PowerUp> powerUps = new ArrayList<>();
    private long beginTime = 0;

    public static void createMap(String textFileOfMap) {
        entities.clear();
        stillObjects.clear();

        File myTextFile = new File(textFileOfMap);
        try {
            Scanner sc = new Scanner(myTextFile);

            rowOfMap = sc.nextInt();
            colOfMap = sc.nextInt();

            sc.nextLine(); //đọc đệm file
            for (int i = 0; i < rowOfMap; i++) {
                String curRow = sc.nextLine();
                for (int j = 0; j < colOfMap; j++) {
                    switch (curRow.charAt(j)) {
                        case '#':
                            stillObjects.add(new Wall(j, i, Sprite.wall.getFxImage()));
                            break;
                        case ' ':
                            stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            break;
                        case '*':
                            stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
                            break;
                        case 'f':
                            Brick brick = new Brick(j, i, Sprite.brick.getFxImage());
                            brick.setBehindItem(new PowerUp(j, i, Sprite.powerup_flames.getFxImage(), "flame item"));
                            stillObjects.add(brick);
                            break;
                        case 'b':
                            Brick brick1 = new Brick(j, i, Sprite.brick.getFxImage());
                            brick1.setBehindItem(new PowerUp(j, i, Sprite.powerup_bombs.getFxImage(), "bomb item"));
                            stillObjects.add(brick1);
                            break;
                        case 's':
                            Brick brick2 = new Brick(j, i, Sprite.brick.getFxImage());
                            brick2.setBehindItem(new PowerUp(j, i, Sprite.powerup_speed.getFxImage(), "speed item"));
                            stillObjects.add(brick2);
                            break;
                        case 'x':
                            Brick brick3 = new Brick(j, i, Sprite.brick.getFxImage());
                            brick3.setBehindItem(new Portal(j, i, Sprite.portal.getFxImage(), "portal"));
                            brick3.getBehindItem().setAllowToPassThru(false);
                            stillObjects.add(brick3);
                            break;
                        case '1':
                            stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            entities.add(new Balloom(j, i, Sprite.balloom_left1.getFxImage()));
                            break;
                        case '2':
                            stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            entities.add(new Oneal(j, i, Sprite.oneal_right1.getFxImage()));
                            break;
                        case '3':
                            stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            entities.add(new Kondoria(j, i, Sprite.kondoria_right1.getFxImage()));
                            break;
                        case '4':
                            stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            entities.add(new Minvo(j, i, Sprite.minvo_left1.getFxImage()));
                            break;
                        case '5':
                            stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            entities.add(new Doll(j, i, Sprite.doll_left1.getFxImage()));
                            break;
                        case 'p':
                            stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            player1 = new Bomber(j, i, Sprite.player_right.getFxImage());
                            entities.add(player1);
                            break;

                    }
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void renderGameOver() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT + 40);
        gc.setFill(Color.RED);
        gc.setLineWidth(2);
        Font theFont = Font.font("Times New Roman", FontWeight.BOLD, 48);
        gc.setFont(theFont);
        gc.fillText("Game Over", 350, 250);
    }

    public static void renderLevel() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT + 40);
        gc.setFill(Color.RED);
        gc.setLineWidth(2);
        Font theFont = Font.font("Times New Roman", FontWeight.BOLD, 48);
        gc.setFont(theFont);
        gc.fillText("Level " + curLevel, 350, 250);
    }

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT + 40);
        gc = canvas.getGraphicsContext2D();

        goToNextLevel();
        SoundEffect.backgroundMusic.play();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();


        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long l) {
                render();
                update();
            }

        };
        timer.start();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                switch (e.getCode()) {
                    case UP:
                        Keyboard.UP = true;
                        break;
                    case W:
                        Keyboard.UP = true;
                        break;
                    case DOWN:
                        Keyboard.DOWN = true;
                        break;
                    case S:
                        Keyboard.DOWN = true;
                        break;
                    case LEFT:
                        Keyboard.LEFT = true;
                        break;
                    case A:
                        Keyboard.LEFT = true;
                        break;
                    case RIGHT:
                        Keyboard.RIGHT = true;
                        break;
                    case D:
                        Keyboard.RIGHT = true;
                        break;
                    case SPACE:
                        Keyboard.SPACE = true;
                        break;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {

                switch (e.getCode()) {
                    case UP:
                        Keyboard.UP = false;
                        break;
                    case W:
                        Keyboard.UP = false;
                        break;
                    case DOWN:
                        Keyboard.DOWN = false;
                        break;
                    case S:
                        Keyboard.DOWN = false;
                        break;
                    case LEFT:
                        Keyboard.LEFT = false;
                        break;
                    case A:
                        Keyboard.LEFT = false;
                        break;
                    case RIGHT:
                        Keyboard.RIGHT = false;
                        break;
                    case D:
                        Keyboard.RIGHT = false;
                        break;
                    case SPACE:
                        Keyboard.SPACE = false;
                        break;
                }

            }
        });
    }

    public static void goToNextLevel() {
        curLevel++;
        timeDurationToRenderLevel = 300;
        nextLevel = true;
        createMap("res/levels/Level"+curLevel+".txt");
        infomationBar.setBeginTime(System.nanoTime());
    }

    public void clearBrick() {
        for (int i = 0; i < stillObjects.size(); i++) {
            Entity b = stillObjects.get(i);
            if (b instanceof Brick && b.isRemoved()) {
                if (((Brick) b).getBehindItem() == null) {
                    stillObjects.add(new Grass(b.getTileX(), b.getTileY(), Sprite.grass.getFxImage()));
                } else {
                    powerUps.add(((Brick) b).getBehindItem());
                }
                stillObjects.remove(i);
            }
        }
    }

    public void clearPowerUps() {
        for (int i = 0; i < powerUps.size(); i++) {
            if (powerUps.get(i).isRemoved()) {
                stillObjects.add(new Grass(powerUps.get(i).getTileX(), powerUps.get(i).getTileY(), Sprite.grass.getFxImage()));
                powerUps.remove(i);
            }
        }
    }

    public void clearEntities() {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).isRemoved()) {
                entities.remove(i);
            }
        }
    }

    public void update() {
        entities.forEach(g -> g.update());
        stillObjects.forEach(g -> g.update());
        powerUps.forEach(g -> g.update());
        infomationBar.update();
        clearBrick();
        clearPowerUps();
        clearEntities();
    }

    public void render() {

        if (gameOver == true) {
            renderGameOver();
            return;
        }

        if (nextLevel == true) {
            if (timeDurationToRenderLevel > 0) {
                renderLevel();
                timeDurationToRenderLevel--;
            } else {
                nextLevel = false;
            }
            return;
        }

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        infomationBar.render(gc);
        powerUps.forEach(g -> g.render(gc));
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }

    public static Entity getEntityAt(int x, int y) {

        for (Entity e : player1.getBombs()) {
            if (e.getTileX() == x && e.getTileY() == y) return e;
        }

        for (Entity e : stillObjects) {
            if (e.getTileX() == x && e.getTileY() == y) return e;
        }

        for (Entity e : entities) {
            if (e.getTileX() == x && e.getTileY() == y) return e;
        }

        for (Entity e : powerUps) {
            if (e.getTileX() == x && e.getTileY() == y) return e;
        }

        return null;
    }

    public static ArrayList<Entity> getMobAt(int x, int y) {
        ArrayList<Entity> mobs = new ArrayList<>();
        for (Entity e : entities) {
            if (e.getTileX() == x && e.getTileY() == y) mobs.add(e);
        }
        return mobs;
    }

}
