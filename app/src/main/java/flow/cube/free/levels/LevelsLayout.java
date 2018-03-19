package flow.cube.free.levels;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import flow.cube.free.GamePaje;
import flow.cube.free.MainActivity;
import flow.cube.free.R;
import flow.cube.free.key.Keys;
import flow.cube.free.model.level.LevelsInfo;
import flow.cube.free.model.stars.StarsBoolian;
import flow.cube.free.memory.FileOpen;
import flow.cube.free.memory.SharidPref;
import flow.cube.free.util.TypeFaceService;


public class LevelsLayout {

    private Context context;
    private List<StarsBoolian> stars;
    private List<LevelsInfo> levelsInfolist = new ArrayList<LevelsInfo>();
    private LinearLayout liner;
    private List<View> levelsView = new ArrayList<View>();
    private TextView textStar;
    private TextView textNumber;
    private TextView textTitel;
    private int ollStars = 0;
    private int oppenUntilId=201;
    private int oppenUntilNumber=0;

    public LevelsLayout(Context context) {
        this.context = context;
        creatLevelsInfo();
        creatOllLevelsDesign(levelsInfolist);
        onClikeLevesView();
        setFont();
    }

    private void setFont() {
        textStar.setTypeface(TypeFaceService.getInstance().getPanforteProLight(context));
    }


    private void creatLevelsInfo() {
        levelsInfolist = FileOpen.getInstance().creatLevelsInfoList(context);
        stars = new ArrayList<StarsBoolian>();
        if (SharidPref.getInstance().getStarList(context) != null) {
            stars = SharidPref.getInstance().getStarList(context);
        }
        for(StarsBoolian s:stars){
            if(s.getLevelid()>oppenUntilId){
                oppenUntilId=s.getLevelid();
            }
        }

        for(int i=0;i<levelsInfolist.size();i++){
            if(levelsInfolist.get(i).getId()==oppenUntilId && i!=(levelsInfolist.size()-1)){
                oppenUntilId=levelsInfolist.get(i+1).getId();
                oppenUntilNumber=i+1;
                break;
            }
        }



    }

    private LinearLayout creatOllLevelsDesign(List<LevelsInfo> levels) {
        liner = new LinearLayout(context);
        LinearLayout.LayoutParams paramsLiner = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        liner.setGravity(Gravity.CENTER_HORIZONTAL);
        liner.setLayoutParams(paramsLiner);
        liner.setOrientation(LinearLayout.VERTICAL);

        int k = 2;
        while (k < 7) {
            textTitel = new TextView(context);
            textTitel.setGravity(Gravity.CENTER_HORIZONTAL);
            textTitel.setTypeface(TypeFaceService.getInstance().getPanforteProLight(context));
            textTitel.setText(k + " x " + k);
            textTitel.setTextSize(30);
            textTitel.setTextColor(Color.WHITE);
            liner.addView(textTitel);
            List<LevelsInfo> tempList = new ArrayList<LevelsInfo>();
            for (LevelsInfo l : levelsInfolist) {
                if (l.getId() > (k * 100) && l.getId() < (k + 1) * 100) {
                    tempList.add(l);
                }
            }
            if (tempList.size() == 1) {
                View v = creatLvelView(levelsInfolist.get(0));
                liner.addView(v);
                levelsView.add(v);
            }
            if (tempList.size() > 2) {
                for (int j = 0; j < tempList.size() / 3; j++) {
                    LinearLayout liner3levels = new LinearLayout(context);
                    LinearLayout.LayoutParams paramsliner3levels = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    liner3levels.setGravity(Gravity.CENTER_HORIZONTAL);
                    liner3levels.setLayoutParams(paramsliner3levels);
                    liner3levels.setOrientation(LinearLayout.HORIZONTAL);
                    for (int l = 0; l < 3; l++) {
                        View v = creatLvelView(tempList.get((j * 3) + l));
                        liner3levels.addView(v);
                        levelsView.add(v);
                    }
                    liner.addView(liner3levels);
                }
            }
            k++;
        }

        textStar = (TextView) ((MainActivity) context).findViewById(R.id.oll_star);
        textStar.setText(ollStars + "");

        return liner;
    }

    private void onClikeLevesView() {
        for (int i = 0; i <=oppenUntilNumber; i++) {
            View v = levelsView.get(i);
            final int finalI = i;
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = 10;
                    startGame(finalI);
                }
            });
        }

    }

    private void startGame(int index) {
        LevelsInfo level = levelsInfolist.get(index);
        Intent intent = new Intent(context.getApplicationContext(), GamePaje.class);
        intent.putExtra(Keys.LEVEL_INFO, level);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private FrameLayout creatLvelView(LevelsInfo info) {
        int id = info.getId();
        StarsBoolian starsBoolian = null;
        for (StarsBoolian st : stars) {
            if (st.getLevelid() == id)
                starsBoolian = st;
        }
        int number = (id % 100);
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        int width = (int) (dm.widthPixels / 5);
        int height = (int) (dm.heightPixels / 6);

        FrameLayout layoutbacgraun = new FrameLayout(context);
        LinearLayout layoutforshape = new LinearLayout(context);
        LinearLayout layoutforText = new LinearLayout(context);
        textNumber = new TextView(context);
        LinearLayout layoutforImage = new LinearLayout(context);

        View v = new LevelsIcon(context, info.getListTrue(), info.getId(), false);

        LinearLayout layoutforStars = new LinearLayout(context);
        AppCompatImageView imageStar1 = new AppCompatImageView(context);
        AppCompatImageView imageStar2 = new AppCompatImageView(context);
        AppCompatImageView imageStar3 = new AppCompatImageView(context);


        LinearLayout.LayoutParams paramsLayoutb = new LinearLayout.LayoutParams(width, height);
        paramsLayoutb.setMargins(22, 22, 20, 20);
        layoutbacgraun.setLayoutParams(paramsLayoutb);
//        layoutbacgraun.setOrientation(LinearLayout.VERTICAL);
        layoutbacgraun.setBackgroundColor(Color.parseColor("#343538"));


        LinearLayout.LayoutParams paramsShape = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutforshape.setLayoutParams(paramsShape);
        layoutforshape.setOrientation(LinearLayout.VERTICAL);
        layoutforshape.setBackgroundResource(R.drawable.levels_bacgraund);


        LinearLayout.LayoutParams paramstext = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.5f);
        layoutforText.setLayoutParams(paramstext);
        layoutforText.setOrientation(LinearLayout.VERTICAL);
        textNumber.setTypeface(TypeFaceService.getInstance().getPanforteProLight(context));
        textNumber.setText(number + "");
        textNumber.setTextColor(Color.WHITE);
        textNumber.setGravity(Gravity.CENTER);


        LinearLayout.LayoutParams paramsImag = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 5f);
        layoutforImage.setLayoutParams(paramsImag);
        layoutforImage.setGravity(Gravity.CENTER);
        layoutforImage.setOrientation(LinearLayout.VERTICAL);


        LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(width - (width / 4), width - (width / 4));
        paramsImage.gravity = Gravity.CENTER;


        v.setLayoutParams(paramsImage);

        LinearLayout.LayoutParams paramStar = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 2f);

        layoutforStars.setGravity(Gravity.CENTER);
        layoutforStars.setLayoutParams(paramStar);
        layoutforStars.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams paramsStar = new LinearLayout.LayoutParams(width / 4, width / 4);
        paramsStar.setMargins(3, 0, 0, 0);


        imageStar1.setLayoutParams(paramsStar);
        imageStar1.setLayoutParams(paramsStar);
        imageStar2.setLayoutParams(paramsStar);
        imageStar3.setLayoutParams(paramsStar);
        imageStar1.setImageResource(R.drawable.ic_star_dark);
        imageStar2.setImageResource(R.drawable.ic_star_dark);
        imageStar3.setImageResource(R.drawable.ic_star_dark);
        if (starsBoolian != null) {
            if (starsBoolian.isWine()) {
                imageStar1.setImageResource(R.drawable.ic_star_yellow);
                ollStars++;

            }
            if (starsBoolian.isStep()) {
                imageStar2.setImageResource(R.drawable.ic_star_yellow);
                ollStars++;
            }
            if (starsBoolian.isTime()) {
                imageStar3.setImageResource(R.drawable.ic_star_yellow);
                ollStars++;
            }
        }
//
        LinearLayout lock = new LinearLayout(context);
        lock.setOrientation(LinearLayout.HORIZONTAL);
        lock.setGravity(Gravity.CENTER);
        lock.setBackgroundResource(R.drawable.lock_level_back);


        AppCompatImageView imagelock  = new AppCompatImageView(context);
        LinearLayout.LayoutParams lockImageParams = new LinearLayout.LayoutParams(width - (width / 2), width - (width / 2));
        imagelock.setLayoutParams(lockImageParams);
        imagelock.setImageResource(R.drawable.ic_lock_outline_black_24dp);
        lock.addView(imagelock);



        layoutforText.addView(textNumber);

        layoutforImage.addView(v);

        layoutforStars.addView(imageStar1);
        layoutforStars.addView(imageStar2);
        layoutforStars.addView(imageStar3);


        layoutforshape.addView(layoutforText);
        layoutforshape.addView(layoutforImage);
        layoutforshape.addView(layoutforStars);


        layoutbacgraun.addView(layoutforshape);
        layoutbacgraun.addView(lock);
        lock.setVisibility(oppenUntilId>=info.getId()?View.GONE:View.VISIBLE);


        return layoutbacgraun;
    }

    public LinearLayout getLiner() {
        return liner;
    }




}
