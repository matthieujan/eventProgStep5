package fr.ensibs.graphic;

import java.util.ArrayList;

public class Snapshot extends ArrayList<SnapshotLayer> {

    private long time;

    public Snapshot(long time){
        this.time = time;
    }

    public long getTime(){
        return time;
    }
}
