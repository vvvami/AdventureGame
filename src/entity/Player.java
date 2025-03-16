package entity;

import main.GamePanel;
import util.KeyHandler;

public class Player extends Entity {
    KeyHandler keyHandler;

    public Player() {
        this.setSpeed(4);
        this.keyHandler = GamePanel.keyHandler;

        addSprite("player_sprite_test1");
        addSprite("player_sprite_test2");
        addAnimatedSprite("player_test", 8)
                .addFrame("player_sprite_test1", this)
                .addFrame("player_sprite_test2", this);

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
        this.getSpriteAnimation("player_test").render(this.getRenderer());
    }
}
