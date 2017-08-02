package ca.uottawa.cookhelper;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by AshishK on 2016-11-21.
 */
public class FontFace  {
    Typeface[] fontArray = new Typeface[4];

    public FontFace(Context context) {

        fontArray[0] = Typeface.createFromAsset(context.getAssets(),"fonts/SignPainter.ttc");
        fontArray[1] = Typeface.createFromAsset(context.getAssets(),"fonts/Raleway-Regular.ttf");
        fontArray[2] = Typeface.createFromAsset(context.getAssets(),"fonts/Raleway-Light.ttf");
        fontArray[3] = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Bold.ttf");
    }

    public void setFontOf(TextView text, int index) {
        text.setTypeface(fontArray[index]);
    }
}
