package sonacollegeoftechnology.csesonastore.com.sonahostelapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button homescreen;
    EditText uname,pword;
    public static final String URL_LOGIN ="http://sonaplaystore.xyz/hostelapp/login.php";
    SharedPreferences sharedPreferences;
    //public static final String MY_PREFERENCES = "MyPrefs";
    public static final String EMAIL = "email";
    public static final String STATUS = "status";
    public static final String USERNAME = "username";
    private boolean status;

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";

    private static final String BASE_URL = "http://sonaplaystore.xyz/hostelapp/";
    private ArrayList<HashMap<String, String>> movieList;
    private ListView movieListView;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uname=findViewById(R.id.uname);
        pword=findViewById(R.id.pword);
        homescreen=findViewById(R.id.nextScreen);

        sharedPreferences = getSharedPreferences("MyPrefs"
                , Context.MODE_PRIVATE);

        homescreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new loginAsyncTask().execute();
            }
        });

    }

    private class loginAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Logging in... Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put("username", uname.getText().toString());
            httpParams.put("password", pword.getText().toString());
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "login.php", "GET", httpParams);
            try{
                int success = jsonObject.getInt(KEY_SUCCESS);

                String adminno=jsonObject.getString("adminno");
                String username=jsonObject.getString("username");
                String name=jsonObject.getString("name");
                String branch=jsonObject.getString("branch");
                String year=jsonObject.getString("year");
                String block = jsonObject.getString("block");
                String roomno=jsonObject.getString("roomno");

                if (success == 1) {

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username",username);
                    editor.putString("name",name);
                    editor.putString("roomno", roomno);
                    editor.commit();

                    Log.d("roomno",roomno);

                    Intent in = new Intent(getApplicationContext(), Main2Activity.class);
                    startActivity(in);
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
