package flow.cube.freee.levels;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


import android.graphics.RectF;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import flow.cube.freee.R;
import flow.cube.freee.model.rect.Rect;
import flow.cube.freee.model.rect.RectToKordinat;


public class LevelsIcon extends View {

    private ArrayList<Rect> rects;
    int id;
    private List<String> colorList;
    private ArrayList<RectToKordinat> listWhithXY = new ArrayList<RectToKordinat>();

    private Paint paint;

    private boolean big;

    public LevelsIcon(Context context, ArrayList<Rect> rects, int id, boolean big) {
        super(context);
        this.rects = rects;
        this.id = id;
        this.big = big;
        initPaintRect(context);
        colorList = Arrays.asList(getResources().getStringArray(R.array.colors));
    }

    private void initPaintRect(Context context) {
        paint = new Paint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        int line = big?5:2;
        int angel = big?15:5;

        int a = id / 100;
        int cirkleA;
        int cirkleB;
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        cirkleA = width / a;
        cirkleB = height / a;
for(Rect r:rects){
    int i=r.getId()/10;
    int j=r.getId()%10;
    listWhithXY.add(new RectToKordinat(r, (j - 1) * cirkleA + line, (i - 1) * cirkleB + line, j * cirkleA - line/2, i * cirkleB - line/2));

}

        for (RectToKordinat rextXY : listWhithXY) {
            paint.setColor(Color.parseColor(colorList.get(rextXY.getRect().getColor())));
            canvas.drawRoundRect(new RectF(rextXY.getStartX(), rextXY.getStartY(), rextXY.getToX(), rextXY.getToY()), angel, angel, paint);
        }

    }
}
