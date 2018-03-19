package flow.cube.free.model.rect;


import java.io.Serializable;

public class Rect implements Serializable{
    private int id;
    private int color;

    public Rect(int id, int color) {
        this.id = id;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
