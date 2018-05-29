package flow.cube.free;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import butterknife.BindView;
import butterknife.ButterKnife;
import flow.cube.free.key.Keys;
import flow.cube.free.levels.LevelsGame;
import flow.cube.free.levels.LevelsIcon;
import flow.cube.free.model.level.LevelsInfo;
import flow.cube.free.util.DialogHelper;
import flow.cube.free.util.IDialogLisener;
import flow.cube.free.util.TypeFaceService;

public class GamePaje extends AppCompatActivity implements IDialogLisener {
    @BindView(R.id.menu)
    AppCompatImageView menu;
    @BindView(R.id.true_icon)
    LinearLayout trueIcon;
    @BindView(R.id.refresh)
    AppCompatImageView refresh;
    @BindView(R.id.time)
    TextView timeText;
    @BindView(R.id.step_titel)
    TextView stepTitel;
    @BindView(R.id.time_title)
    TextView timeTitel;
    @BindView(R.id.step)
    TextView step;
    @BindView(R.id.game_layout)
    LinearLayout liner;


    private boolean first = true;
    private boolean pouse;
    private int time;

    private Animation anim;
    private LevelsInfo level;


//    private AdView mAdView;
//    private AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_game_paje);
        ButterKnife.bind(this);
        anim = AnimationUtils.loadAnimation(this, R.anim.myscale);


        setFonts();
//        mAdView = (AdView) findViewById(R.id.adView);
//        AdView adView = new AdView(this);
//        adView.setAdSize(AdSize.BANNER);
//        adView.setAdUnitId(getString(R.string.adIdGame));
//        adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
    }


    @Override
    protected void onStart() {
        super.onStart();
        pouse = false;
        linerDispleymetrik();
        setLevelInfo();
        play();


    }

    @Override
    protected void onPause() {
//        if (mAdView != null) {
//            mAdView.pause();
//        }
        super.onPause();
        pouse = true;
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (mAdView != null) {
//            mAdView.resume();
//        }
    }

    @Override
    public void onDestroy() {
//        if (mAdView != null) {
//            mAdView.destroy();
//        }
        super.onDestroy();
    }

    private void linerDispleymetrik() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int hight = dm.heightPixels;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
        liner.setLayoutParams(params);
        int a;
        if ((1.5 * width / 3.5) < 1.8 * hight / 8.6) {
            a = (int) (1.8 * width / 3.5);
        } else {
            a = (int) (1.7 * hight / 8.6);
        }
        LinearLayout.LayoutParams paramsTrueIcon = new LinearLayout.LayoutParams(a, a);
        trueIcon.setLayoutParams(paramsTrueIcon);
    }

    private void setLevelInfo() {
        level = (LevelsInfo) getIntent().getExtras().getSerializable(Keys.LEVEL_INFO);
        if (first) {
            time = level.getTime();
            first = false;
        }

        if (!pouse) {
            timego();
        }


    }


    private void play() {
        trueIcon.addView(new LevelsIcon(this, level.getListTrue(), level.getId(), false));
        View v = new LevelsGame(this, level);
        v.invalidate();
        liner.addView(v);

    }


    public void menu(View view) {
        menu.startAnimation(anim);
        Intent i = new Intent(GamePaje.this, MainActivity.class);
        startActivity(i);
    }

    public void refresh(View view) {
        refresh.startAnimation(anim);
        DialogHelper.getInstance().showDialog(GamePaje.this,(IDialogLisener) this,true);

    }



    private void timego() {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!pouse) {
                    if (time > 0) {
                        time--;
                    }


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int a = time / 60;
                            timeText.setText(a + ":" + (time - (a * 60)));
                            if (time == 0) {
                                pouse = true;
                                timeText.setTextColor(Color.parseColor("#FFD32626"));
                            }

                        }
                    });


                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        t.start();

    }

    private void setFonts() {
        timeTitel.setTypeface(TypeFaceService.getInstance().getPanforteProBold(this));
        stepTitel.setTypeface(TypeFaceService.getInstance().getPanforteProBold(this));
        timeText.setTypeface(TypeFaceService.getInstance().getPanforteProLight(this));
        step.setTypeface(TypeFaceService.getInstance().getPanforteProLight(this));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(GamePaje.this, MainActivity.class);
        startActivity(i);

    }



    @Override
    public void yes() {
        liner.removeAllViews();
        liner.addView(new LevelsGame(GamePaje.this, level));

    }
}
