package fr.ensibs.sync;

/**
 * A factory to create schedulers
 *
 * @author Pascale Launay
 * @version 4
 */
public interface SchedulerFactory {

    /**
     * Create a new scheduler
     *
     * @param period the delay between each notification
     * @return a scheduler
     */
    public Scheduler makeScheduler(long period);
}
