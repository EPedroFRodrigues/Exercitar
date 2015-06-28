package com.eulersonrodrigues.exercitar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;


public class ExerciseActivity extends ActionBarActivity {
    TextView data ;
    TextView cardio;
    TextView esportes;
    TextView musculacao;
    TextView total;
    Button b;
    String dataLoad;

    DBHelper mydb;

    Boolean editMode = false;
    Boolean shareAble = true;

    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        b = (Button)findViewById(R.id.button1);
        mydb = new DBHelper(this);

        data = (TextView) findViewById(R.id.edtData);
        cardio = (TextView) findViewById(R.id.edtHorasCardio);
        esportes = (TextView) findViewById(R.id.edtHorasEsportes);
        musculacao = (TextView) findViewById(R.id.edtHorasMusculacao);
        total = (TextView) findViewById(R.id.edtHorasTotal);

        Bundle extras = getIntent().getExtras();
        dataLoad = extras.getString("data");

        data.setText(dataLoad);

        Cursor rs = mydb.getData(dataLoad);
        if (rs.getCount()>0) {
            int teste = rs.getCount();
            Log.d("teste", Integer.toString(teste));
            editMode = true;
            cardio.setText(rs.getString(rs.getColumnIndex(DBHelper.EXERCICIOS_COLUMN_CARDIO)));
            esportes.setText(rs.getString(rs.getColumnIndex(DBHelper.EXERCICIOS_COLUMN_ESPORTES)));
            musculacao.setText(rs.getString(rs.getColumnIndex(DBHelper.EXERCICIOS_COLUMN_MUSCULACAO)));
            total.setText(rs.getString(rs.getColumnIndex(DBHelper.EXERCICIOS_COLUMN_TOTAL)));
            Log.d("Cardio",rs.getString(rs.getColumnIndex(DBHelper.EXERCICIOS_COLUMN_CARDIO)));
            b.setVisibility(View.INVISIBLE);
        } else editMode = false;

        if (!editMode) {
            b.setVisibility(View.VISIBLE);

            cardio.setEnabled(true);
            cardio.setFocusableInTouchMode(true);
            cardio.setClickable(true);

            esportes.setEnabled(true);
            esportes.setFocusableInTouchMode(true);
            esportes.setClickable(true);

            musculacao.setEnabled(true);
            musculacao.setFocusableInTouchMode(true);
            musculacao.setClickable(true);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (editMode){
        getMenuInflater().inflate(R.menu.menu_exercise, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.Edit_Value:

                b.setVisibility(View.VISIBLE);

                cardio.setEnabled(true);
                cardio.setFocusableInTouchMode(true);
                cardio.setClickable(true);

                esportes.setEnabled(true);
                esportes.setFocusableInTouchMode(true);
                esportes.setClickable(true);

                musculacao.setEnabled(true);
                musculacao.setFocusableInTouchMode(true);
                musculacao.setClickable(true);

                shareAble = false;
                return true;
            case R.id.Delete_Value:


                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteValue)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteValue(dataLoad);
                                Toast.makeText(getApplicationContext(), R.string.successDelete, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                AlertDialog d = builder.create();
                d.setTitle("Tem certeza?");
                d.show();

                return true;
            case R.id.bt_facebook:
                if(shareAble) {
                    Integer totalInt;
                    Integer cardioInt = Integer.parseInt(cardio.getText().toString());
                    Integer esportesInt = Integer.parseInt(esportes.getText().toString());
                    Integer musculacaolInt = Integer.parseInt(musculacao.getText().toString());

                    totalInt = cardioInt + esportesInt + musculacaolInt;

                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                .setContentTitle("Exercitar")
                                .setImageUrl(Uri.parse("http://i.imgur.com/EihQWwd.png"))
                                .setContentDescription(
                                        "Exercícios do dia " + dataLoad + ": " + cardioInt + " minutos de cardio, " + esportesInt + " minutos de esportes e " + musculacaolInt + " minutos de musculação.Total: " + totalInt + " minutos me exercitando!")
                                .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=com.eulersonrodrigues.calendarproject"))
                                .build();

                        shareDialog.show(linkContent);
                    }
                } else Toast.makeText(getApplicationContext(), R.string.alterationsSave, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }


    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void run(View view)
    {
        Integer totalInt;
        Integer cardioInt = Integer.parseInt(cardio.getText().toString());
        Integer esportesInt = Integer.parseInt(esportes.getText().toString());
        Integer musculacaolInt = Integer.parseInt(musculacao.getText().toString());

        totalInt = cardioInt + esportesInt + musculacaolInt;

        if(editMode){
            if(mydb.updateValue(data.getText().toString(), cardio.getText().toString(), esportes.getText().toString(), musculacao.getText().toString(), totalInt.toString())){
                Toast.makeText(getApplicationContext(), "Registro Atualizado", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(getApplicationContext(), "Não Atualizado", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            if(mydb.insertValue(data.getText().toString(), cardio.getText().toString(), esportes.getText().toString(), musculacao.getText().toString(), totalInt.toString())){
                Toast.makeText(getApplicationContext(), "Registro Concluído", Toast.LENGTH_SHORT).show();
            }

            else{
                Toast.makeText(getApplicationContext(), "Erro", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }

    }


}
