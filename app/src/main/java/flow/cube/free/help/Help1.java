package flow.cube.free.help;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import flow.cube.free.R;
import flow.cube.free.levels.LevelsIcon;
import flow.cube.free.model.level.LevelsInfo;
import flow.cube.free.util.TypeFaceService;


public class Help1 extends AppCompatActivity {
    @BindView(R.id.game_help_text1)
    AppCompatTextView gameHelpText;

    private LevelsInfo infoFirst;
    private LinearLayout trueIcon;
    private LinearLayout liner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help1);
        ButterKnife.bind(Help1.this);
        trueIcon = (LinearLayout) findViewById(R.id.true_icon);
        liner = (LinearLayout) findViewById(R.id.game_layout);
        displeymetric();
        inicalizacia();
    }

    private void displeymetric() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int hight = dm.heightPixels;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
        liner.setLayoutParams(params);
        if ((1.5 * width / 3.5) < 1.6 * hight / 8.6) {
            int a = (int) (1.5 * width / 3.5);
            LinearLayout.LayoutParams paramsTrueIcon = new LinearLayout.LayoutParams(a, a);
            trueIcon.setLayoutParams(paramsTrueIcon);
        } else {
            int a = (int) (1.6 * hight / 8.6);
            LinearLayout.LayoutParams paramsTrueIcon = new LinearLayout.LayoutParams(a, a);
            trueIcon.setLayoutParams(paramsTrueIcon);
        }
        gameHelpText.setTypeface(TypeFaceService.getInstance().getPanforteProBold(Help1.this));
    }

    private void inicalizacia() {
        String info = "{\"listTrue\":[{\"color\":4,\"id\":11},{\"color\":2,\"id\":12},{\"color\":0,\"id\":21},{\"color\":1,\"id\":22}],\"listPlay\":[{\"color\":4,\"id\":11},{\"color\":2,\"id\":12},{\"color\":1,\"id\":21},{\"color\":0,\"id\":22}],\"id\":201,\"step\":1,\"time\":60}";
        Gson gson = new Gson();
        infoFirst = gson.fromJson(info, LevelsInfo.class);
        trueIcon.addView(new LevelsIcon(this, infoFirst.getListTrue(), infoFirst.getId(), false));
        liner.addView(new GemeHelp(this, infoFirst));
    }

    @Override
    public void onBackPressed() {
        //do nouting
    }
}
