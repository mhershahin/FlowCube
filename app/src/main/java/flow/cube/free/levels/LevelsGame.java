package flow.cube.free.levels;


import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import flow.cube.free.GamePaje;
import flow.cube.free.R;
import flow.cube.free.Wine;
import flow.cube.free.key.Keys;
import flow.cube.free.model.level.LevelsInfo;
import flow.cube.free.model.stars.StarIntegers;
import flow.cube.free.memory.SharidPref;
import flow.cube.free.model.rect.Rect;
import flow.cube.free.model.rect.RectToKordinat;


public class LevelsGame extends View   {

    private TextView step;
    private TextView timetx;
    private int steps;
    private MediaPlayer click ;
   private Context context;
   private LevelsInfo levelsInfo;

    private RectToKordinat rectToutch;
    private RectToKordinat rectVoid;
    int  touchX=0,touchY=0,canMove=0;


    private List<String> colorList;
    private ArrayList<RectToKordinat> listWhithXY ;
    private LinkedList<Rect> rectTemp;
    private List<Rect>  rectTrue;
    private Paint paint;
    private SharidPref sharidPref;
    private boolean sound;





    public LevelsGame(Context context, LevelsInfo levelsInfo) {
        super(context);

        this.context = context;
        this.levelsInfo = levelsInfo;
String a = context.getResources().getString(R.string.project_id);

         setFonts();
        inicalizatia();


    }

    private void setFonts() {

    }


    private void inicalizatia() {

        click = MediaPlayer.create(context.getApplicationContext(), R.raw.click);
        click.setVolume(10.0f, 3.0f);

        steps=-1;

        sound = SharidPref.getInstance().isSound(context);
        paint = new Paint();

        step = (TextView) ((GamePaje)context).findViewById(R.id.step);
        timetx = (TextView) ((GamePaje)context).findViewById(R.id.time);
        step.setText(steps+"/"+levelsInfo.getStep());



          setep();

        listWhithXY = new ArrayList<RectToKordinat>();
        colorList  = Arrays.asList(getResources().getStringArray(R.array.colors));
        rectTrue = levelsInfo.getListTrue();
        rectTemp = new LinkedList<Rect>();

        for(Rect r:levelsInfo.getListPlay()){
            rectTemp.add(r);
        }



    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        boolean handled = false;


        int pointerId,toX,toY;
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

                    if((touchX-toX)*(touchX-toX)+(touchY-toY)*(touchY-toY)>1500 ){
                        float deltaX = touchX - toX;
                        float deltaY = touchY - toY;
                        if(Math.abs(deltaX) > Math.abs(deltaY)){
                            if(deltaX > 0) {
                                onRightSwipe();
                                handled= true; }
                            if(deltaX < 0) {onLeftSwipe();handled=true; }
                        }
                        if(Math.abs(deltaX) < Math.abs(deltaY)){
                            if(deltaY < 0) { this.onDownSwipe(); handled=true; }
                            if(deltaY > 0) { this.onUpSwipe(); handled=true; }
                        }



                        return true;

                    }


                break;


        }


        return super.onTouchEvent(event) || handled;
    }

    private void onUpSwipe() {

if(canMove==1) {
    int idRectTuch = rectToutch.getRect().getId();
    int idRectVoid = rectVoid.getRect().getId();
    for (int i = 0; i < rectTemp.size(); i++) {
        Rect r = rectTemp.get(i);
        int a = r.getId();
        if (a % 10 == idRectVoid % 10 && a / 10 > idRectVoid / 10 && a / 10 <= idRectTuch / 10) {
            rectTemp.set(i, new Rect(r.getId() - 10, r.getColor()));
        }
        if (rectTemp.get(i).getColor() == 0) {
            rectTemp.remove(i);
            rectTemp.add(new Rect(rectToutch.getRect().getId(),0));

        }

    }
    canMove=0;

    invalidate();
    setep();
    playPositivSound();
    chekwine();

}
    }
    private void onLeftSwipe() {
        if(canMove==2) {
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



            invalidate();
            setep();
           playPositivSound();
            chekwine();


        }
    }



    private void onDownSwipe() {

        if(canMove==3) {
            int idRectTuch = rectToutch.getRect().getId();
            int idRectVoid = rectVoid.getRect().getId();
            for (int i = 0; i < rectTemp.size(); i++) {
                Rect r = rectTemp.get(i);
                int a = r.getId();
                if(a%10==idRectVoid%10 && a/10<idRectVoid/10 && a/10>=idRectTuch/10){
                    rectTemp.set(i, new Rect(a + 10, r.getColor()));
                }
                if (rectTemp.get(i).getColor() == 0) {
                    rectTemp.remove(i);
                    rectTemp.add(new Rect(rectToutch.getRect().getId(),0));

                }

            }
            canMove=0;

            invalidate();
            setep();
           playPositivSound();
            chekwine();

        }
    }
    private void onRightSwipe() {
        if(canMove==4) {
            int idRectTuch = rectToutch.getRect().getId();
            int idRectVoid = rectVoid.getRect().getId();
            for (int i = 0; i < rectTemp.size(); i++) {
                Rect r = rectTemp.get(i);
                int a = r.getId();
                if(a/10==idRectVoid/10  && a%10<=idRectTuch%10  && a%10>idRectVoid%10){
                    rectTemp.set(i, new Rect(a-1, r.getColor()));
                }

            }
            for (int i = 0; i < rectTemp.size(); i++) {
                Rect r = rectTemp.get(i);
                int a = r.getId();
                if (rectTemp.get(i).getColor() == 0 && a==rectVoid.getRect().getId()){
                    rectTemp.remove(i);
                    rectTemp.add(new Rect(rectToutch.getRect().getId(),0));

                }

            }

            canMove=0;

            invalidate();
            setep();
            playPositivSound();
            chekwine();

        }
    }



    private void findRects(int x, int y) {
        for(RectToKordinat rect:listWhithXY){
            if(rect.getStartX()<x && rect.getToX()>x && rect.getStartY()<y && rect.getToY()>y){
                rectToutch=rect;
            }
            if(rect.getRect().getColor()==0){
                rectVoid = rect;
            }
        }
        int idRectToutch = rectToutch.getRect().getId();
        int idRectVoid = rectVoid.getRect().getId();
        if(idRectToutch%10==idRectVoid%10 && idRectToutch/10>idRectVoid/10){
            canMove=1;
        }
        if(idRectToutch%10==idRectVoid%10 && idRectToutch/10<idRectVoid/10){
            canMove=3;
        }
        if(idRectToutch/10==idRectVoid/10 && idRectToutch%10<idRectVoid%10 ){
            canMove=2;
        }
        if(idRectToutch/10==idRectVoid/10 && idRectToutch%10>idRectVoid%10 ){
            canMove=4;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int a = levelsInfo.getId() / 100;
        int cirkleA;
        int cirkleB;
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        cirkleA = width / a;
        cirkleB = height / a;



        for(Rect r:rectTemp){
            int i=r.getId()/10;
            int j=r.getId()%10;
            listWhithXY.add(new RectToKordinat(r, (j - 1) * cirkleA + 7, (i - 1) * cirkleB + 7, j * cirkleA - 5, i * cirkleB - 5));

        }


        for (RectToKordinat rextXY : listWhithXY) {
            paint.setColor(Color.parseColor(colorList.get(rextXY.getRect().getColor())));
            canvas.drawRoundRect(new RectF(rextXY.getStartX(), rextXY.getStartY(), rextXY.getToX(), rextXY.getToY()), 20, 20, paint);
        }


    }







    private void setep() {
        steps++;
        step.setText(steps+"/"+levelsInfo.getStep());

    }
    public void playPositivSound(){

        if(sound){

            if(click.isPlaying()){
                click.stop();
                MediaPlayer click1=MediaPlayer.create(context.getApplicationContext(), R.raw.click);
                click1.setVolume(10.0f, 3.0f);
                click1.start();
            } else {
                click.start();
            }

        }


        }

    private void chekwine(){
        int couttrueRect=0;
        for(Rect rectGame:rectTemp){
            for(Rect rectTrue:levelsInfo.getListTrue()){
                if(rectGame.getId()==rectTrue.getId() && rectGame.getColor()==rectTrue.getColor()){
                    couttrueRect++;
                }
            }

        }
        if(couttrueRect==rectTemp.size()){

            String textTime[] =timetx.getText().toString().split(":");
            int minut=Integer.parseInt(textTime[0]);
            int second=Integer.parseInt(textTime[1]);
            int time=minut*60+second;
            StarIntegers starIntegers = new StarIntegers(levelsInfo.getId(),steps,time);
            Intent intent = new Intent(context, Wine.class);
            intent.putExtra(Keys.STAR_INFO,starIntegers);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }


    }
}
