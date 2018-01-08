package fr.ensibs.android.sprite;

import fr.ensibs.engines.GraphicEngine;
import fr.ensibs.graphic.Snapshot;
import fr.ensibs.android.graphic.AndroidGraphic;
import fr.ensibs.sync.Scheduler;
import android.graphics.Canvas;

public class AndroidGraphicEngine extends GraphicEngine {

    private final Canvas canvas;

    public AndroidGraphicEngine(Canvas canvas, Scheduler scheduler){
        super(scheduler);
        this.canvas = canvas;
    }

    @Override
    public void draw(Snapshot snapshot) {
        AndroidGraphic androidGraphic = new AndroidGraphic(canvas);
        AndroidGraphic.drawSnapshot(snapshot);
    }
}
