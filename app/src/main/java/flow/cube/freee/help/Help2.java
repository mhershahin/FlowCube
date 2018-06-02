package flow.cube.freee.help;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import flow.cube.freee.GamePaje;
import flow.cube.freee.R;
import flow.cube.freee.model.level.LevelsInfo;
import flow.cube.freee.model.stars.StarsBoolian;
import flow.cube.freee.memory.SharidPref;
import flow.cube.freee.util.TypeFaceService;

import static flow.cube.freee.key.Keys.LEVEL_INFO;


public class Help2 extends AppCompatActivity {
    @BindView(R.id.play)
    AppCompatImageView play;
    @BindView(R.id.step_help_title)
    AppCompatTextView stepHelpTitle;
    @BindView(R.id.time_help_title)
    AppCompatTextView timeHelpTitle;
    @BindView(R.id.time_help_text)
    AppCompatTextView timeHelpText;
    @BindView(R.id.step_help_text)
    AppCompatTextView stepHelpText;
    @BindView(R.id.time_help)
    AppCompatTextView timeHelp;
    @BindView(R.id.step_help)
    AppCompatTextView stepHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help2);
        ButterKnife.bind(this);
         play.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 play();
             }
         });
        setFonts();
        starcheng();
    }

    private void setFonts() {
        stepHelpTitle.setTypeface(TypeFaceService.getInstance().getPanforteProBold(this));
        timeHelpTitle.setTypeface(TypeFaceService.getInstance().getPanforteProBold(this));
        timeHelpText.setTypeface(TypeFaceService.getInstance().getPanforteProBold(this));
        stepHelpText.setTypeface(TypeFaceService.getInstance().getPanforteProBold(this));
        timeHelp.setTypeface(TypeFaceService.getInstance().getPanforteProLight(this));
        stepHelp.setTypeface(TypeFaceService.getInstance().getPanforteProLight(this));

    }

    private void starcheng() {
        int area = 5000;
        List<StarsBoolian> starsListt = new ArrayList<StarsBoolian>();


        if (SharidPref.getInstance().getStarList(this) != null) {
            starsListt = SharidPref.getInstance().getStarList(this);
        }
        for (int i = 0; i < starsListt.size(); i++) {
            if (starsListt.get(i).getLevelid() == 201) {
                area = i;
                break;
            }
        }
        if (area == 5000) {
            starsListt.add(new StarsBoolian(201, true, true, true));
        } else {
            starsListt.set(area, new StarsBoolian(201, true, true, true));
        }

        SharidPref.getInstance().saveStars(this, starsListt);


    }


    public void play() {

        play.startAnimation(AnimationUtils.loadAnimation(this, R.anim.myscale));
        SharidPref.getInstance().saveFirst(this, false);
        String level2 = "{\"listTrue\":[{\"color\":1,\"id\":11},{\"color\":1,\"id\":12},{\"color\":1,\"id\":13},{\"color\":1,\"id\":21},{\"color\":0,\"id\":22},{\"color\":1,\"id\":23},{\"color\":1,\"id\":31},{\"color\":1,\"id\":32},{\"color\":1,\"id\":33}],\"listPlay\":[{\"color\":1,\"id\":11},{\"color\":1,\"id\":12},{\"color\":1,\"id\":13},{\"color\":1,\"id\":21},{\"color\":1,\"id\":22},{\"color\":1,\"id\":23},{\"color\":1,\"id\":31},{\"color\":1,\"id\":32},{\"color\":0,\"id\":33}],\"id\":301,\"step\":2,\"time\":120}";
        Intent intent = new Intent(Help2.this, GamePaje.class);
        intent.putExtra(LEVEL_INFO, new Gson().fromJson(level2, LevelsInfo.class));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Help2.this, Help1.class);
        startActivity(i);
    }
}
