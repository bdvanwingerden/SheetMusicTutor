package vanwingerdenbarrier.sheetmusictutor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Bronson VanWingerden on 9/26/2017.
 */
public class DrawLines extends View {


    Point size;

    public DrawLines(Context context) {
        super(context);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        size = new Point();
        display.getSize(size);
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawColor(Color.BLUE);
        drawStaff(canvas);
    }


    private void drawStaff(Canvas canvas){
        Rect rectangle;
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);

        /**
         * size of the border
         */

        int lineWidth = 30;
        int spaceBetween = 75;
        int top = spaceBetween;
        int bottom = top + lineWidth;
        int right = size.x;
        int left = 0;
        int position = 50;

        for(int i = 1; i <= 5; i++) {

            rectangle = new Rect(left, top, right, bottom);
            canvas.drawRect(rectangle, paint);

            position += spaceBetween;
            top += position;
            bottom = top + lineWidth;
        }
    }
}



