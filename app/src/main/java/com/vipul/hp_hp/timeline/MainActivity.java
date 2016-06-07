package com.vipul.hp_hp.timeline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

/**
 * Created by HP-HP on 07-06-2016.
 */
public class MainActivity extends AppCompatActivity {

    public final static String TAG_ORIENTATION = "orientation";

    private Button mVerticalTimeLineButton, mHorizontalTimeLineButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mVerticalTimeLineButton = (Button) findViewById(R.id.verticalTimeLineButton);
        mHorizontalTimeLineButton = (Button) findViewById(R.id.horizontalTimeLineButton);

        mVerticalTimeLineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(Orientation.vertical);
            }
        });

        mHorizontalTimeLineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(Orientation.horizontal);
            }
        });

    }

    private void onButtonClick(Orientation orientation) {

        Intent intent = new Intent(this, TimeLineActivity.class);
        intent.putExtra(TAG_ORIENTATION, orientation);
        startActivity(intent);
    }

}
