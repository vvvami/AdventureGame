package interactable;

import interactable.interactions.Collider;
import render.*;
import render.sprite.*;
import util.AABB;
import world.World;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.UUID;

public abstract class Interactable implements Renderable {
    private float x;
    private float y;

    private float vx;
    private float vy;

    private Collider collider;
    private boolean hasCollision = false;

    public Rectangle2D.Float intersectDebug; // test for intersect between two colliding mfers
    public Rectangle2D.Float futColliderDebug;

    private boolean hasGravity = true;
    public boolean isGrounded = false;

    private SpriteRenderer renderer;
    public boolean isRendering;
    private SpriteType currentSprite;
    private ArrayList<SpriteType> spriteLayers = new ArrayList<>();

    private static ArrayList<Interactable> interactableList = new ArrayList<>();

    private UUID ID;

    public Interactable() {
        this(0,0);
    }

    public Interactable(float x, float y) {
        this.x = x;
        this.y = y;
        interactableList.add(this);
        renderer = new SpriteRenderer(this);
        ID = UUID.randomUUID();
        registerSprites();
    }

    @Override
    public void draw() {
        if (currentSprite instanceof AnimatedSprite) {
            getRenderer().renderSpriteAnimation((AnimatedSprite) currentSprite);
        } else {
            getRenderer().renderSprite((Sprite) currentSprite);
        }

        for (SpriteType sprite : spriteLayers) {
            if (sprite instanceof AnimatedSprite) {
                getRenderer().renderSpriteAnimation((AnimatedSprite) sprite);
            } else {
                getRenderer().renderSprite((Sprite) sprite);
            }
        }
    }


    @Override
    public void update() {
        if (currentSprite instanceof AnimatedSprite animatedSprite) {
            renderer.updateSpriteAnimation(animatedSprite);
        }

        if (collider != null) {
            collider.update(this);
        }

        if (this.hasGravity()) {
            applyGravity();
        }

        isGrounded = isGrounded();

    }

    private void applyGravity() {
        if (isGrounded) return;
        vy = Math.min(vy + World.getGravity(), World.maxFallSpeed);
        move(0, vy);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float vx() {
        return vx;
    }

    public float vy() {
        return vy;
    }

    public void vx(float v) {
        this.vx = v;
    }

    public void vy(float v) {
        this.vy = v;
    }

    public void move(float dx, float dy) {
        if (dx == 0 && dy == 0) return;
        AABB selfCollider = this.getCollider().getBox();
        float dx1 = dx;
        float dy1 = dy;
        AABB insctX = new AABB(0,0);
        AABB insctY = new AABB(0,0);
        boolean collidedX = false;
        boolean collidedY = false;

        if (this.collideable()) {
            AABB futureCollider = selfCollider.copy();
            for (Collider collider1 : Collider.list()) {
                if (this.collider == collider1) continue;

                futureCollider.x += dx1;
                if (futureCollider.intersects(collider1.getBox())) {
                    insctX = selfCollider.intersection(collider1.getBox());
                    this.intersectDebug = insctX.toRect();
                    collidedX = true;
                    dx = 0;
                    vx = 0;
                }

                futureCollider.y += dy1;
                if (futureCollider.intersects(collider1.getBox())) {
                    insctY = selfCollider.intersection(collider1.getBox());
                    this.intersectDebug = insctX.toRect();
                    collidedY = true;
                    dy = 0;
                    vy = 0;
                }
            }
            this.futColliderDebug = futureCollider.toRect();
        }

        vx = dx != 0 ? dx : vx;
        vy = dy != 0 ? dy : vy;
        setX(getX() + dx);
        setY(getY() + dy);
    }

    public Interactable setY(float y) {
        this.y = y;
        return this;
    }

    public Interactable setX(float x) {
        this.x = x;
        return this;
    }

    private boolean isGrounded() {
        AABB box = getCollider().getBox();

        AABB feet = new AABB(box.x, box.y + box.height, box.width, 2);

        for (Interactable itrc : Interactable.list()) {
            if (!itrc.collideable()) continue;
            if (itrc == this) continue;

            if (itrc.getCollider().getBox().intersects(feet)) {
                return true;
            }
        }
        return false;
    }

    public void setGrounded(boolean grounded) {
        isGrounded = grounded;
    }

    public SpriteRenderer getRenderer() {
        return renderer;
    }

    @SuppressWarnings(value = "all")
    public Sprite addSprite(String name) {
        if (Sprite.getList().contains(Sprite.getSpriteFromName(name))) {
            return null;
        }

        String filePath = getFullFilePath(name);

        Sprite newSprite = new Sprite(filePath);
        return newSprite;
    }

    private String getFullFilePath(String name) {
        return SpriteRenderer.assetPath + getAssetFolder() + "/"
                + name + SpriteRenderer.spriteFileType;
    }

    public AnimatedSprite addAnimatedSprite(String name) {
        AnimatedSprite.getList().add(new AnimatedSprite(name, 12));
        return (AnimatedSprite) SpriteType.getList().getLast();
    }

    public AnimatedSprite addAnimatedSprite(String name, int speed) {
        AnimatedSprite.getList().add(new AnimatedSprite(name, speed));
        return (AnimatedSprite) SpriteType.getList().getLast();
    }

    public void setSprite(String name) {
        SpriteType sprite = AnimatedSprite.getAnimationFromName(name);
        if (sprite == null) {
            sprite = Sprite.getSpriteFromName(name);
        }

        if (this.currentSprite instanceof AnimatedSprite animatedSprite
        && animatedSprite.getFrameIndex() == 0) {
            return;
        }

        this.currentSprite = sprite;
    }

    public Sprite getSprite() {
        return currentSprite.get();
    }

    public SpriteType getCurrentSprite() {
        return currentSprite;
    }

    @Override
    public void registerSprites() {

    }

    public static void renderInteractablesInList(Graphics2D graphics2D) {
        for (Interactable interactable : interactableList) {
            interactable.renderer.setGraphics2D(graphics2D);
            interactable.draw();
        }
    }


    public static void updateInteractablesInList() {
        for (Interactable interactable : interactableList) {
            interactable.update();
        }
    }

    public static ArrayList<Interactable> list() {
        return interactableList;
    }

    public String getAssetFolder() {
        return "";
    }

    public void setGravity(boolean hasGravity) {
        this.hasGravity = hasGravity;
    }

    public boolean hasGravity() {
        return hasGravity;
    }

    public boolean hasCollision() {
        return hasCollision;
    }

    public boolean hasCollider() {
        return collider != null;
    }

    public boolean collideable() {
        return hasCollision() && hasCollider();
    }

    public void setCollision(boolean hasCollision) {
        this.hasCollision = hasCollision;
    }

    public Collider getCollider() {
        return collider;
    }

    public void setCollider(Collider collider) {
        this.collider = collider;
    }

    public float width() {
        return getCollider().getWidth();
    }

    public float height() {
        return getCollider().getHeight();
    }

    public ArrayList<SpriteType> getSpriteLayers() {
        return spriteLayers;
    }

    public void setCurrentSprite(String name) {
        SpriteType sprite = AnimatedSprite.getAnimationFromName(name);
        if (sprite == null) {
            sprite = Sprite.getSpriteFromName(name);
        }
        this.currentSprite = sprite;
    }

    public void setSpriteLayers(ArrayList<SpriteType> spriteLayers) {
        this.spriteLayers = spriteLayers;
    }

    public void addSpriteLayer(String name) {
        SpriteType sprite = AnimatedSprite.getAnimationFromName(name);
        if (sprite == null) {
            sprite = Sprite.getSpriteFromName(name);
        }
        addSpriteLayer(sprite);
    }

    public void addSpriteLayer(SpriteType sprite) {
        if (spriteLayers.contains(sprite)) {return;}
        spriteLayers.add(sprite);
    }

    public void removeSpriteLayer(String name) {
        SpriteType sprite = AnimatedSprite.getAnimationFromName(name);
        if (sprite == null) {
            sprite = Sprite.getSpriteFromName(name);
        }
        removeSpriteLayer(sprite);
    }

    public void removeSpriteLayer(SpriteType sprite) {
        spriteLayers.remove(sprite);
    }

    public void clearSpriteLayers() {
        spriteLayers.clear();
    }

    public static Interactable getInteractableAtPos(int x, int y) {
        for (Interactable interactable : Interactable.list()) {
            if (interactable.collider != null
                    && interactable.getCollider().getBox().inside(x, y)) {
                return interactable;
            }
        }
        return null;
    }

}
