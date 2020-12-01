package uet.oop.bomberman.entities.Tiles;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Tile extends AnimatedEntity {
    public Tile(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

}
