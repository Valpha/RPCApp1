package com.valpha.rpcapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.valpha.rpcapp.R;
import com.valpha.rpcapp.View.FlowLayout;


public class MainActivity extends AppCompatActivity {

    FlowLayout appSelecter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appSelecter = findViewById(R.id.vg_appselecter);

        View appItemTuner = addAppItem(R.drawable.tuner_icon, "收音机", TunerActivity.class);
        appSelecter.addView(appItemTuner);

    }

    private View addAppItem(int appIcon, String appName, final Class appClass) {
        View appItem = getLayoutInflater().inflate(R.layout.activity_item_app, null);
        ((ImageView) appItem.findViewById(R.id.iv_appicon)).setImageResource(appIcon);
        ((TextView) appItem.findViewById(R.id.tv_appname)).setText(appName);
        appItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), appClass);
                startActivity(intent);
            }
        });
        return appItem;
    }
}
