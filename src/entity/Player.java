package entity;

import main.GamePanel;
import util.KeyHandler;

public class Player extends Entity {
    KeyHandler keyHandler;

    public Player() {
        this.setSpeed(4);
        this.keyHandler = GamePanel.keyHandler;

        this.addSprite("walk1", "entities/player/player_sprite_test.png");
    }

    @Override
    public void update() {
        if (keyHandler.upPressed) {
            this.setY(this.getY() - this.getSpeed());
        } else if (keyHandler.downPressed) {
            this.setY(this.getY() + this.getSpeed());
        }

        if (keyHandler.leftPressed) {
            this.setX(this.getX() - this.getSpeed());
        } else if (keyHandler.rightPressed) {
            this.setX(this.getX() + this.getSpeed());
        }
    }

    @Override
    public void draw() {
        this.drawSprite("walk1");
    }
}
