package fr.ensibs.sprite.action;

import fr.ensibs.sprite.Sprite;

/**
 * A linear motion of a sprite that occurs between a start time and a end time
 *
 * @author Pascale Launay
 * @version 2
 */
public class MotionAction extends SpriteAction {

    private final long endTime;       // the end time of the motion
    private final int startX, startY; // the start location of the sprite
    private final int endX, endY;     // the end location of the sprite

    //---------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------
    /**
     * Constructor
     *
     * @param sprite the sprite involved by the action
     * @param startTime the start time of the motion
     * @param endTime the end time of the motion
     * @param startX the start x location of the sprite
     * @param startY the start y location of the sprite
     * @param endX the end x location of the sprite
     * @param endY the end y location of the sprite
     */
    public MotionAction(Sprite sprite, long startTime, long endTime, int startX, int startY, int endX, int endY) {
        super(sprite, startTime);
        this.endTime = endTime;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    //---------------------------------------------------------------
    // Getters
    //---------------------------------------------------------------
    /**
     * Give the end time of the motion
     *
     * @return the end time of the motion
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * Give the start x location of the sprite
     *
     * @return the start x location of the sprite
     */
    public int getStartX() {
        return startX;
    }

    /**
     * Give the start y location of the sprite
     *
     * @return the start y location of the sprite
     */
    public int getStartY() {
        return startY;
    }

    /**
     * Give the end x location of the sprite
     *
     * @return the end x location of the sprite
     */
    public int getEndX() {
        return endX;
    }

    /**
     * Give the end y location of the sprite
     *
     * @return the end y location of the sprite
     */
    public int getEndY() {
        return endY;
    }
}
