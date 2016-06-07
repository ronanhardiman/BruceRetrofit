package com.tigerfour.libraryutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainUtilsActivity extends AppCompatActivity {
    private TextView tv_info,tv_info1,tv_info2,tv_info3,tv_info4,tv_info5,tv_info6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        DeviceHelper.setContext(this);
//        tv_info = (TextView) findViewById(R.id.tv_info);
//        tv_info1 = (TextView) findViewById(R.id.tv_info1);
//        tv_info2 = (TextView) findViewById(R.id.tv_info2);
//        tv_info3 = (TextView) findViewById(R.id.tv_info3);
//        tv_info4 = (TextView) findViewById(R.id.tv_info4);
//        tv_info5 = (TextView) findViewById(R.id.tv_info5);
//        tv_info6 = (TextView) findViewById(R.id.tv_info6);
//
//        tv_info1.setText(tv_info1.getText()+""+DeviceHelper.getDpi());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
