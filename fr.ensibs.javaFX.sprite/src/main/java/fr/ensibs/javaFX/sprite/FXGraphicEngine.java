package fr.ensibs.javaFX.sprite;

import fr.ensibs.engines.GraphicEngine;
import fr.ensibs.graphic.Snapshot;
import fr.ensibs.javaFX.graphic.FXGraphic;
import fr.ensibs.sync.Scheduler;
import javafx.scene.canvas.Canvas;

public class FXGraphicEngine extends GraphicEngine {

    private final Canvas canvas;

    public FXGraphicEngine(Canvas canvas, Scheduler scheduler){
        super(scheduler);
        this.canvas = canvas;
    }

    @Override
    public void draw(Snapshot snapshot) {
        FXGraphic fxGraphic = new FXGraphic(canvas);
        fxGraphic.drawSnapshot(snapshot);
    }
}
