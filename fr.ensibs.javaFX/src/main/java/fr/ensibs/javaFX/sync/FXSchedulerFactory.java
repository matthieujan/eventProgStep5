package fr.ensibs.javaFX.sync;

import fr.ensibs.sync.Scheduler;
import fr.ensibs.sync.SchedulerFactory;

/**
 * Factory to make schedulers for JavaFX
 *
 * @author Pascale Launay
 * @version 4
 */
public class FXSchedulerFactory implements SchedulerFactory {

    @Override
    public Scheduler makeScheduler(long period) {
        return new FXScheduler(period);
    }
}
