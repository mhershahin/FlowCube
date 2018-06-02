package flow.cube.freee.help;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import flow.cube.freee.R;
import flow.cube.freee.levels.LevelsIcon;
import flow.cube.freee.model.level.LevelsInfo;
import flow.cube.freee.util.TypeFaceService;


public class Help1 extends AppCompatActivity {
    @BindView(R.id.game_help_text1)
    AppCompatTextView gameHelpText;

    @BindView(R.id.true_icon)
    LinearLayout trueIcon;
    @BindView(R.id.game_layout)
    LinearLayout liner;




    private LevelsInfo infoFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help1);
        ButterKnife.bind(Help1.this);
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
        int a;
        if ((1.5 * width / 3.5) < 1.6 * hight / 8.6) {
            a = (int) (1.5 * width / 3.5);
        } else {
             a = (int) (1.6 * hight / 8.6);
        }
        LinearLayout.LayoutParams paramsTrueIcon = new LinearLayout.LayoutParams(a, a);
        trueIcon.setLayoutParams(paramsTrueIcon);

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
