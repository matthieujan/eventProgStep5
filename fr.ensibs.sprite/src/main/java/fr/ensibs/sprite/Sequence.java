package fr.ensibs.sprite;

import fr.ensibs.sprite.action.SpriteAction;
import fr.ensibs.collections.SortedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A sequence takes place on a stage and is composed of sprites and actions
 *
 * @author Pascale Launay
 * @version 2
 */
public class Sequence {

    private final long duration;              // the duration of the sequence
    private Stage stage;                      // the stage where the sequence takes place
    private final List<Sprite> sprites;       // the sprites in the sequence
    private final List<SpriteAction> actions; // the actions in the sequence

    //---------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------
    /**
     * Constructor
     *
     * @param duration the duration of the sequence
     * @param stage the stage where the sequence takes place
     */
    public Sequence(long duration, Stage stage) {
        this.duration = duration;
        this.sprites = new ArrayList<>();
        this.actions = new SortedList<>();
        this.stage = stage;
    }

    //---------------------------------------------------------------
    // Getters
    //---------------------------------------------------------------
    /**
     * Give the duration of the sequence
     *
     * @return the duration of the sequence
     */
    public long getDuration() {
        return duration;
    }

    /**
     * Give the stage where the sequence takes place
     *
     * @return the stage where the sequence takes place
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Give the sprites in the sequence
     *
     * @return the sprites in the sequence
     */
    public List<Sprite> getSprites() {
        return sprites;
    }

    /**
     * Give the actions in the sequence
     *
     * @return the actions in the sequence
     */
    public List<SpriteAction> getActions() {
        return actions;
    }

    /**
     * Give the width of the stage of this sequence
     *
     * @return the width of the stage of this sequence
     */
    public int getWidth() {
        return stage.getWidth();
    }

    /**
     * Give the height of the stage of this sequence
     *
     * @return the height of the stage of this sequence
     */
    public int getHeight() {
        return stage.getHeight();
    }

    //---------------------------------------------------------------
    // Setters
    //---------------------------------------------------------------
    /**
     * Add a sprite to this sequence
     *
     * @param idx the index in the sprites list of this sequence
     * @param sprite a sprite to be added to this sequence
     */
    public void addSprite(int idx, Sprite sprite) {
        sprites.add(idx, sprite);
    }

    /**
     * Add a sprite at the end of this sequence
     *
     * @param sprite a sprite to be added to this sequence
     */
    public void addSprite(Sprite sprite) {
        addSprite(sprites.size(), sprite);
    }

    /**
     * Add an action that occurs in this sequence
     *
     * @param action the action to be added
     * @throws IllegalArgumentException if the sprite the new action is related
     * to is not in the sequence
     */
    public void addAction(SpriteAction action) {
        if (!sprites.contains(action.getSprite())) {
            throw new IllegalArgumentException("The sprite the new action is related to is not in the sequence");
        }
        actions.add(action);
    }

    /**
     * Set the stage of the sequence
     *
     * @param stage the stage of the sequence
     * @throws ResourceSizeException if the new stage has a dimension different
     * from the previous one
     */
    public void setStage(Stage stage) throws ResourceSizeException {
        if (stage.getWidth() != getWidth() || stage.getHeight() != getHeight()) {
            throw new ResourceSizeException(stage.getWidth(), stage.getHeight(), getWidth(), getHeight());
        }
        this.stage = stage;
    }

    //---------------------------------------------------------------
    // equals, hashCode
    //---------------------------------------------------------------
    @Override
    public boolean equals(Object o) {
        return (o instanceof Sequence) && hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (int) (this.duration ^ (this.duration >>> 32));
        hash = 17 * hash + Objects.hashCode(this.stage);
        hash = 17 * hash + Objects.hashCode(this.sprites);
        hash = 17 * hash + Objects.hashCode(this.actions);
        return hash;
    }
}
