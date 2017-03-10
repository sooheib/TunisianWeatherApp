package teamtreehouse.com.stormy.ui;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import teamtreehouse.com.stormy.adapters.ListViewAdapter;
import teamtreehouse.com.stormy.R;


public class MainList extends ActionBarActivity {
    // Declare Variables
    JSONObject jsonobject;
    JSONArray jsonarray;
    ListView listview;
    Toolbar toolbar;

    ListViewAdapter adapter;
    ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, String>> arraylist;
    //json string
    private String jsonString = "{\"employee\":" +
            "[" +
            "{\"city\":\"Ariana\",\"latitude\":\"36.866537\",\"longitude\":\"10.164723\"}," +
            "{\"city\":\"Beja\",\"latitude\":\"36.7333193\",\"longitude\":\"9.1843676\"}," +
            "{\"city\":\"BenArous\",\"latitude\":\"36.7435\",\"longitude\":\"10.231976\"},"+
            "{\"city\":\"Bizerte\",\"latitude\":\"37.266667\",\"longitude\":\"9.866667000000007\"}," +
            "{\"city\":\"Gabes\",\"latitude\":\"33.883333\",\"longitude\":\"10.116667000000007\"}," +
            "{\"city\":\"Gafsa\",\"latitude\":\"34.416667\",\"longitude\":\"8.78333299999997\"},"+
            "{\"city\":\"Jendouba\",\"latitude\":\"36.5072263\",\"longitude\":\"8.7756556\"}," +
            "{\"city\":\"Kairouan\",\"latitude\":\"35.695603\",\"longitude\":\"9.723267\"}," +
            "{\"city\":\"Kasserine\",\"latitude\":\"35.2102\",\"longitude\":\"8.8585\"},"+
            "{\"city\":\"Kebili\",\"latitude\":\"33.7058\",\"longitude\":\"8.9706\"}," +
            "{\"city\":\"Kef\",\"latitude\":\"36.1675\",\"longitude\":\"8.7043\"}," +
            "{\"city\":\"Mahdia\",\"latitude\":\"35.5039\",\"longitude\":\"11.0560\"},"+
            "{\"city\":\"Manouba\",\"latitude\":\"36.7624\",\"longitude\":\"9.8336\"}," +
            "{\"city\":\"Medenine\",\"latitude\":\"33.3396\",\"longitude\":\"10.4912\"}," +
            "{\"city\":\"Monastir\",\"latitude\":\"35.7710\",\"longitude\":\"10.8272\"}," +
            "{\"city\":\"Nabeul\",\"latitude\":\"36.4538\",\"longitude\":\"10.7372\"}," +
            "{\"city\":\"Sfax\",\"latitude\":\"34.7397\",\"longitude\":\"10.7599\"}," +
            "{\"city\":\"SidiBouzid\",\"latitude\":\"34.8876\",\"longitude\":\"9.5223\"}," +
            "{\"city\":\"Siliana\",\"latitude\":\"36.0850\",\"longitude\":\"9.3695\"}," +
            "{\"city\":\"Sousse\",\"latitude\":\"35.8288\",\"longitude\":\"10.6405\"}," +
            "{\"city\":\"Tatouine\",\"latitude\":\"32.9292\",\"longitude\":\"10.4512\"}," +
            "{\"city\":\"Tozeur\",\"latitude\":\"33.9204\",\"longitude\":\"8.1338\"}," +
            "{\"city\":\"Tunis\",\"latitude\":\"36.8149\",\"longitude\":\"10.1591\"}," +
            "{\"city\":\"Zaghouan\",\"latitude\":\"36.3973\",\"longitude\":\"10.1462\"}"+

            "]}";
    public static String CITY = "city";
    public static String LATITUDE = "latitude";
    public static String LONGITUDE = "longitude";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from listview_main.xml
        setContentView(R.layout.listview_main);
        // Execute DownloadJSON AsyncTask


        if (isNetworkAvailable()) {

        new DownloadJSON().execute();

        }
        else

        { Toast.makeText(this, getString(R.string.network_unavailable_message),
                Toast.LENGTH_LONG).show();}

        toolbar = (Toolbar) findViewById(R.id.view); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    // DownloadJSON AsyncTask
    private class DownloadJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(MainList.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Android JSON Parse Tutorial");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create the array
            arraylist = new ArrayList<HashMap<String, String>>();
            // Retrive JSON Objects from the given website URL in JSONfunctions.class

            try {
                jsonobject = new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                // Locate the array name
                jsonarray = jsonobject.getJSONArray("employee");

                for (int i = 0; i < jsonarray.length(); i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    jsonobject = jsonarray.getJSONObject(i);
                    // Retrive JSON Objects
                    map.put("city", jsonobject.getString("city"));
                    System.out.println(jsonobject.getString("city"));
                    map.put("latitude", jsonobject.getString("latitude"));
                 //   System.out.println(jsonobject.getString("latitude"));

                    map.put("longitude", jsonobject.getString("longitude"));
                  //  System.out.println(jsonobject.getString("longitude"));

                    // Set the JSON Objects into the array
                    arraylist.add(map);
                }
            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            // Locate the listview in listview_main.xml
            listview = (ListView) findViewById(R.id.listview);
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter(MainList.this, arraylist);
            // Binds the Adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }

}
