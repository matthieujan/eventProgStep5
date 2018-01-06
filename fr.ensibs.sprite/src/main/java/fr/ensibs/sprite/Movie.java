package fr.ensibs.sprite;

import fr.ensibs.graphic.Snapshot;
import fr.ensibs.graphic.SnapshotLayer;
import fr.ensibs.sprite.action.SpriteAction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * A movie composed of a list of sequences which stages have all the same size
 *
 * @author Pascale Launay
 * @version 2
 */
public class Movie {

    private final List<Sequence> sequences; // the sequences in the movie
    private int width, height;              // the size of the sequences
    private long duration;                  // the duration of the movie

    //Snapshot attributes
    private int seqIndex;
    private long lastSeqEnd;
    private long time;


    //---------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------
    /**
     * Constructor
     */
    public Movie() {
        this.sequences = new ArrayList<>();
        this.time = 0;
        this.lastSeqEnd=0;
        this.seqIndex=0;
    }

    //---------------------------------------------------------------
    // Getters
    //---------------------------------------------------------------
    /**
     * Give the sequences in the movie
     *
     * @return the sequences
     */
    public List<Sequence> getSequences() {
        return sequences;
    }

    /**
     * Give the sprites of the movie
     *
     * @return the sprites
     */
    public List<Sprite> getSprites() {
        Set<Sprite> sprites = new HashSet<>();
        for (Sequence sequence : sequences) {
            sprites.addAll(sequence.getSprites());
        }
        return new ArrayList<>(sprites);
    }

    /**
     * Give the duration of the movie
     *
     * @return the duration of the movie
     */
    public long getDuration() {
        return duration;
    }

    /**
     * Give the width of the movie
     *
     * @return the width of the movie
     */
    public int getWidth() {
        return width;
    }

    /**
     * Give the height of the movie
     *
     * @return the height of the movie
     */
    public int getHeight() {
        return height;
    }

    /**
     * Modify the model's time and set the state of the movie
     * @param time
     */
    public void setCurrentTime(long time){

        //Sequencement normal
        if(time > this.time){

            //Changement de sequence ?
            if(time-lastSeqEnd>sequences.get(seqIndex).getDuration()){
                sequences.get(seqIndex).setCurrentTime(0);
                lastSeqEnd+=sequences.get(seqIndex).getDuration();
                seqIndex++;
            }
            //SetCurrentTime for current sequence
            sequences.get(seqIndex).setCurrentTime(time-lastSeqEnd);
        }
        // Meme temps (rien ne se passe)
        else if(time == this.time){

        }
        //Retour arriere
        else{
            this.reset();
            setCurrentTime(time);
        }

        this.time = time;
    }

    private void reset() {
        this.seqIndex = 0;
        this.time = 0;
        this.lastSeqEnd = 0;
        for(Sequence seq : sequences){
            seq.reset();
        }
    }


    /**
     * Get a Snapshot of the current state
     * @return
     */
    public Snapshot getSnapshot(){
        return sequences.get(seqIndex).getSnapshot();
    }

    //---------------------------------------------------------------
    // Setters
    //---------------------------------------------------------------
    /**
     * Add a sequence to this movie
     *
     * @param idx the index in the sequence list of this movie
     * @param sequence a sequence to be added to this movie
     * @throws ResourceSizeException if the size of the sequence is not
     * compatible with the other sequences of the movie
     */
    public void addSequence(int idx, Sequence sequence) throws ResourceSizeException {
        if (sequences.isEmpty()) {
            width = sequence.getWidth();
            height = sequence.getHeight();
        } else if (width != sequence.getWidth() || height != sequence.getHeight()) {
            throw new ResourceSizeException(sequence.getWidth(), sequence.getHeight(), width, height);
        }
        sequences.add(idx, sequence);
        duration += sequence.getDuration();
    }

    /**
     * Add a sequence at the end of this movie
     *
     * @param sequence a sequence to be added to this movie
     * @throws ResourceSizeException if the size of the sequence is not
     * compatible with the other sequences of the movie
     */
    public void addSequence(Sequence sequence) throws ResourceSizeException {
        addSequence(sequences.size(), sequence);
    }

    //---------------------------------------------------------------
    // equals, hashCode
    //---------------------------------------------------------------
    @Override
    public boolean equals(Object o) {
        return (o instanceof Movie) && hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.sequences);
        hash = 23 * hash + this.width;
        hash = 23 * hash + this.height;
        hash = 23 * hash + (int) (this.duration ^ (this.duration >>> 32));
        return hash;
    }
}
