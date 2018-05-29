package flow.cube.free;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import flow.cube.free.help.Help1;
import flow.cube.free.model.level.LevelsInfo;
import flow.cube.free.levels.LevelsLayout;
import flow.cube.free.memory.SharidPref;
import flow.cube.free.util.DialogHelper;
import flow.cube.free.util.IDialogLisener;
public class MainActivity extends AppCompatActivity implements IDialogLisener {
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


    public void exit(View view) {
        exit.startAnimation(anim);
        DialogHelper.getInstance().showDialog(MainActivity.this,this,false);
    }



    public void help(View view) {
        help.startAnimation(anim);
        Intent intentHelp = new Intent(MainActivity.this, Help1.class);
        startActivity(intentHelp);
    }

    public void sound(View view) {
        SharidPref.getInstance().saveSound(this, a ? (a = false) : (a = true));
        initSound(a);
        sound.startAnimation(anim);
    }

    private void initSound(boolean k) {
        sound.setImageResource(k ? R.drawable.sound : R.drawable.mute);


    }


    @Override
    public void yes() {
        finishAffinity();

    }
}
