package fr.ensibs.sync;

import java.util.Observable;

/**
 * A scheduler that notifies observers periodically
 *
 * @author Pascale Launay
 * @version 4
 */
public abstract class Scheduler extends Observable {

    protected final long period;   // the delay between each notification

    /**
     * Constructor
     *
     * @param period the delay between each notification
     */
    public Scheduler(long period) {
        this.period = period;
    }

    /**
     * Give the scheduler period
     *
     * @return the delay between each notification
     */
    public long getPeriod() {
	return period;
    }

    /**
     * Start notifying observers periodically.
     */
    public abstract void start();

    /**
     * Stop notifying observers
     */
    public abstract void stop();
}
