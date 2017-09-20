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

public class drawShapes extends View {
    Paint paint = new Paint();

    public drawShapes(Context context) {
        super(context);
    }

    @Override
    public void onDraw(Canvas canvas){
        paint.setColor(Color.MAGENTA);
        Rect rect = new Rect(10,10,10,10);
        canvas.drawRect(rect, paint);
    }
}
