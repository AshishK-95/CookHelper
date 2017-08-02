package ca.uottawa.cookhelper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {

    public static FontFace font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //Remove notification bar
        font = new FontFace(this);

        TextView cookHelper = (TextView) findViewById(R.id.hw);
        TextView hungry = (TextView) findViewById(R.id.hungrybtn);

        font.setFontOf(cookHelper, 0); //set custom font of the 'cook helper' text
        font.setFontOf(hungry, 1); //set custom font of the 'hungry' text
    }

    public void onClickHungry(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}
