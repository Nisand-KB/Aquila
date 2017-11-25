package com.sbi.project.aquila;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnSignin;
    TextInputEditText username,password;
    String user,pass,ip="192.168.137.1:81";
    Gson gson=new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=findViewById(R.id.username_input);
        password=findViewById(R.id.password_input);
        btnSignin=findViewById(R.id.btnSignin);
        btnSignin.setOnClickListener(this);
    }
 @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnSignin){
            user = username.getEditableText().toString();
            pass = password.getEditableText().toString();
            new HttpGetRequest(MainActivity.this).execute("http://"+ip+"/Aquila/aquila_app_login.php");
        }

    }
    public class HttpGetRequest extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;
        public HttpGetRequest(Context mainActivity) {
            dialog = new ProgressDialog(mainActivity);
            dialog.setMessage("Signing in!");
           dialog.setCancelable(false);
           dialog.show();
        }

        @Override
        protected String doInBackground(String... params){
            String data = "username="+ URLEncoder.encode(user)+"&password="+URLEncoder.encode(pass);
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
            dialog.dismiss();
            Response res=gson.fromJson(result,Response.class);
            String status = res.status;
            Log.e("check","=="+status);
            if(status.equals("2")) {
                Intent cleanner = new Intent(getApplicationContext(), Cleanner.class);
                cleanner.putExtra("User",user);
                cleanner.putExtra("ip",ip);
                startActivity(cleanner);
            }
            else if(status.equals("1")) {
                Intent OfficeStaff = new Intent(getApplicationContext(), OfficeStaff.class);
                OfficeStaff.putExtra("User",user);
                OfficeStaff.putExtra("ip",ip);
                startActivity(OfficeStaff);

            }
            else {
                Toast.makeText(getApplicationContext(),"Invalid username and password",Toast.LENGTH_LONG).show();
            }
        }

    }


}
