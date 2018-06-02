package flow.cube.freee.model.level;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mhers on 10/10/2017.
 */

public class LevelInfoList {

    @SerializedName("data")
    @Expose
    private List<LevelsInfo> data = new ArrayList<LevelsInfo>();

    public List<LevelsInfo> getData() {
        return data;
    }

    public void setData(List<LevelsInfo> data) {
        this.data = data;
    }
}
