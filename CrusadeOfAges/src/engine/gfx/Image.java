package engine.gfx;

import game.Conf;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Image {

    public static final int THUMBW = 120;
    public static final int THUMBH = 60;

    private int w, h;
    int[] p;
    private boolean alpha = false;

    /**
     * Creates Image object using image from : [True] AppData or [False] game assets
     *
     * @param path
     * @param appdata True / False
     */
    public Image(String path, boolean appdata) {
        try {
            BufferedImage image = appdata ? ImageIO.read(new File(path)) : ImageIO.read(Image.class.getResourceAsStream(path));
            w = image.getWidth();
            h = image.getHeight();
            p = image.getRGB(0, 0, w, h, null, 0, w);
            image.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Image(int[] p, int w, int h) {
        this.p = p;
        this.w = w;
        this.h = h;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    /**
     * Returns pixels (array of rgba codes) of Image
     *
     * @return int[]
     */
    public int[] getP() {
        return p;
    }

    public void setP(int x, int y, int value) {
        int pos = x + y * getW();
        if (pos >= 0 && pos < p.length) p[pos] = value;
    }

    public boolean isAlpha() {
        return alpha;
    }

    public void setAlpha(boolean alpha) {
        this.alpha = alpha;
    }

    public Image getThumbnail() {
        BufferedImage origin = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                origin.setRGB(x, y, this.p[x + y * w]);
            }
        }
        BufferedImage thumbnail = new BufferedImage(THUMBW, THUMBH, origin.getType());
        Graphics2D g = thumbnail.createGraphics();
        g.drawImage(origin, 0, 0, THUMBW, THUMBH, null);
        g.dispose();
        int[] p = thumbnail.getRGB(0, 0, THUMBW, THUMBH, null, 0, THUMBW);
        thumbnail.flush();
        return new Image(p, THUMBW, THUMBH);
    }

    public void blank() {
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
               setP(x, y, 0);
            }
        }
    }

    public void save(String rename) {
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                bi.setRGB(x, y, p[x + y * w]);
            }
        }
        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
            String filename;
            if (rename.equals("")) filename = sdf.format(new Date()) + ".png";
            else filename = rename;
            File out = new File(Conf.SM_FOLDER + "/creative_mode/" + filename);
            ImageIO.write(bi, "png", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
