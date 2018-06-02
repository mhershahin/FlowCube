package flow.cube.freee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import flow.cube.freee.util.TypeFaceService;


public class SplashScreen extends Activity {
    @BindView(R.id.text_app_name)
    TextView appName;
    private static int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        appName.setTypeface(TypeFaceService.getInstance().getPanforteProBold(this));

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
