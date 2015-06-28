package com.disaintworking.konnect;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SettingsActivity extends ActionBarActivity {

    public NumberPicker mNumberPicker;
    public Button mButton, mDeleteButton;
    public Button mButton2;
    public Spinner spinner;
    public GridView mGridview;
    public static int NoOfColummns;
    final Context context = this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (AddPeople.db==null){
            AddPeople.db = new DatabaseOps(this);
        }

        mDeleteButton = (Button) findViewById(R.id.deleteButton);
        mNumberPicker = (NumberPicker)findViewById(R.id.NumberPicker);
        mButton2 = (Button)findViewById(R.id.backButton);
        mNumberPicker.setMinValue(1);
        mNumberPicker.setMaxValue(3);

        mButton = (Button)findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoOfColummns = mNumberPicker.getValue();
                Intent myIntent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(myIntent);
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoOfColummns = mNumberPicker.getValue();
                Intent myIntent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(myIntent);
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompt, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                alertDialogBuilder.setView(promptsView);
                final Spinner spinner = (Spinner) promptsView.findViewById (R.id.spinnerDelete);

                final List <People> Listx = AddPeople.db.getALlPeople();
                ArrayList <String> List2 = new ArrayList<>();

                for (int p = 0; p < Listx.size(); p++) List2.add(Listx.get(p).getName());

                ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(SettingsActivity.this, android.R.layout.simple_spinner_item, List2);
                spinner.setAdapter(dataAdapter2);
                //final People userSelectPeople = new People(userSelect2);
                AlertDialog.Builder builder = alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {



                                        if (spinner.getSelectedItem() != null) {
                                            final String userSelect2 = spinner.getSelectedItem().toString();
                                            AddPeople.db.deletePeople(userSelect2);

                                            final List Listx2 = AddPeople.db.getALlPeople();

                                            ArrayList<String> List3 = new ArrayList<>();

                                            for (int p = 0; p < Listx2.size(); p++)
                                                List3.add(Listx2.get(p).toString());

                                            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(SettingsActivity.this, android.R.layout.simple_spinner_item, List3);
                                            spinner.setAdapter(dataAdapter2);



                                        } else {
                                            Toast.makeText(context, "Please Create a Contact First", Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
