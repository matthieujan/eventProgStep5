package fr.ensibs.javaFX.sprite;

import static fr.ensibs.javaFX.sprite.Status.PAUSE;
import static fr.ensibs.javaFX.sprite.Status.START;
import static fr.ensibs.javaFX.sprite.Status.STOP;

import fr.ensibs.engines.TimeEvent;
import fr.ensibs.engines.TimeObservable;
import fr.ensibs.sync.Scheduler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.TimeZone;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * The status bar at the bottom of the GUI for the buttons and the time display
 *
 * @author Pascale Launay
 * @version 4
 */
public class StatusBar extends BorderPane {

    public static final String STOP_TXT = "\u2B1B";
    public static final String START_TXT = "\u25B6";
    public static final String PAUSE_TXT = "\u25EB";

    private static final DateFormat MS_FORMAT = new SimpleDateFormat("mm:ss", Locale.getDefault());
    private static final DateFormat HMS_FORMAT = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

    static {
        MS_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
        HMS_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    private final Label timeLabel;            // label that displays the time
    private final Slider timeSlider;          // slider that shows the time
    private Button startButton, stopButton;   // buttons

    private Status status;                    // the status: start/stop/pause
    private long duration;                    // the duration of the movie (in s)s

    private final Scheduler scheduler;        // scheduler to increment the slider periodically

    //---------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------
    /**
     * Make the status bar to be displayed at the bottom of the stage
     *
     * @param duration the duration of the movie (in s)
     * @param scheduler scheduler to increment the slider periodically
     */
    public StatusBar(long duration, Scheduler scheduler) {
        this.scheduler = scheduler;
        this.timeSlider = makeSlider(duration);
        setCenter(timeSlider);
        setLeft(makeButtonsBar());
        this.timeLabel = makeLabel(duration);
        setRight(timeLabel);

        this.scheduler.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object o1) {
                tick();
            }
        });
        this.status = STOP;
        this.duration = duration;
    }

    //---------------------------------------------------------------
    // Buttons and slider actions
    //---------------------------------------------------------------
    /**
     * Action called when the start button is clicked
     */
    private void onStart() {
        if (status == START) {
            setStatus(PAUSE);
        } else {
            setStatus(START);
        }
    }

    /**
     * Action called when the stop button is clicked
     */
    private void onStop() {
        setStatus(STOP);
    }

    /**
     * Action called when the slider value changes
     */
    private void timeChanged(long oldValue, long newValue) {
        if (newValue != oldValue) {
            timeLabel.setText(timeToString(newValue, duration));
            TimeObservable.getInstance().notifyObservers(new TimeEvent(newValue));
        }
    }

    //---------------------------------------------------------------
    // Status management
    //---------------------------------------------------------------
    /**
     * Change the status: start/stop/pause
     *
     * @param status the new status
     */
    private void setStatus(Status status) {
        if (this.status != status) {
            this.status = status;
            switch (status) {
                case START:
                    startButton.setText(PAUSE_TXT);
                    timeSlider.setDisable(true);
                    scheduler.start();
                    break;
                case STOP:
                    timeSlider.setValue(0);
                case PAUSE:
                    startButton.setText(START_TXT);
                    timeSlider.setDisable(false);
                    scheduler.stop();
                    break;
            }
        }
    }

    //---------------------------------------------------------------
    // Private methods
    //---------------------------------------------------------------
    /**
     * Increment the time
     */
    private void tick() {
        timeSlider.increment();
    }

    /**
     * Make the buttons to be displayed at the left of the status bar
     *
     * @return the node to be displayed
     */
    private Node makeButtonsBar() {
        HBox buttonsBar = new HBox(5);
        startButton = new Button(START_TXT);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                onStart();
            }
        });
        stopButton = new Button(STOP_TXT);
        stopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                onStop();
            }
        });
        ObservableList<Node> children = buttonsBar.getChildren();
        children.add(startButton);
        children.add(stopButton);
        return buttonsBar;
    }

    /**
     * Make the time slider to be displayed at the center of the status bar
     *
     * @param duration the maximum value of the slider (in s)
     * @return the node to be displayed
     */
    private Slider makeSlider(long duration) {
	long period = scheduler.getPeriod();
        Slider slider = new Slider(0, duration, 0);
        BorderPane.setMargin(slider, new Insets(0, 5, 0, 5));
        slider.setBlockIncrement(period/1000.0);
	double r = 1000.0/period;
        long majorUnit = (duration < 600 ? Math.round(60*r) :
			  duration < 3600 ? Math.round(300*r) :
			  Math.round(900*r));
        int minorCount = (int)(duration < 600 ? Math.round(5*r) :
			       duration < 3600 ? Math.round(4*r) :
			       Math.round(2*r));
        slider.setMajorTickUnit(majorUnit);
        slider.setMinorTickCount(minorCount);
        slider.setShowTickMarks(true);
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                timeChanged(Math.round((double) oldValue), Math.round((double) newValue));
            }
        });
        return slider;
    }

    /**
     * Make the time label to be displayed at the right of the status bar
     *
     * @param duration the duration in s
     * @return the node to be displayed
     */
    private Label makeLabel(long duration) {
        Label label = new Label(timeToString(0, duration));
        return label;
    }

    /**
     * Give a string representation of a time in s
     *
     * @param time the time in s
     * @param duration the duration in s
     * @return the string representation of the time
     */
    private String timeToString(long time, long duration) {
        if (duration < 3600) {
            return MS_FORMAT.format(time * 1000)
                    + "/" + MS_FORMAT.format(duration * 1000);
        }
        return HMS_FORMAT.format(time * 1000)
                + "/" + HMS_FORMAT.format(duration * 1000);
    }
}
