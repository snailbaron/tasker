package com.mrsnail.tasker;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class ProcessInfoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_info);

        Intent intent = getIntent();
        ActivityManager.RunningAppProcessInfo processInfo = intent.getParcelableExtra(MainActivity.EXTRA_PROCESS_INFO);

        TextView pidTextView = (TextView) findViewById(R.id.pid_text_view);
        pidTextView.setText("PID: " + Integer.toString(processInfo.pid));

        TextView processNameTextView = (TextView) findViewById(R.id.process_name_text_view);
        processNameTextView.setText("Name: " + processInfo.processName);

        String[] packages = processInfo.pkgList;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.process_list_item, packages);

        ListView packageListView = (ListView) findViewById(R.id.package_list_view);
        packageListView.setAdapter(adapter);

        PackageManager pm = getPackageManager();
        for (String pkg : packages)
        {
            Log.d("Package", pkg);
            try {
                ApplicationInfo ai = pm.getApplicationInfo(pkg, PackageManager.GET_META_DATA);
                Log.d("App name", ai.loadLabel(pm).toString());
            } catch (PackageManager.NameNotFoundException ex) {
                Log.d("App not found", pkg);
            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_process_info, menu);
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
