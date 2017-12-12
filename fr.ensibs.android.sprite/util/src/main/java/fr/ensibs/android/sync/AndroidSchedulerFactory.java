package fr.ensibs.android.sync;

import android.os.Handler;
import fr.ensibs.sync.Scheduler;
import fr.ensibs.sync.SchedulerFactory;

/**
 *  Factory to make schedulers for Android
 *
 * @author Pascale Launay
 * @version 4
 */
public class AndroidSchedulerFactory implements SchedulerFactory {

    private final Handler handler;

    /**
     * Constructor
     *
     * @param handler Android {@link Handler} instance
     */
    public AndroidSchedulerFactory(Handler handler) {
        this.handler = handler;
    }

    @Override
    public Scheduler makeScheduler(long period) {
        return new AndroidScheduler(period, handler);
    }
}
