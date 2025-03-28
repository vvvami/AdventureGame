package interactable.entity;

import main.GamePanel;
import render.AnimatedSprite;
import util.KeyHandler;

public class Player extends Entity {
    KeyHandler keyHandler;

    public Player() {
        this.setSpeed(4);
        this.keyHandler = GamePanel.keyHandler;

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
    public void registerSprites() {
        addSprite("player_sprite_test1");
        addSprite("player_sprite_test2");
        addAnimatedSprite("player_test", 12)
                .addFrame("player_sprite_test1")
                .addFrame("player_sprite_test2")
                .scale(1.4f);
        setSprite("player_test");
    }
}
