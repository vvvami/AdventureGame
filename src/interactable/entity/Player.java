package interactable.entity;

import main.GamePanel;
import util.KeyHandler;

public class Player extends Entity {
    KeyHandler keyHandler;

    public Player() {
        this.setSpeed(5.5f);
        this.keyHandler = GamePanel.keyHandler;

    }

    @Override
    public void update() {
        movementUpdate();
        GamePanel.createCamBounds(this.getX(), this.getY());
    }

    private void movementUpdate() {
        int dx = 0;
        int dy = 0;

        if (KeyHandler.upPressed) dy -= 1;
        if (KeyHandler.downPressed) dy += 1;
        if (KeyHandler.leftPressed) dx -= 1;
        if (KeyHandler.rightPressed) dx += 1;

        if (dx != 0 || dy != 0) {
            double length = Math.sqrt(dx * dx + dy * dy);
            double nx = dx / length;
            double ny = dy / length;

            this.setX((float) (this.getX() + (nx * this.getSpeed())));
            this.setY((float) (this.getY() + (ny * this.getSpeed())));
            setCurrentSprite("player_down");
        } else {
            setCurrentSprite("player_down1");
        }
    }

    @Override
    public void registerSprites() {
        addSprite("player_neutral");
        addSprite("player_sad");
        addSprite("player_angry");
        addSprite("player_happy");
        addSprite("player_down1");
        addSprite("player_down2");
        addSprite("player_down3");
        addSprite("square");

        addAnimatedSprite("player_sad_anim", 14)
                .addFrame("player_neutral")
                .addFrame("player_sad");

        addAnimatedSprite("player_angry_anim", 12)
                .addFrame("player_neutral")
                .addFrame("player_angry");

        addAnimatedSprite("player_happy_anim", 13)
                .addFrame("player_neutral")
                .addFrame("player_happy").scale(1.5f);

        addAnimatedSprite("player_down", 7)
                .addFrame("player_down1")
                .addFrame("player_down2")
                .addFrame("player_down1")
                .addFrame("player_down3")
                .scale(1.5f);

        setSprite("player_down");
    }

}
