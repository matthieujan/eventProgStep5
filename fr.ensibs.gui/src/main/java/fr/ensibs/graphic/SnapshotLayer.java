package fr.ensibs.graphic;

public class SnapshotLayer {

    private Image image;
    private int x;
    private int y;

    public SnapshotLayer(Image image, int x, int y){
        this.image = image;
        this.x = x;
        this.y = y;
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
