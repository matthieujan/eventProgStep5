package fr.ensibs.sprite.action;

import fr.ensibs.sprite.Sprite;
import java.util.Objects;

/**
 * An action involving a sprite in a sequence
 *
 * @author Pascale Launay
 * @version 2
 */
public class SpriteAction implements Comparable<SpriteAction> {

    private final Sprite sprite;    // the sprite involved by this action
    private final long time;        // the time when the action occurs

    //---------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------
    /**
     * Constructor
     *
     * @param sprite the sprite involved by this action
     * @param time the time when the action occurs
     */
    public SpriteAction(Sprite sprite, long time) {
        this.sprite = sprite;
        this.time = time;
    }

    //---------------------------------------------------------------
    // Getters
    //---------------------------------------------------------------
    /**
     * Give the sprite involved by this action
     *
     * @return the sprite involved by this action
     */
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Give the time when the action occurs
     *
     * @return the time when the action occurs
     */
    public long getTime() {
        return time;
    }

    //---------------------------------------------------------------
    // Comparable method
    //---------------------------------------------------------------
    /**
     * Compare two actions based on their time
     *
     * @param otherAction an other action
     * @return an integer negative if the current action occurs before the other
     * action
     */
    @Override
    public int compareTo(SpriteAction otherAction) {
        if (time < otherAction.time) {
            return -1;
        }
        if (time > otherAction.time) {
            return 1;
        }
        return 0;
    }

    //---------------------------------------------------------------
    // equals, hashCode
    //---------------------------------------------------------------
    @Override
    public boolean equals(Object o) {
        return (o instanceof SpriteAction) && hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.sprite);
        hash = 53 * hash + (int) (this.time ^ (this.time >>> 32));
        return hash;
    }
}
