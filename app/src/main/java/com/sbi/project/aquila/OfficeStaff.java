package com.sbi.project.aquila;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class OfficeStaff extends AppCompatActivity {
ImageButton problem,requirement,lab;
String User,ip;
String pro;
String Spinervalue ="";
Spinner sItems1;
String Floorvalue="";
    EditText problemdia;
    Gson gson=new Gson();
    AlertDialog.Builder alertDialogBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_staff);
        User=getIntent().getStringExtra("User");
        ip=getIntent().getStringExtra("ip");
        problem =findViewById(R.id.imageButton);
        requirement = findViewById(R.id.imageButton2);
        lab =findViewById(R.id.imageButton3);
        problem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogBuilder = new AlertDialog.Builder(OfficeStaff.this);
                problemmethod();
                alertDialogBuilder.setPositiveButton("Post",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                pro=problemdia.getText().toString();
                                new HttpGetRequest1().execute("http://"+ip+"/Aquila/aquila_problem_insert.php");

                            }
                        });
                alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }

        });
        requirement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OfficeStaff.this);
                TextView problemtext = new TextView(OfficeStaff.this);
                final EditText problem = new EditText(OfficeStaff.this);
                problemtext.setText("Enter the problem :");
                problemtext.setTextSize(20);
                problem.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                LinearLayout ll=new LinearLayout(OfficeStaff.this);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.addView(problemtext);
                ll.addView(problem);
                alertDialogBuilder.setView(ll);
                alertDialogBuilder.setPositiveButton("Post",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                String pro=problem.getText().toString();
                                Toast.makeText(OfficeStaff.this,"sucessfully"+pro,Toast.LENGTH_LONG).show();
                            }
                        });

                alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        lab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OfficeStaff.this);
                TextView problemtext = new TextView(OfficeStaff.this);
                final EditText problem = new EditText(OfficeStaff.this);
                problemtext.setText("Enter the problem :");
                problemtext.setTextSize(20);
                problem.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                LinearLayout ll=new LinearLayout(OfficeStaff.this);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.addView(problemtext);
                ll.addView(problem);
                alertDialogBuilder.setView(ll);
                alertDialogBuilder.setPositiveButton("Post",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                String pro=problem.getText().toString();
                                Toast.makeText(OfficeStaff.this,"sucessfully"+pro,Toast.LENGTH_LONG).show();
                            }
                        });

                alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    private void problemmethod() {
        TextView problemtext = new TextView(OfficeStaff.this);
        problemtext.setTextColor(Color.WHITE);
        TextView spinnertxt = new TextView(OfficeStaff.this);
        TextView floortext = new TextView(OfficeStaff.this);
        spinnertxt.setTextColor(Color.WHITE);
        floortext.setTextColor(Color.WHITE);
        floortext.setText("Select the Floor :");
        floortext.setTextSize(20);
        problemdia = new EditText(OfficeStaff.this);
        problemdia.setTextColor(Color.WHITE);
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Cleaning");
        spinnerArray.add("Security");
        spinnerArray.add("Service");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(OfficeStaff.this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner sItems = new Spinner(OfficeStaff.this);
        sItems.setAdapter(adapter);

        spinerFloor();
        problemtext.setText("Enter the problem :");
        problemtext.setTextSize(20);
        spinnertxt.setText("Select the problem type :");
        spinnertxt.setTextSize(20);
        problemdia.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        LinearLayout ll=new LinearLayout(OfficeStaff.this);
        ll.setPadding(10,10,10,10);
        ll.setBackgroundColor(getResources().getColor(R.color.colorBlackTrans));
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(problemtext);
        ll.addView(problemdia);
        ll.addView(spinnertxt);
        ll.addView(sItems);
        ll.addView(floortext);
        ll.addView(sItems1);
        alertDialogBuilder.setView(ll);
        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch( i){
                    case 0:
                        Spinervalue ="Cleaning";
                        break;
                    case 1:
                        Spinervalue ="Security";
                        break;
                    case 2:
                        Spinervalue ="Service";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void spinerFloor(){
        List<String> spinnerArray1 =  new ArrayList<String>();
        spinnerArray1.add("Floor 1");
        spinnerArray1.add("Floor 2");
        spinnerArray1.add("Floor 3");
        spinnerArray1.add("Floor 4");
        spinnerArray1.add("Floor 5");
        spinnerArray1.add("Floor 6");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(OfficeStaff.this, android.R.layout.simple_spinner_item, spinnerArray1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sItems1 = new Spinner(OfficeStaff.this);
        sItems1.setAdapter(adapter1);
        sItems1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Floorvalue = "1";
                        break;
                    case 1:
                        Floorvalue = "2";
                        break;
                    case 3:
                        Floorvalue = "3";
                        break;
                    case 4:
                        Floorvalue = "4";
                        break;
                    case 5:
                        Floorvalue = "5";
                        break;
                    case 6:
                        Floorvalue = "6";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

}
    public class HttpGetRequest1 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params){
            String data = "username="+ URLEncoder.encode(User)+"&problem="+URLEncoder.encode(pro)+"&problemtype="+URLEncoder.encode(Spinervalue)+"&floor="+URLEncoder.encode(Floorvalue);
            String text = "";
            BufferedReader reader=null;
            try
            {
                URL url = new URL(params[0]);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write( data );
                wr.flush();

                // Get the server response

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                text = sb.toString();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            finally
            {
                try
                {
                    reader.close();
                }
                catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
            return text;
        }
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            Response res=gson.fromJson(result,Response.class);
            String status = res.problemstatus;
            Log.e("check","=="+status);
Toast.makeText(getApplicationContext(),status,Toast.LENGTH_LONG).show();
        }

    }
}
