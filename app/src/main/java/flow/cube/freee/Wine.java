package flow.cube.freee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import flow.cube.freee.key.Keys;
import flow.cube.freee.levels.LevelsIcon;
import flow.cube.freee.model.level.LevelsInfo;
import flow.cube.freee.model.stars.StarIntegers;
import flow.cube.freee.model.stars.StarsBoolian;
import flow.cube.freee.memory.FileOpen;
import flow.cube.freee.memory.SharidPref;
import flow.cube.freee.util.TypeFaceService;


public class Wine extends AppCompatActivity implements View.OnClickListener {
    private StarIntegers starIntegers;
    private StarsBoolian starsBoolian;
    private List<StarsBoolian> starsListt;
    private List<LevelsInfo> levellist;
    private LevelsInfo level;
    private LevelsInfo levelNext;

    private Animation anim;

    @BindView(R.id.wine_loyout)
    LinearLayout linerTrue;
    @BindView(R.id.step_data)
    AppCompatTextView textStep;
    @BindView(R.id.text_time)
    AppCompatTextView textTime;

    @BindView(R.id.star_step)
    AppCompatImageView stepStar;
    @BindView(R.id.stat_lamp)
    AppCompatImageView timeStar;

    @BindView(R.id.menu)
    AppCompatImageView menu;
    @BindView(R.id.share)
    AppCompatImageView share;
    @BindView(R.id.refresh_wine)
    AppCompatImageView refresh;
    @BindView(R.id.next)
    AppCompatImageView next;


    @BindView(R.id.wine_next_laout)
    LinearLayout nextLayout;
    @BindView(R.id.wine_refresh_layout)
    LinearLayout refreshLayout;
    @BindView(R.id.wine_menu_layout)
    LinearLayout menuLayout;
    @BindView(R.id.wine_share_layout)
    LinearLayout shareLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine);
        ButterKnife.bind(this);

        textStep.setTypeface(TypeFaceService.getInstance().getPanforteProLight(this));
        textTime.setTypeface(TypeFaceService.getInstance().getPanforteProLight(this));

        anim = AnimationUtils.loadAnimation(this, R.anim.myscale);

        menuLayout.setOnClickListener(this);
        shareLayout.setOnClickListener(this);
        refreshLayout.setOnClickListener(this);
        nextLayout.setOnClickListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        starIntegers = (StarIntegers) getIntent().getExtras().getSerializable(Keys.STAR_INFO);
        levelsInfo();
        findLevel();
        inicalizaciStar();
        inicalizaciViews();
    }


    private void levelsInfo() {
        starsListt = new ArrayList<StarsBoolian>();
        starsListt.add(new StarsBoolian(201, true, true, true));


    }

    private void findLevel() {
        levellist = FileOpen.getInstance().creatLevelsInfoList(this);
        for (int i = 0; i < levellist.size(); i++) {
            if (levellist.get(i).getId() == starIntegers.getLevelId()) {
                level = levellist.get(i);
                if (i < levellist.size() - 1) {
                    levelNext = levellist.get(i + 1);
                }
                break;
            }

        }
        if (level.getId() == levellist.get(levellist.size() - 1).getId()) {
            nextLayout.setVisibility(View.GONE);

        }
    }

    private void inicalizaciStar() {
        if (SharidPref.getInstance().getStarList(this) != null) {
            starsListt = SharidPref.getInstance().getStarList(this);
        }
        StarsBoolian starTemp = new StarsBoolian(level.getId(), false, false, false);
        boolean stepBoolian = false;
        boolean timeBoolian = false;
        int a = starIntegers.getTime();
        if (starIntegers.getStepCount() <= level.getStep()) {
            stepBoolian = true;
        }
        if (starIntegers.getTime() != 0) {
            timeBoolian = true;
        }
        starsBoolian = new StarsBoolian(starIntegers.getLevelId(), true, stepBoolian, timeBoolian);
        if (starsListt != null) {
            for (int i = 0; i < starsListt.size(); i++) {
                if (starsListt.get(i).getLevelid() == level.getId()) {
                    starTemp = starsListt.get(i);
                    if (!starTemp.isStep() || !starTemp.isTime()) {
                        starsListt.remove(i);
                        starsListt.add(starsBoolian);
                        break;
                    }

                }

            }


        }


        if (!starTemp.isWine()) {
            starsListt.add(starsBoolian);
            int k = 10;
        }


        SharidPref.getInstance().saveStars(this, starsListt);

    }

    private void inicalizaciViews() {

        linerTrue.addView(new LevelsIcon(this, level.getListTrue(), level.getId(), true));
        textStep.setText(starIntegers.getStepCount() + "/" + level.getStep());
        int a = starIntegers.getTime() / 60;
        textTime.setText(a + ":" + (starIntegers.getTime() - (a * 60)));

        if (starsBoolian.isStep()) {
            stepStar.setImageResource(R.drawable.ic_star_yellow);
        }
        if (starsBoolian.isTime()) {
            timeStar.setImageResource(R.drawable.ic_star_yellow);
        }

        if (level.getId() == levellist.get(levellist.size() - 1).getId()) {
            nextLayout.setVisibility(View.GONE);
        }

    }


    @Override
    public void onBackPressed() {
        refresh();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wine_menu_layout:
                menu();
                break;
            case R.id.wine_share_layout:
                share();
                break;
            case R.id.wine_refresh_layout:
                refresh.setAnimation(anim);
                refresh();
                break;
            case R.id.wine_next_laout:
                next();
                break;

        }

    }


    public void menu() {
        menu.startAnimation(anim);
        Intent i = new Intent(Wine.this, MainActivity.class);
        startActivity(i);
    }

    public void share() {
        share.startAnimation(anim);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=" + getPackageName());
        sendIntent.setType("text/plain");
        Intent chooser = Intent.createChooser(sendIntent, " ");
        chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(chooser);


    }

    private void refresh() {
        level.setTime(starIntegers.getTime());
        Intent intent = new Intent(Wine.this, GamePaje.class);
        intent.putExtra(Keys.LEVEL_INFO, level);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void next() {
        next.startAnimation(anim);
        Intent intent = new Intent(Wine.this, GamePaje.class);
        intent.putExtra(Keys.LEVEL_INFO, levelNext);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
