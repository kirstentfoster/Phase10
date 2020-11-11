package up.edu.phase10.Framework;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.SurfaceView;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.core.content.res.ResourcesCompat;

public class ComputerPhaseView extends SurfaceView {

    /**   https://htmlcolorcodes.com/
     * Creates all the cards as drawables
     * @param canvas
     */
    public void onDraw(Canvas canvas){
        Resources res = context.getResources();
        Drawable myImage = ResourcesCompat.getDrawable(res, R.drawable.my_image, null);

    }


    public ComputerPhaseView(Context context) {
        super(context);
    }
                

}
