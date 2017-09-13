package ccc.tcl.com.sprotappui.customui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by user on 17-9-12.
 */

public class TrapezoidView extends View {

    public TrapezoidView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(3);
        Path path = new Path();
        path.moveTo(40, 40);
        path.lineTo(190, 40);
        path.lineTo(190,140);
        path.lineTo(115, 170);
        path.lineTo(40, 140);
        path.close();
        canvas.drawPath(path, paint);
    }
}
