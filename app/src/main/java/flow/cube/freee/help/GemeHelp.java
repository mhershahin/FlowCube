package flow.cube.freee.help;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import flow.cube.freee.R;
import flow.cube.freee.key.Keys;
import flow.cube.freee.model.level.LevelsInfo;
import flow.cube.freee.model.rect.Rect;
import flow.cube.freee.model.rect.RectToKordinat;
import flow.cube.freee.util.TypeFaceService;


public class GemeHelp extends View {

    private Context context;
    private LevelsInfo levelsInfo;

    private RectToKordinat rectToutch;
    private RectToKordinat rectVoid;
    int touchX = 0, touchY = 0, canMove = 0;

    private List<String> colorList;
    private ArrayList<RectToKordinat> listWhithXY;
    private LinkedList<Rect> rectTemp;
    private List<Rect> rectTrue;

    private Drawable d;
    int dinamicX = 0;
    private Paint paint;
    private Paint painttext;
    private boolean isontoch = false;

    public GemeHelp(Context context, LevelsInfo levelsInfo) {
        super(context);
        this.context = context;
        this.levelsInfo = levelsInfo;
        inicalizatia();
    }

    private void inicalizatia() {
        threadDinamicX();
        d = ResourcesCompat.getDrawable(getResources(), R.drawable.hand,null);

        paint = new Paint();

        painttext = new Paint();
        painttext.setTypeface(TypeFaceService.getInstance().getPanforteProBold(context));
        painttext.setColor(Color.WHITE);
        painttext.setTextSize(100);


        listWhithXY = new ArrayList<RectToKordinat>();
        colorList = Arrays.asList(getResources().getStringArray(R.array.colors));
        rectTrue = levelsInfo.getListTrue();
        rectTemp = new LinkedList<Rect>();


        for (Rect r : levelsInfo.getListPlay()) {
            rectTemp.add(r);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        boolean handled = false;


        int pointerId, toX, toY;
        int actionIndex = event.getActionIndex();

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:

                touchX = (int) event.getX(0);
                touchY = (int) event.getY(0);
                findRects(touchX, touchY);

                handled = true;
                break;


            case MotionEvent.ACTION_MOVE:

                pointerId = event.getPointerId(actionIndex);

                toX = (int) event.getX(actionIndex);
                toY = (int) event.getY(actionIndex);

                if ((touchX - toX) * (touchX - toX) + (touchY - toY) * (touchY - toY) > 1500) {
                    float deltaX = touchX - toX;
                    float deltaY = touchY - toY;
                    if (Math.abs(deltaX) > Math.abs(deltaY)) {
                        if (deltaX > 0) {
                            handled = true;
                        }
                        if (deltaX < 0) {
                            onLeftSwipe();
                            handled = true;
                        }
                    }


                    return true;

                }


                break;


        }


        return super.onTouchEvent(event) || handled;
    }

    private void onLeftSwipe() {
        if (canMove == 2) {
            int idRectTuch = rectToutch.getRect().getId();
            int idRectVoid = rectVoid.getRect().getId();
            for (int i = 0; i < rectTemp.size(); i++) {
                Rect r = rectTemp.get(i);
                int a = r.getId();
                if (a / 10 == idRectVoid / 10 && a % 10 < idRectVoid % 10 && a % 10 >= idRectTuch % 10) {
                    rectTemp.set(i, new Rect(a + 1, r.getColor()));
                }


            }
            for (int i = 0; i < rectTemp.size(); i++) {
                Rect r = rectTemp.get(i);
                int a = r.getId();

                if (rectTemp.get(i).getColor() == 0 && a == rectVoid.getRect().getId()) {
                    rectTemp.remove(i);
                    rectTemp.add(new Rect(rectToutch.getRect().getId(), 0));

                }

            }


            canMove = 0;


            isontoch = true;
            invalidate();
            Intent i = new Intent(context, Help2.class);
            context.startActivity(i);


        }
    }


    private void findRects(int x, int y) {
        for (RectToKordinat rect : listWhithXY) {
            if (rect.getStartX() < x && rect.getToX() > x && rect.getStartY() < y && rect.getToY() > y) {
                rectToutch = rect;
            }
            if (rect.getRect().getColor() == 0) {
                rectVoid = rect;
            }
        }
        int idRectToutch = rectToutch.getRect().getId();
        int idRectVoid = rectVoid.getRect().getId();
        if (idRectToutch % 10 == idRectVoid % 10 && idRectToutch / 10 > idRectVoid / 10) {
            canMove = 1;
        }
        if (idRectToutch % 10 == idRectVoid % 10 && idRectToutch / 10 < idRectVoid / 10) {
            canMove = 3;
        }
        if (idRectToutch / 10 == idRectVoid / 10 && idRectToutch % 10 < idRectVoid % 10) {
            canMove = 2;
        }
        if (idRectToutch / 10 == idRectVoid / 10 && idRectToutch % 10 > idRectVoid % 10) {
            canMove = 4;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int a = levelsInfo.getId() / 100;
        int cirkleA;
        int cirkleB;
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        if (width < 800) {
            painttext.setTextSize(60);
        }
        cirkleA = width / a;
        cirkleB = height / a;

        int textX = 0;
        int textY = 0;
        int textTouchX = 0;
        int textTouchY = 0;

        for (Rect r : rectTemp) {
            int i = r.getId() / 10;
            int j = r.getId() % 10;
            listWhithXY.add(new RectToKordinat(r, (j - 1) * cirkleA + 7, (i - 1) * cirkleB + 7, j * cirkleA - 5, i * cirkleB - 5));

        }


        for (RectToKordinat rextXY : listWhithXY) {
            paint.setColor(Color.parseColor(colorList.get(rextXY.getRect().getColor())));
            canvas.drawRoundRect(new RectF(rextXY.getStartX(), rextXY.getStartY(), rextXY.getToX(), rextXY.getToY()), 20, 20, paint);
            if (rextXY.getRect().getColor() == 0) {
                textX = rextXY.getStartX() + (rextXY.getToX() - rextXY.getStartX()) / 3;
                textY = rextXY.getStartY() + (rextXY.getToY() - rextXY.getStartY()) / 2;

            }
            if (rextXY.getRect().getColor() == 1) {
                textTouchX = rextXY.getStartX() + (rextXY.getToX() - rextXY.getStartX()) / 3;
                textTouchY = rextXY.getStartY() + (rextXY.getToY() - rextXY.getStartY()) / 2;

            }
        }

        canvas.drawText("Void", textX, textY, painttext);

        if (!isontoch) {
            if (textTouchX + dinamicX > cirkleA) {
                dinamicX = 0;
            }
            d.setBounds(textTouchX + dinamicX, textTouchY, textTouchX + 150 + dinamicX, textTouchY + 150);
            d.draw(canvas);
        }
    }

    private void threadDinamicX() {
        {

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!isontoch) {

                        dinamicX = dinamicX + 30;


                        ((Help1) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                invalidate();

                            }
                        });


                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });

            t.start();

        }
    }


    private void chekwine() {
        int couttrueRect = 0;
        for (Rect rectGame : rectTemp) {
            for (Rect rectTrue : levelsInfo.getListTrue()) {
                if (rectGame.getId() == rectTrue.getId() && rectGame.getColor() == rectTrue.getColor()) {
                    couttrueRect++;
                }
            }

        }


    }

    public Drawable getDrawable(String resource, Context context) {
        String packageName = context.getApplicationInfo().packageName;
        int nameResourceID = context.getResources().getIdentifier(resource, "drawable", packageName);
        return nameResourceID == 0 ? null : ContextCompat.getDrawable(context, nameResourceID);
    }

}