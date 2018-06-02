package flow.cube.freee;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;
import android.widget.Switch;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import flow.cube.freee.help.Help1;
import flow.cube.freee.model.level.LevelsInfo;
import flow.cube.freee.levels.LevelsLayout;
import flow.cube.freee.memory.SharidPref;
import flow.cube.freee.util.DialogHelper;
import flow.cube.freee.util.IDialogLisener;
public class MainActivity extends AppCompatActivity implements IDialogLisener,View.OnClickListener {
    private Animation anim;
    boolean a;
    private ArrayList<LevelsInfo> levels = new ArrayList<LevelsInfo>();

    @BindView(R.id.x)
    AppCompatImageView exit;
    @BindView(R.id.help)
    AppCompatImageView help;
    @BindView(R.id.sound)
    AppCompatImageView sound;
    @BindView(R.id.scrol)
    ScrollView scrollView;



//    private AdView mAdView;
//    private AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);

        if (SharidPref.getInstance().isFirst(this)) {
            Intent i = new Intent(MainActivity.this, Help1.class);
            startActivity(i);
        }


        exit.setOnClickListener(this);
        help.setOnClickListener(this);
        sound.setOnClickListener(this);

        anim = AnimationUtils.loadAnimation(this, R.anim.myscale);



        addLevelDesign();


//        mAdView = (AdView) findViewById(R.id.adView);
//        AdView adView = new AdView(this);
//        adView.setAdSize(AdSize.BANNER);
//        adView.setAdUnitId(getString(R.string.adIdMenu));
//        adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);


    }


    @Override
    protected void onStart() {
        super.onStart();
        a = SharidPref.getInstance().isSound(this);
        initSound(a);


    }

    @Override
    protected void onPause() {
//        if (mAdView != null) {
//            mAdView.pause();
//        }
        super.onPause();
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


    @Override
    public void onBackPressed() {
        DialogHelper.getInstance().showDialog(MainActivity.this,this,false);

    }

    private void addLevelDesign() {
        LevelsLayout levelsDS = new LevelsLayout(this);
        scrollView.addView(levelsDS.getLiner());
    }




    private void initSound(boolean k) {
        sound.setImageResource(k ? R.drawable.sound : R.drawable.mute);


    }


    @Override
    public void yes() {
        finishAffinity();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.x:
                exit();
                break;
            case R.id.help:
                help();
                break;
            case R.id.sound:
                sound();
                break;


        }
    }

    public void exit() {
        exit.startAnimation(anim);
        DialogHelper.getInstance().showDialog(MainActivity.this,this,false);
    }

    public void help() {
        help.startAnimation(anim);
        Intent intentHelp = new Intent(MainActivity.this, Help1.class);
        startActivity(intentHelp);
    }

    public void sound() {
        SharidPref.getInstance().saveSound(this, a ? (a = false) : (a = true));
        initSound(a);
        sound.startAnimation(anim);
    }
}
