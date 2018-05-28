package flow.cube.free;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import flow.cube.free.key.Keys;
import flow.cube.free.levels.LevelsIcon;
import flow.cube.free.model.level.LevelsInfo;
import flow.cube.free.model.stars.StarIntegers;
import flow.cube.free.model.stars.StarsBoolian;
import flow.cube.free.memory.FileOpen;
import flow.cube.free.memory.SharidPref;
import flow.cube.free.util.TypeFaceService;


public class Wine extends AppCompatActivity {
    private StarIntegers starIntegers;
    private StarsBoolian starsBoolian;
    private List<StarsBoolian> starsListt;
    private List<LevelsInfo> levellist;
    private LevelsInfo level;
    private LevelsInfo levelNext;

    private Animation anim;

    private LinearLayout linerTrue;
    private TextView textStep;
    private TextView textTime;
    private AppCompatImageView stepStar;
    private AppCompatImageView timeStar;
    private AppCompatImageView menu;
    private AppCompatImageView share;
    private AppCompatImageView next;
    private LinearLayout nextLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine);


        linerTrue = (LinearLayout) findViewById(R.id.wine_loyout);
        textStep = (TextView) findViewById(R.id.step_data);
        textTime = (TextView) findViewById(R.id.lamp_data);
        textStep.setTypeface(TypeFaceService.getInstance().getPanforteProLight(this));
        textTime.setTypeface(TypeFaceService.getInstance().getPanforteProLight(this));
        stepStar = (AppCompatImageView) findViewById(R.id.star_step);
        timeStar = (AppCompatImageView) findViewById(R.id.stat_lamp);
        menu = (AppCompatImageView) findViewById(R.id.menu);
        share = (AppCompatImageView) findViewById(R.id.share);
        next = (AppCompatImageView) findViewById(R.id.next);
        anim = AnimationUtils.loadAnimation(this, R.anim.myscale);
        nextLayout = (LinearLayout) findViewById(R.id.next_laout);


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
            LinearLayout layoutNex = (LinearLayout) findViewById(R.id.next_laout);
            layoutNex.setVisibility(View.GONE);
        }

    }


    @Override
    public void onBackPressed() {
        refresh();
    }

    public void menu(View view) {
        menu.startAnimation(anim);
        Intent i = new Intent(Wine.this, MainActivity.class);
        startActivity(i);
    }

    public void share(View view) {
        share.startAnimation(anim);

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=" + getPackageName());
        sendIntent.setType("text/plain");
        Intent chooser = Intent.createChooser(sendIntent, " ");
        chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(chooser);


    }


    public void refreshClick(View view) {
 refresh();
    }
    private void refresh(){
        level.setTime(starIntegers.getTime());
        Intent intent = new Intent(Wine.this, GamePaje.class);
        intent.putExtra(Keys.LEVEL_INFO, level);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void next(View view) {
        next.startAnimation(anim);
        Intent intent = new Intent(Wine.this, GamePaje.class);
        intent.putExtra(Keys.LEVEL_INFO, levelNext);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
