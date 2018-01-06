package fr.ensibs.javaFX.graphic;

import fr.ensibs.graphic.Graphic;
import fr.ensibs.graphic.Snapshot;
import fr.ensibs.graphic.SnapshotLayer;
import javafx.scene.canvas.Canvas;

public class FXGraphic implements Graphic{

    private Canvas canvas;

    public FXGraphic(Canvas c){
        this.canvas = c;
    }

    public void drawSnapshot(Snapshot snapshot) {
        SnapshotLayer sl;
        for(int i = 0;i<snapshot.size();i++){
            sl = snapshot.get(i);
            javafx.scene.image.Image img = ((FXImage) sl.getImage()).getImage();
            canvas.getGraphicsContext2D().drawImage(img, sl.getX(), sl.getY(), sl.getImage().getWidth(), sl.getImage().getHeight());
        }
    }
}
