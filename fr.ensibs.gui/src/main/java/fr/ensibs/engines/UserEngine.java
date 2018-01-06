package fr.ensibs.engines;

import fr.ensibs.collections.FIFO;

public class UserEngine extends ObserverEngine {
    @Override
    public void run(Object o) {
        TimeEvent te = (TimeEvent) o;
        FIFO.getInstance().add(te);
    }
}
