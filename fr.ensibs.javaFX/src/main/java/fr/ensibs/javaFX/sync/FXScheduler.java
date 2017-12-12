package fr.ensibs.javaFX.sync;

import fr.ensibs.sync.Scheduler;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 * A scheduler to schedule steps in the FX UI thread
 *
 * @author Pascale Launay
 * @version 4
 */
public class FXScheduler extends Scheduler {

    private final Timeline timeline;   // FX timeline
    private boolean playing;           // true if the scheduler has been started

    /**
     * Constructor
     *
     * @param period the delay between each notification
     */
    public FXScheduler(long period) {
        super(period);
        timeline = new Timeline(new KeyFrame(Duration.millis(period), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setChanged();
                notifyObservers();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    @Override
    public synchronized void start() {
        if (!playing) {
            playing = true;
            timeline.play();
        }
    }

    @Override
    public synchronized void stop() {
        if (playing) {
            playing = false;
            timeline.stop();
        }
    }
}
