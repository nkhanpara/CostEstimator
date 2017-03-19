package com.example.gokulmarbles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


/**
 * Created by khanpar on 3/5/17.
 */

public class DisplayDataActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        String marbleData = "Marble";
        String kotaData = "Kota";
        for (int i = 1; i <= DBDefaultValues.MarbleData.length; i++) {
            marbleData += "\n" + i + "-" + DBDefaultValues.MarbleData[i-1];
            kotaData += "\n" + i + "-" + DBDefaultValues.KotaData[i-1];
        }

        // Capture the layout's TextView and set the string as its text
        TextView MarbleTextView = (TextView) findViewById(R.id.marble);
        TextView kotaTextView = (TextView) findViewById(R.id.kota);

        MarbleTextView.setText(marbleData);
        kotaTextView.setText(kotaData);
    }
}
