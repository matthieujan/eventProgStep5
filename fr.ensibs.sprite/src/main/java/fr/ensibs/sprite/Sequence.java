package fr.ensibs.sprite;

import fr.ensibs.graphic.Snapshot;
import fr.ensibs.graphic.SnapshotLayer;
import fr.ensibs.sprite.action.MotionAction;
import fr.ensibs.sprite.action.SpriteAction;
import fr.ensibs.collections.SortedList;
import fr.ensibs.sprite.action.VisibilityAction;

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
    private long time;

    private List<SpriteAction> actionsDone;
    private List<MotionAction> actionsDoing;


    //---------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------

    /**
     * Constructor
     *
     * @param duration the duration of the sequence
     * @param stage    the stage where the sequence takes place
     */
    public Sequence(long duration, Stage stage) {
        this.duration = duration;
        this.sprites = new ArrayList<>();
        this.actions = new SortedList<>();
        this.actionsDone = new SortedList<>();
        this.actionsDoing = new SortedList();
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
        //Modified to take into account that we transfert action from list to list but want to keep a getter for all Actions
        List<SpriteAction> res = new SortedList<>();
        res.addAll(actions);
        res.addAll(actionsDoing);
        res.addAll(actionsDone);

        return res;
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
     * @param idx    the index in the sprites list of this sequence
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
     *                                  to is not in the sequence
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
     *                               from the previous one
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

    //TODO
    public void setCurrentTime(long time) {
        //If normal time flow
        if (this.time < time) {
            if (time > duration) {
                //Error
            } else {
                List<SpriteAction> toRemove =  new ArrayList<>();
                //First we check all pending action
                for (SpriteAction a : actions) {
                    //If action time is smaller than current time, we do the action
                    if (a.getTime() < time) {
                        //Setting visibility and storing them in actionsDone
                        if (a.getClass().equals(VisibilityAction.class)) {
                            a.getSprite().setVisible(((VisibilityAction) a).isVisible());
                            toRemove.add(a);
                            actionsDone.add(a);
                            //Setting motion start and storing them in actionsDoing
                        } else if (a.getClass().equals(MotionAction.class)) {
                            a.getSprite().setX(((MotionAction) a).getStartX());
                            a.getSprite().setY(((MotionAction) a).getStartY());
                            toRemove.add(a);
                            actionsDoing.add((MotionAction) a);
                        }
                    }
                }
                //Removing done or doing actions from the list
                for(SpriteAction a : toRemove){
                    actions.remove(a);
                }
                toRemove.clear();

                //Checking all current motion action
                for(MotionAction a : actionsDoing){
                    //Checking finished action
                    if(a.getEndTime()<time){
                        a.getSprite().setX(a.getEndX());
                        a.getSprite().setY(a.getEndY());
                        toRemove.add(a);
                    }
                    //Moving the others
                    else{
                        int deltaX = a.getEndX()-a.getStartX();
                        int deltaY = a.getEndY()-a.getStartY();
                        long num = time-a.getTime();
                        long denum = a.getEndTime()-a.getTime();
                        //If instant move
                        if(denum == 0){
                            a.getSprite().setX(a.getEndX());
                            a.getSprite().setY(a.getEndY());
                            toRemove.add(a);
                        }else{
                            a.getSprite().setX((int)(deltaX*num/denum));
                            a.getSprite().setY((int)(deltaY*num/denum));
                        }
                    }
                }

                //Remove done actions from the doing list
                for(SpriteAction a : toRemove){
                    actionsDoing.remove(a);
                }
                toRemove.clear();

                for(Sprite s : this.sprites){
                    s.setCurrentTime(time);
                }
            }
        }
        //if reverse time flow
        else if (this.time > time) {

        }
        this.time = time;
    }

    public Snapshot getSnapshot() {
        Snapshot snap = new Snapshot(time);
        snap.add(new SnapshotLayer(stage.getBackground(),0,0));
        SnapshotLayer snapLayer;
        for(Sprite s : sprites){
            if(s.isVisible()){
                snapLayer = new SnapshotLayer(s.getCurrentImage(),s.getX(),s.getY());
                snap.add(snapLayer);
            }
        }
        return snap;
    }

    public void reset() {
        actions.addAll(actionsDoing);
        actions.addAll(actionsDone);
        actionsDoing.clear();
        actionsDone.clear();
        this.time = 0;
        for(Sprite s : sprites){
            s.reset();
        }
    }
}
