package flow.cube.freee.memory;

import android.content.Context;
import android.content.res.AssetManager;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import flow.cube.freee.model.level.LevelInfoList;
import flow.cube.freee.model.level.LevelsInfo;


public class FileOpen {

    public static FileOpen INSTANCE = null;

    public static FileOpen getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FileOpen();
        }
        return INSTANCE;
    }

    public List<LevelsInfo> creatLevelsInfoList(Context context) {
        LevelInfoList levels = null;
        try {
            AssetManager assetManager = context.getAssets();
            InputStream ims = assetManager.open("levels.info.json");
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(ims);
             levels = gson.fromJson(reader, LevelInfoList.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return levels.getData();
    }



}
