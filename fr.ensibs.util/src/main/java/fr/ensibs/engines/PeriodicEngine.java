package fr.ensibs.engines;

import fr.ensibs.sync.Scheduler;

import java.util.Observable;
import java.util.Observer;

public abstract class PeriodicEngine implements Engine,Observer{

    private Scheduler scheduler;

    public PeriodicEngine(Scheduler scheduler){
        this.scheduler = scheduler;
        scheduler.addObserver(this);
    }

    @Override
    public void start() {
        scheduler.start();
    }

    @Override
    public void stop() {
        scheduler.stop();
    }
}
