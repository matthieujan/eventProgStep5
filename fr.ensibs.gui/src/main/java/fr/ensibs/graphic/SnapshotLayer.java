package fr.ensibs.graphic;

public class SnapshotLayer {

    private Image image;
    private int x;
    private int y;
    private boolean visible;

    public SnapshotLayer(Image image, int x, int y, boolean visible){
        this.image = image;
        this.x = x;
        this.y = y;
        this.visible = visible;
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
