package vanwingerdenbarrier.sheetmusictutor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.animation.PathInterpolator;

/**
 * Created by Bronson VanWingerden on 9/20/2017.
 */

public class DrawShapes extends View {
    Paint paint = new Paint();

    public DrawShapes(Context context) {
        super(context);
    }

    @Override
    public void onDraw(Canvas canvas){
        paint.setColor(Color.MAGENTA);
        Rect rect = new Rect(100,100,100,100);
        canvas.drawRect(rect, paint);
    }
}
