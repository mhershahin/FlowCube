package flow.cube.free.model.stars;


import java.io.Serializable;

public class StarIntegers implements Serializable{
    private int levelId;
    private int stepCount;
    private int time;

    public StarIntegers(int levelId, int stepCount, int time) {
        this.levelId = levelId;
        this.stepCount = stepCount;
        this.time = time;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
