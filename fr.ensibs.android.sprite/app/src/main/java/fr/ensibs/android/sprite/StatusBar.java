package fr.ensibs.android.sprite;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import fr.ensibs.sync.Scheduler;
import fr.ensibs.engines.TimeEvent;
import fr.ensibs.engines.TimeObservable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import static fr.ensibs.android.sprite.Status.PAUSE;
import static fr.ensibs.android.sprite.Status.START;
import static fr.ensibs.android.sprite.Status.STOP;

/**
 * The status bar at the bottom of the GUI for the buttons and the time display
 *
 * @author Pascale Launay
 * @version 4
 */
public class StatusBar {

    private static final DateFormat MS_FORMAT = new SimpleDateFormat("mm:ss", Locale.getDefault());
    private static final DateFormat HMS_FORMAT = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

    private Activity activity;
    private CharSequence startText, pauseText;

    private TextView timeLabel;  // numeric display of time and duration
    private SeekBar timeBar;     // bar to display the time (slider)
    private Button startButton, stopButton; // start and stop buttons

    private Status status;       // the status: start/stop/pause
    private int duration;        // the duration of the movie (in s)
    private int progress;        // current progress value of the time bar

    private Scheduler scheduler; // scheduler to increment the time periodically

    //---------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------
    /**
     * Constructor
     *
     * @param activity the parent activity
     * @param duration the movie total duration
     * @param scheduler scheduler to increment the time periodically
     */
    public StatusBar(Activity activity, int duration, Scheduler scheduler) {
        // initialize the bar components in the activity
        this.activity = activity;
        this.startText = activity.getText(R.string.start_button);
        this.pauseText = activity.getText(R.string.pause_button);

        makeTime(duration);
        makeButtons();

        this.duration = duration;
        this.status = STOP;
        this.scheduler = scheduler;
        this.scheduler.addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object o) {
                tick();
            }
        });
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
    private void timeChanged(int progress) {
        if (this.progress != progress) {
            this.progress = progress;
            timeLabel.setText(timeToString(progress, duration));
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
                    startButton.setText(pauseText);
                    timeBar.setEnabled(false);
                    scheduler.start();
                    break;
                case STOP:
                    timeBar.setProgress(0);
                case PAUSE:
                    startButton.setText(startText);
                    timeBar.setEnabled(true);
                    scheduler.stop();
                    break;
            }
        }
    }

    //---------------------------------------------------------------
    // Private methods
    //---------------------------------------------------------------
    /**
     * Give a string representation of a time and duration
     *
     * @param time     the time in s
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

    /**
     * Initialize the start and stop buttons.
     */
    private void makeButtons() {
        startButton = (Button) activity.findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStart();
            }
        });
        stopButton = (Button) activity.findViewById(R.id.stop_button);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStop();
            }
        });
    }

    /**
     * Make the time bar and label.
     */
    private void makeTime(int duration) {
        timeLabel = (TextView) activity.findViewById(R.id.time_label);
        timeBar = (SeekBar) activity.findViewById(R.id.time_bar);

        timeBar.setMax(duration);
        timeBar.setProgress(0);
        timeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                timeChanged(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // DO NOTHING
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // DO NOTHING
            }
        });
        timeLabel.setText(timeToString(0, duration));
    }

    /**
     * Make a task that increment the time bar and launch a new task 1s later
     *
     * @return the task
     */
    private void tick() {
        timeBar.setProgress(progress + 1);
    }
}
