package fr.ensibs.android.sync;

import android.os.Handler;
import fr.ensibs.sync.Scheduler;

/**
 * A scheduler to schedule steps in the Android UI thread
 *
 * @author Pascale Launay
 * @version 4
 */
public class AndroidScheduler extends Scheduler {

    private Handler handler;  // Android {@link Handler} instance
    private long timestamp;   // the time of the last notification
    private boolean playing;  // true if the scheduler has been started

    /**
     * Constructor
     *
     * @param period the delay between each notification
     * @param handler Android {@link Handler} instance
     */
    public AndroidScheduler(long period, Handler handler) {
        super(period);
        this.handler = handler;
    }

    @Override
    public synchronized void start() {
        if (!playing) {
            playing = true;
            handler.post(makeTask());
        }
    }

    @Override
    public synchronized void stop() {
        playing = false;
    }

    /**
     * Make a periodic task that notifies the observers and launch the next task after
     * the appropriate delay
     *
     * @return the task
     */
    private Runnable makeTask() {
        return new Runnable() {
            @Override
            public void run() {
                if (playing) {
                    timestamp = System.currentTimeMillis();
                    setChanged();
                    notifyObservers();

                    if (playing) {
                        // launch the next step after the appropriate delay
                        Runnable nextTask = makeTask();
                        long next = timestamp + period;
                        long delay = Math.max(0, next - System.currentTimeMillis());
                        handler.postDelayed(nextTask, Math.max(0, delay));
                    }
                }
            }
        };
    }
}
