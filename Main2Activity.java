package sonacollegeoftechnology.csesonastore.com.sonahostelapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {
ImageView e_book,e_status,e_cancel,f_book,f_status,f_cancel;
    String name,roomno,regno;
SharedPreferences prf;
    private ProgressDialog pDialog;
    CheckBox cb1,cb2,cb3,cb4,cb5;
    RatingBar rb;
    EditText complaint, f_complaint;
    RadioButton bradio,lradio,dradio;
    String result = "";
    String tof="";
    String rating="";
    String thisDate="";
    String thisTime="";
    private static final String BASE_URL = "http://sonaplaystore.xyz/hostelapp/";
    private static final String KEY_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        e_book=findViewById(R.id.book_electrical);
        e_status=findViewById(R.id.status_electrical);
        e_cancel=findViewById(R.id.cancel_electrical);

        f_book=findViewById(R.id.book_food);
        f_status=findViewById(R.id.status_food);
        f_cancel=findViewById(R.id.cancel_food);



        prf = getSharedPreferences("MyPrefs",MODE_PRIVATE);

        name=prf.getString("name",null);
        roomno=prf.getString("roomno",null);
        regno=prf.getString("username",null);
        e_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showElectricalComplaintDialog();
            }
        });

        e_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),Electric_status.class);
                startActivity(in);
            }
        });

        f_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFoodComplaintDialog();
            }
        });
    }

    public void showFoodComplaintDialog(){

        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();
        thisDate = currentDate.format(todayDate);

        SimpleDateFormat sdf4 = new SimpleDateFormat("h:mm a");
        thisTime =sdf4.format(todayDate);



        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this,R.style.CardView_Dark);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.food_book, null);
        dialogBuilder.setView(dialogView);

        f_complaint = (EditText) dialogView.findViewById(R.id.food_complaint);
        rb=dialogView.findViewById(R.id.ratingBar);

        bradio=dialogView.findViewById(R.id.radioBreakfast);
        lradio=dialogView.findViewById(R.id.radioLunch);
        dradio=dialogView.findViewById(R.id.radioDinner);


        if(bradio.isChecked()){
            tof=bradio.getText().toString();
        }else if(lradio.isChecked()){
            tof=lradio.getText().toString();
        }else if(dradio.isChecked()){
            tof=dradio.getText().toString();
        }


        dialogBuilder.setTitle("Food Complaints");
        dialogBuilder.setMessage("Hi "+name+ " Hope you enjoyed the Food that served today ("+thisDate+ "). Kindly give your valuable feedback");

        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                new foodCompAsyncTask().execute();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
    private class foodCompAsyncTask extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Main2Activity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();


        }

        @Override
        protected String doInBackground(String... strings) {
            rating=String.valueOf(rb.getRating());
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put("regno", regno);
            httpParams.put("tof", tof);
            httpParams.put("rating",rating);
            httpParams.put("description", f_complaint.getText().toString());
            httpParams.put("dt1",thisDate);
            httpParams.put("dt2",thisTime);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "food_fb_ins.php", "GET", httpParams);
            Log.d("radio","radio");
            try{
                int success = jsonObject.getInt(KEY_SUCCESS);

                Log.d("sucess",String.valueOf(success));
                if (success == 1) {

                    Log.d("sucess",String.valueOf(success));
                    //Toast.makeText(getApplicationContext(),"Insert Sucessfully", Toast.LENGTH_LONG).show();

                    return "OK";

                }
            }catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public void showElectricalComplaintDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.electrical_book, null);
        dialogBuilder.setView(dialogView);

        complaint = (EditText) dialogView.findViewById(R.id.edit1);
        cb1=dialogView.findViewById(R.id.checkBox2);
        cb2=dialogView.findViewById(R.id.checkBox3);
        cb3=dialogView.findViewById(R.id.checkBox4);
        cb4=dialogView.findViewById(R.id.checkBox5);
        cb5=dialogView.findViewById(R.id.checkBox6);

        dialogBuilder.setTitle("Electrical Complaints");
        dialogBuilder.setMessage("Hi "+name+ " kindly book your Electrical realted complaints of your room no. "+roomno);
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                new elecCompAsyncTask().execute();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private class elecCompAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();




            pDialog = new ProgressDialog(Main2Activity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();

            if(s.equals("OK"))
            {
                // Do something awesome here
                Toast.makeText(getApplicationContext(), "Complaint Registered Successfully...", Toast.LENGTH_LONG).show();

            }
            else
            {
                Toast.makeText(getApplicationContext(),"Network issue, Try Again Later...",Toast.LENGTH_LONG).show();
            }
        }


        @Override
        protected String doInBackground(String... strings) {

            if(cb1.isChecked()){
                result += cb1.getText().toString();
            }
            if(cb2.isChecked()){
                result += cb2.getText().toString();
            }
            if(cb3.isChecked()){
                result += cb3.getText().toString();
            }
            if(cb4.isChecked()){
                result += cb4.getText().toString();
            }
            if(cb5.isChecked()){
                result += cb5.getText().toString();
            }

            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put("regno", regno);
            httpParams.put("tyoc", result);
            httpParams.put("description", complaint.getText().toString());

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "electric_comp_ins.php", "GET", httpParams);

            try{
                int success = jsonObject.getInt(KEY_SUCCESS);


                if (success == 1) {

Log.d("sucess","sucess");
                    //Toast.makeText(getApplicationContext(),"Insert Sucessfully", Toast.LENGTH_LONG).show();

                return "OK";

                }
            }catch (JSONException e) {
                e.printStackTrace();
            }



            return null;
        }
    }
}
