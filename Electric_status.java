package sonacollegeoftechnology.csesonastore.com.sonahostelapp;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Electric_status extends AppCompatActivity {

    private static final String data_url = "http://sonaplaystore.xyz/hostelapp/electric_status.php"; // kasih link prosesnya contoh : http://domainname or ip/folderproses/namaproses

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    ArrayList<ModelData> mItems;
    String regno;

    SharedPreferences prf;

    TextView booked,progress,completed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.status_activity);

        prf = getSharedPreferences("MyPrefs",MODE_PRIVATE);

        regno=prf.getString("username",null);

        pd = new ProgressDialog(Electric_status.this);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_data);
        mItems = new ArrayList<>();
        mManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mManager);
        mAdapter = new AdapterProcess(getApplicationContext(), mItems);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        loadjson();

        booked=findViewById(R.id.booked);
        progress=findViewById(R.id.progress);
        completed=findViewById(R.id.completed);

        booked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadBookedJson();
            }
        });
    }



    private void loadBookedJson() {
        pd.setMessage("Booking Data...");
        pd.setCancelable(false);
        pd.show();

        String d="?regno="+regno.toString();

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, data_url+d, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                pd.cancel();
                Log.d("volley", "response : " + response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject data = response.getJSONObject(i);
                        ModelData md = new ModelData();
                        md.setNamaData(data.getString("comp_id")); // memanggil nama array yang kita buat
                        //mItems.add(md);
                        md.setTyoc(data.getString("tyoc")); // memanggil nama array yang kita buat
                        //mItems.add(md);
                        md.setDesc(data.getString("description")); // memanggil nama array yang kita buat
                        //mItems.add(md);
                        md.setStat(data.getString("status")); // memanggil nama array yang kita buat
                        md.setYear(data.getString("changedat"));
                        mItems.add(md);
                        //Log.d("data", String.valueOf(data.getString("tyoc")));
                        Log.d("data", String.valueOf(data.getString("comp_id")));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("Arraylist", String.valueOf(mItems.get(0)));
                    //Log.d("Arraylist", String.valueOf(mItems.get(1)));
                }
                mAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.cancel();
                Log.d("volley", "error : " + error.getMessage());
            }
        });
        Controller.getInstance().addToRequestQueue(arrayRequest);
    }

    private void loadjson() {
        pd.setMessage("Mengambil Data");
        pd.setCancelable(false);
        pd.show();

        String d="?regno="+regno.toString();

            JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, data_url+d, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                pd.cancel();
                Log.d("volley", "response : " + response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject data = response.getJSONObject(i);
                        ModelData md = new ModelData();
                        md.setNamaData(data.getString("comp_id")); // memanggil nama array yang kita buat
                        //mItems.add(md);
                        md.setTyoc(data.getString("tyoc")); // memanggil nama array yang kita buat
                        //mItems.add(md);
                        md.setDesc(data.getString("description")); // memanggil nama array yang kita buat
                        //mItems.add(md);
                        md.setStat(data.getString("status")); // memanggil nama array yang kita buat
                        md.setYear(data.getString("changedat"));
                        mItems.add(md);
                        //Log.d("data", String.valueOf(data.getString("tyoc")));
                        Log.d("data", String.valueOf(data.getString("comp_id")));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("Arraylist", String.valueOf(mItems.get(0)));
                    //Log.d("Arraylist", String.valueOf(mItems.get(1)));
                }
                mAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.cancel();
                Log.d("volley", "error : " + error.getMessage());
            }
        })/*{
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> httpParams = new HashMap<>();
                httpParams.put("regno", regno);
                return httpParams;
            }
        }*/;
        Controller.getInstance().addToRequestQueue(arrayRequest);
    }
}
