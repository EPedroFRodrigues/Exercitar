package com.eulersonrodrigues.exercitar;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;


public class MainActivity extends ActionBarActivity {
    Long date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create object of SharedPreferences.
        SharedPreferences sharedPref= getSharedPreferences("mypref", 0);
        //now get Editor
        SharedPreferences.Editor editor= sharedPref.edit();
        //put your value
        editor.putBoolean("firstAccess", false);
        //commits your edits
        editor.commit();

        final CalendarView calendarView=(CalendarView) findViewById(R.id.calendarView1);
        date = calendarView.getDate();

        calendarView.setOnDateChangeListener(new OnDateChangeListener(){
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                if(calendarView.getDate() != date){
                    date = calendarView.getDate();
                    Bundle dataBundle = new Bundle();
                    dataBundle.putString("data", dayOfMonth+"/"+(month+1)+"/"+year);

                    Intent intent;
                    intent = new Intent(getApplicationContext(),ExerciseActivity.class);

                    intent.putExtras(dataBundle);
                    startActivity(intent);
                }
            }
        });

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
        if (id == R.id.Gera_Relatorio) {
            Intent intent;
            intent = new Intent(getApplicationContext(),ListActivity.class);

            startActivity(intent);
        }

        if (id == R.id.Tutorial) {
            Intent intent;
            intent = new Intent(getApplicationContext(),TutorialActivity.class);

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
