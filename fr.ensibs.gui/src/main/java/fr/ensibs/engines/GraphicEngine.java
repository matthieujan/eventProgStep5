package fr.ensibs.engines;

import fr.ensibs.collections.ObjectHolder;
import fr.ensibs.graphic.Snapshot;
import fr.ensibs.sync.Scheduler;

import java.util.Observable;

public abstract class GraphicEngine extends fr.ensibs.engines.PeriodicEngine {
    public GraphicEngine(Scheduler scheduler) {
        super(scheduler);
    }

    @Override
    public void update(Observable observable, Object o) {
        Snapshot s = (Snapshot) ObjectHolder.pop();
        if(s != null){
            this.draw(s);
        }
    }

    public abstract void draw(Snapshot s);
}
