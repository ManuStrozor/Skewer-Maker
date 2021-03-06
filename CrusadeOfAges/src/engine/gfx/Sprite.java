package engine.gfx;

public class Sprite extends Image {

    private int w, h;

    public Sprite(String path, int w, int h, boolean ext) {
        super(path, ext);
        this.w = w;
        this.h = h;
    }

    public Image getSprite(int tileX, int tileY) {
        int[] p = new int[w * h];
        System.arraycopy(this.p, (tileX * w) + (tileY * h) * this.getWidth(), p, 0, w * h);
        return new Image(p, w, h);
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }
}
