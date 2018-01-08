package fr.ensibs.android.graphic;

import fr.ensibs.graphic.Graphic;
import fr.ensibs.graphic.Snapshot;
import fr.ensibs.graphic.SnapshotLayer;
import android.graphics.Canvas;

public class AndroidGraphic implements Graphic {

    private Canvas canvas;

    public AndroidGraphic(Canvas c){
        this.canvas = c;
    }

    public void drawSnapshot(Snapshot snapshot) {
        Paint p;
        SnapshotLayer sl;

        for(int i = 0;i<snapshot.size();i++){
            sl = snapshot.get(i);
            p=new Paint();

            btm = sl.getImage().getBitmap();
            canvas.drawBitmap(btm, 0, 0, p);
        }
    }

}


