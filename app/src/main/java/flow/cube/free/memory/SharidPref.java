package flow.cube.free.memory;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import flow.cube.free.model.stars.StarsBoolian;

import static flow.cube.free.key.Keys.FIRST_KEY;
import static flow.cube.free.key.Keys.SOUND_KEY;
import static flow.cube.free.key.Keys.SP_NAME;
import static flow.cube.free.key.Keys.STAR_LIST_KEY;


public class SharidPref {
    public static SharidPref INSTANCE = null;

    public static SharidPref getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SharidPref();
        }
        return INSTANCE;
    }

    private SharidPref() {
    }


    public void saveFirst(Context context,boolean firstGame){
       SharedPreferences sharedPreferences=context.getSharedPreferences(SP_NAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(FIRST_KEY,firstGame);
        editor.commit();
    }
    public boolean isFirst(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SP_NAME, Context.MODE_APPEND);
        return sharedPreferences.getBoolean(FIRST_KEY,true);
    }



    public void saveSound(Context context,boolean sound){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SP_NAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SOUND_KEY,sound);
        editor.commit();
    }
    public boolean isSound(Context context){
       SharedPreferences sharedPreferences=context.getSharedPreferences(SP_NAME, Context.MODE_APPEND);
        return sharedPreferences.getBoolean(SOUND_KEY,true);
    }

    public void saveStars(Context context,List<StarsBoolian> starsList) {
        Gson g = new Gson();
        String json= g.toJson(starsList);
       SharedPreferences sharedPreferences=context.getSharedPreferences(SP_NAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(STAR_LIST_KEY,json);
        editor.commit();
    }
    public List<StarsBoolian> getStarList(Context context){
         SharedPreferences sharedPreferences=context.getSharedPreferences(SP_NAME, Context.MODE_APPEND);
        Gson gson = new Gson();
        Type type = new TypeToken<List<StarsBoolian>>(){}.getType();
        return gson.fromJson(sharedPreferences.getString(STAR_LIST_KEY,null),type );

    }





}
