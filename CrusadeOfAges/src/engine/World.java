package engine;

import java.util.ArrayList;

public class World {

    private ArrayList<Bloc> blocs;
    private Level level;

    public World() {
        level = null;
        blocs = new ArrayList<>();
        blocs.add(new Bloc("free",                  0, 6,5, false));
        blocs.add(new Bloc("wall",                  0, 1,0, false));
        blocs.add(new Bloc("wall",                  0x00ffffff, 1,0, false));
        blocs.add(new Bloc("wall",                  0xffffffff, 1,0, false));
        blocs.add(new Bloc("water",                 0xff0000ef, 3,3, false));
        blocs.add(new Bloc("floor",                 0xff000000, 0,0, true));
        blocs.add(new Bloc("slime",                 0xff777777, 0,2, true));
        blocs.add(new Bloc("ladder",                0xff009900, 0,1, false));
        blocs.add(new Bloc("under_shadow",          0xff666666, 0,3, false));
        blocs.add(new Bloc("ground_spikes",         0xffff0000, 1,1, false));
        blocs.add(new Bloc("ground_spikes_blooded", 0xff990000, 1,2, false));
        blocs.add(new Bloc("ceiling_spikes",        0xffff00ff, 2,1, false));
        blocs.add(new Bloc("ceiling_spikes_blooded",0xff990099, 2,2, false));
        blocs.add(new Bloc("lever_left",            0xffe1e1e1, 1,3, false));
        blocs.add(new Bloc("lever_right",           0xffe2e2e2, 2,3, false));
        blocs.add(new Bloc("spawn",                 0xff00ff00, 2,0, false));
        blocs.add(new Bloc("skull",                 0xffff7700, 3,0, false));
        blocs.add(new Bloc("key",                   0xff0000ff, 3,1, false));
        blocs.add(new Bloc("pill",                  0xffff648c, 3,2, false));
        blocs.add(new Bloc("torch",                 0xff00ffff, 4,0, false));
        blocs.add(new Bloc("coin",                  0xffffff00, 5,0, false));
        blocs.add(new Bloc("door",                  0xff999999, 5,2, false));
        blocs.add(new Bloc("arrow_down",            0xff000000, 6,0, false));
        blocs.add(new Bloc("arrow_left",            0xffff0000, 6,1, false));
        blocs.add(new Bloc("arrow_right",           0xff0000ff, 6,2, false));
        blocs.add(new Bloc("arrow_up",              0xff00ff00, 6,3, false));
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Bloc getBlocMap(int x, int y) {
        if (x >= 0 && x < level.getWidth() && y >= 0 && y < level.getHeight())
            return getBloc(level.getTiles()[x + y * level.getWidth()]);
        else
            return getBloc("wall");
    }

    /**
     * Define one bloc with a rgba code
     * @param x   Width coordinate
     * @param y   Height coordinate
     * @param col Rgba code
     */
    public void setBloc(int x, int y, int col) {
        int pos = x + y * level.getWidth();
        if (pos >= 0 && pos < level.getTiles().length) {
            if (col == getBloc("spawn").getCode()) {
                level.setSpawn(x, y);
            }
            level.getTiles()[pos] = col;
        }
    }

    public Bloc getBloc(String tag) {
        int i = 0;
        while (i < blocs.size() && !blocs.get(i).isTagged(tag)) {
            i++;
        }
        if (i < blocs.size()) return blocs.get(i);
        return null;
    }

    private Bloc getBloc(int code) {
        int i = 0;
        while (i < blocs.size() && blocs.get(i).getCode() != code) {
            i++;
        }
        if (i < blocs.size()) return blocs.get(i);
        return getBloc("wall");
    }
}
