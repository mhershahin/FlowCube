package flow.cube.freee.model.stars;



public class StarsBoolian {
    private int levelid;
    private boolean wine;
    private boolean step;
    private boolean time;

    public StarsBoolian() {
    }

    public StarsBoolian(int levelid, boolean wine, boolean step, boolean time) {
        this.levelid = levelid;
        this.wine = wine;
        this.step = step;
        this.time = time;
    }

    public int getLevelid() {
        return levelid;
    }

    public void setLevelid(int levelid) {
        this.levelid = levelid;
    }

    public boolean isWine() {
        return wine;
    }

    public void setWine(boolean wine) {
        this.wine = wine;
    }

    public boolean isStep() {
        return step;
    }

    public void setStep(boolean step) {
        this.step = step;
    }

    public boolean isTime() {
        return time;
    }

    public void setTime(boolean time) {
        this.time = time;
    }
}
