package uet.oop.bomberman.gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class InfomationBar {

    private long beginTime;
    private long countDownTime;

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public void update() {
        countDownTime = 200 - (System.nanoTime() - beginTime) / 1000000000;
        if(countDownTime == 0) {
            BombermanGame.gameOver = true;
            return;
        }
    }

    public void render(GraphicsContext gc) {
        //render a stop watch
        gc.setFill( Color.BLUE);
        gc.setLineWidth(2);
        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 14 );
        gc.setFont( theFont );
        gc.fillText( "Time :" + countDownTime, 30, BombermanGame.HEIGHT * Sprite.SCALED_SIZE + 30 );
        gc.fillText("Bomb Rate: "+ BombermanGame.player1.getMaxSizeOfBombs(), 130, BombermanGame.HEIGHT * Sprite.SCALED_SIZE + 30 );
        gc.fillText("Bomb Radius: "+ BombermanGame.player1.getBombRadius(), 270, BombermanGame.HEIGHT * Sprite.SCALED_SIZE + 30 );
        gc.fillText("Speed : "+ BombermanGame.player1.getVelocity(), 420, BombermanGame.HEIGHT * Sprite.SCALED_SIZE + 30 );
        gc.fillText("Lives: "+ BombermanGame.player1.getLives(), 550, BombermanGame.HEIGHT * Sprite.SCALED_SIZE + 30 );
    }

}
