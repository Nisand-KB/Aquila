package com.sbi.project.aquila;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

public class Cleanner extends AppCompatActivity {
    String User,ip;
    Gson gson=new Gson();
    Button trail,refresh;
    ListView lv;
    String listvalue,p,q;
    Response res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleanner);
        User=getIntent().getStringExtra("User");
        ip=getIntent().getStringExtra("ip");
        refresh = findViewById(R.id.buttontask);
        refresh.setVisibility(View.GONE);
        trail=findViewById(R.id.button2);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HttpGetRequest2().execute("http://"+ip+"/Aquila/aquila_problem_task.php");
            }
        });
        trail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HttpGetRequest2().execute("http://"+ip+"/Aquila/aquila_problem_task.php");
                refresh.setVisibility(View.VISIBLE);
            }
        });
    }
    public class HttpGetRequest2 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params){
            String data = "username="+ URLEncoder.encode(User);
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
            res=gson.fromJson(result,Response.class);
            String status = res.lab.toString();
            Log.e("check","=="+status);
            lv =findViewById(R.id.view);
            final List<String> your_array_list = new ArrayList<String>();
          //  Toast.makeText(getApplicationContext(),status,Toast.LENGTH_LONG).show();
            for (int j = 0; j < res.lab.size(); j++) {
                String problem=res.lab.get(j).get("Problem");
                String floor=res.lab.get(j).get("Floor");
                listvalue = "Floor "+floor+"   "+problem;
                your_array_list.add(listvalue);
                Log.e("check","=="+problem+"=="+floor);
            }


//            for (int i = 0; i < httpHistory.lab.length; i++) {
//                listtext = httpHistory.period[i] + "    " + httpHistory.dates[i] + "     " + httpHistory.lab[i];
//                your_array_list.add(listtext);
//
//
//            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    Cleanner.this,
                    android.R.layout.simple_list_item_1,
                    your_array_list);

            lv.setAdapter(arrayAdapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //view.getId();
//                    p = httpHistory.lab[i];
//                    q = httpHistory.dates[i];
                   p=res.lab.get(i).get("Problem");
                   q=res.lab.get(i).get("Floor");
                    Log.e("check","=="+p);
                    Log.e("check","=="+q);

                    try {
                        LayoutInflater layoutInflater = LayoutInflater.from(Cleanner.this);
                        View promptView = layoutInflater.inflate(R.layout.diolog, null);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Cleanner.this);
                        alertDialogBuilder.setView(promptView);
                        final TextView t = promptView.findViewById(R.id.textView3);

                        alertDialogBuilder.setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        new HttpGetRequest3().execute("http://"+ip+"/Aquila/aquila_problem_finished.php");
                                    }
                                })
                                .setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                        AlertDialog alert = alertDialogBuilder.create();
                        alert.show();
                    } catch (Exception e) {
                        System.out.println("error " + e);
                    }
                }
            });

        }
        }
    public class HttpGetRequest3 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params){
            String data = "username="+ URLEncoder.encode(User)+"&problem="+URLEncoder.encode(p)+"&floor="+URLEncoder.encode(q);
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
            String status = res.status;
            Log.e("check","=="+status);

        }

    }

    }

