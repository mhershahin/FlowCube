package flow.cube.free.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import flow.cube.free.GamePaje;
import flow.cube.free.R;
import flow.cube.free.levels.LevelsGame;

import static android.view.Window.FEATURE_NO_TITLE;

/**
 * Created by mhers on 05/10/2017.
 */

public class DialogHelper {

    public static DialogHelper INSTANCE = null;

    public static DialogHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DialogHelper();
        }
        return INSTANCE;
    }

    public void showDialog(Activity activity, final IDialogLisener lisener, boolean isRefres){
        final Dialog dialog = new Dialog(activity);
        dialog.setCancelable(false);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().requestFeature(FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(500, 400);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView text = (TextView) dialog.findViewById(R.id.dialog_text);
        TextView pozitiv = (TextView) dialog.findViewById(R.id.possitiv);
        TextView negativ = (TextView) dialog.findViewById(R.id.negativ);


        text.setTypeface(TypeFaceService.getInstance().getPanforteProBold(activity));
        pozitiv.setTypeface(TypeFaceService.getInstance().getPanforteProLight(activity));
        negativ.setTypeface(TypeFaceService.getInstance().getPanforteProLight(activity));

        text.setText(isRefres?activity.getString(R.string.play_again):activity.getString(R.string.exit));

        pozitiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                lisener.yes();
            }
        });

        negativ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
