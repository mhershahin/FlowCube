package flow.cube.free.model.rect;


import flow.cube.free.model.rect.Rect;

public class RectToKordinat {
    private Rect rect;
    private int startX;
    private int startY;
    private int toX;
    private  int toY;

    public RectToKordinat(Rect rect, int startX, int startY, int toX, int toY) {
        this.rect = rect;
        this.startX = startX;
        this.startY = startY;
        this.toX = toX;
        this.toY = toY;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getToX() {
        return toX;
    }

    public void setToX(int toX) {
        this.toX = toX;
    }

    public int getToY() {
        return toY;
    }

    public void setToY(int toY) {
        this.toY = toY;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }
}

