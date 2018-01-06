package fr.ensibs.engines;

import fr.ensibs.sync.Scheduler;

import java.util.Observable;
import java.util.Observer;

public abstract class ObserverEngine implements Engine,Observer {

    private boolean running;

    @Override
    public void start() {
        running = true;
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public void update(Observable observable, Object o){
        if(running){
            run(o);
        }
    }

    public abstract void run(Object o);
}
