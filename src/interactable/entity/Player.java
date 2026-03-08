package interactable.entity;

import interactable.interactions.Collider;
import main.Game;
import util.KeyHandler;

public class Player extends Entity {
    KeyHandler keyHandler;
    private int jumps = 0;
    private int maxJumps = 2;
    private boolean lastJumpKeyState = false;

    public Player(float x, float y) {
        super(x, y);
        this.setSpeed(5.5f);
        this.keyHandler = Game.keyHandler;
        setCollision(true);
        setCollider(new Collider(this, 0.3, 0.2, 0.7, 1));
    }

    @Override
    public void update() {
        super.update();
        movementUpdate();
        Game.createCamBounds(this.getX(), this.getY());
    }

    private void movementUpdate() {
        if (isGrounded()) {
            jumps = 0;
        }

        int dx = 0;
        int dy = 0;

        if (KeyHandler.upPressed && !lastJumpKeyState && jumps < maxJumps) {
            dy -= 1;
            jumps++;
        }
        lastJumpKeyState = KeyHandler.upPressed;

        if (KeyHandler.downPressed) dy += 1;
        if (KeyHandler.leftPressed) dx -= 1;
        if (KeyHandler.rightPressed) dx += 1;

        if (dx != 0 || dy != 0) {
            float length = (float) Math.sqrt(dx * dx + dy * dy);
            float nx = (dx / length) * getSpeed();
            float ny = (dy / length) * getSpeed();

            move(nx, ny);

            if (nx != 0) {
                setCurrentSprite("player_down");
            }
        } else {
            setCurrentSprite("player_down1");
        }
    }

    @Override
    public void registerSprites() {
        addSprite("player_down1");
        addSprite("player_down2");
        addSprite("player_down3");

        addAnimatedSprite("player_down", 7)
                .addFrame("player_down1")
                .addFrame("player_down2")
                .addFrame("player_down1")
                .addFrame("player_down3")
                .scale(1.5f);

        setSprite("player_down");
    }

}
