package com.mrsnail.tasker;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    public final static String EXTRA_PROCESS_INFO = "com.mrsnail.tasker.PROCESS_INFO";

    private ActivityManager.RunningAppProcessInfo[] m_processInfos;

    private ListView.OnItemClickListener processClickListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Intent processInfoIntent = new Intent(MainActivity.this, ProcessInfoActivity.class);
            processInfoIntent.putExtra(EXTRA_PROCESS_INFO, m_processInfos[position]);
            startActivity(processInfoIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfoList = activityManager.getRunningAppProcesses();
        m_processInfos = processInfoList.toArray(new ActivityManager.RunningAppProcessInfo[processInfoList.size()]);

        List<String> pids = new ArrayList<>();
        for (ActivityManager.RunningAppProcessInfo process : m_processInfos)
            pids.add(Integer.toString(process.pid) + " : " + process.processName);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.process_list_item, pids);

        ListView processesListView = (ListView) findViewById(R.id.processes_list_view);
        processesListView.setAdapter(adapter);

        processesListView.setOnItemClickListener(processClickListener);
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
