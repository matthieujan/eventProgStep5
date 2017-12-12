package fr.ensibs.sprite.action;

import fr.ensibs.sprite.Sprite;

/**
 * An action occuring in a moment: a sprite is shown/hidden at a given location
 *
 * @author Pascale Launay
 * @version 2
 */
public class VisibilityAction extends SpriteAction {

    private final boolean visible; // true if the sprite is visible
    private final int x, y;        // the location of the sprite

    //---------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------
    /**
     * Constructor
     *
     * @param sprite the sprite involved by the action
     * @param time the time when the action occurs
     * @param visible true if the sprite is visible
     * @param x the x location of the sprite
     * @param y the y location of the sprite
     */
    public VisibilityAction(Sprite sprite, long time, boolean visible, int x, int y) {
        super(sprite, time);
        this.visible = visible;
        this.x = x;
        this.y = y;
    }

    //---------------------------------------------------------------
    // Getters
    //---------------------------------------------------------------
    /**
     * True if the sprite is visible
     *
     * @return true if the sprite is visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Give the x location of the sprite
     *
     * @return the x location of the sprite
     */
    public int getX() {
        return x;
    }

    /**
     * Give the y location of the sprite
     *
     * @return the y location of the sprite
     */
    public int getY() {
        return y;
    }
}
