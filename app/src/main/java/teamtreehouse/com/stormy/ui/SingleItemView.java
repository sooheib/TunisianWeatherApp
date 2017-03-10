package teamtreehouse.com.stormy.ui;

/**
 * Created by sooheib on 14/07/16.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import teamtreehouse.com.stormy.R;

public class SingleItemView extends Activity {
    // Declare Variables
    String city;
    String latitude;
    String longitude;
    ProgressDialog mProgressDialog;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.singleitemview);
        // Execute loadSingleView AsyncTask
        new loadSingleView().execute();
    }

    public class loadSingleView extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(SingleItemView.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Android JSON Parse Tutorial");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {

                // Retrieve data from ListViewAdapter on click event
                Intent i = getIntent();
                // Get the result of rank
                city = i.getStringExtra("city");
                // Get the result of country
                latitude = i.getStringExtra("latitude");
                // Get the result of population
                longitude = i.getStringExtra("longitude");
                // Get the result of flag

            return null;
        }

        @Override
        protected void onPostExecute(String args) {
            // Locate the TextViews in singleitemview.xml
            TextView txtcity = (TextView) findViewById(R.id.city);
            TextView txtlatitude = (TextView) findViewById(R.id.latitude);
            TextView txtlongitude = (TextView) findViewById(R.id.longitude);
            // Locate the ImageView in singleitemview.xml

            // Set results to the TextViews
            txtcity.setText(city);
            txtlatitude.setText(latitude);
            txtlongitude.setText(longitude);

            // Set results to the ImageView

            // Close the progressdialog
            mProgressDialog.dismiss();

        }
    }

}
