package com.eulersonrodrigues.exercitar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class TutorialActivity extends Activity implements View.OnClickListener {
    Button btnNext;
    Button btnDone;
    Integer currentImg = 0;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        btnNext = (Button)findViewById(R.id.btnNext);
        btnDone = (Button)findViewById(R.id.btnDone);
        btnNext.setOnClickListener(this);
        btnDone.setOnClickListener(this);

        imageView = (ImageView)findViewById(R.id.imagemTuto);

        imageView.setImageResource(R.drawable.tuto4);

        btnNext.setVisibility(View.VISIBLE);
        btnDone.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btnNext:
                imageView.setImageResource(R.drawable.tuto3);
                btnNext.setVisibility(View.INVISIBLE);
                btnDone.setVisibility(View.VISIBLE);
                break;
            case R.id.btnDone:
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
        }

    }

   /* public void onClick (View v) {
        if (v.getId() == R.id.btnDone) {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
    }*/
}
