package flow.cube.free.model.level;


import java.io.Serializable;
import java.util.ArrayList;

import flow.cube.free.model.rect.Rect;

public class LevelsInfo implements Serializable {

    private int id;
    private ArrayList<Rect> listTrue;
    private ArrayList<Rect> listPlay;
    private int step;
    private  int time ;


    public LevelsInfo(int id, ArrayList<Rect> listTrue, ArrayList<Rect> listPlay, int step, int time) {
        this.id = id;
        this.listTrue = listTrue;
        this.listPlay = listPlay;
        this.step = step;
        this.time = time;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Rect> getListTrue() {
        return listTrue;
    }

    public void setListTrue(ArrayList<Rect> listTrue) {
        this.listTrue = listTrue;
    }

    public ArrayList<Rect> getListPlay() {
        return listPlay;
    }

    public void setListPlay(ArrayList<Rect> listPlay) {
        this.listPlay = listPlay;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }




}
